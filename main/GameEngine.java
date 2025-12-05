package main;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import playable.Acrobat;
import playable.FortuneTeller;
import playable.StrongMan;

public class GameEngine {
    public static void runEncounter() {
        // Use Clown default constructor (default HP set in Clown.java)
        Clown clown = new Clown();
        TreasureChest chest = new TreasureChest();
        GameStats stats = new GameStats();

        // shared party health (characters share this pool)
        java.util.concurrent.atomic.AtomicInteger partyHp = new java.util.concurrent.atomic.AtomicInteger(100);

        StrongMan strongMan = new StrongMan(0, "Strong Man");
        FortuneTeller fortuneTeller = new FortuneTeller(0, "Fortune Teller");
        Acrobat acrobat = new Acrobat(0, "Acrobat");

        // share target and resources
        strongMan.setTarget(clown);
        fortuneTeller.setTarget(clown);
        acrobat.setTarget(clown);

        strongMan.setChest(chest); strongMan.setStats(stats);
        fortuneTeller.setChest(chest); fortuneTeller.setStats(stats);
        acrobat.setChest(chest); acrobat.setStats(stats);

        // We'll use an ExecutorService to control execution order of character steps.
        // A single-thread executor will run submitted Runnables sequentially.
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Clown will attack deterministically between character steps (no separate clown thread)

        // Execute per-step: for each step, run each character in order, then let the clown attack.
        java.util.List<playable.GameCharacter> players = java.util.Arrays.asList(strongMan, fortuneTeller, acrobat);
        int maxSteps = 3;
        outer:
        for (int step = 1; step <= maxSteps; step++) {
            for (playable.GameCharacter pc : players) {
                final int currentStep = step;
                final playable.GameCharacter currentPc = pc;
                try {
                    // submit the single step for this character and wait for it to finish
                    java.util.concurrent.Future<?> f = executor.submit(() -> {
                        currentPc.performStep(currentStep);
                    });
                    f.get();
                } catch (Exception e) {
                    // interrupted or execution exception
                    Thread.currentThread().interrupt();
                    break outer;
                }

                // After the character's step completes, clown attacks once (if still alive)
                if (clown.isAlive()) {
                    int dmg = clown.clownAttack();
                    int remaining = partyHp.addAndGet(-dmg);
                    System.out.println("the party now has " + Math.max(remaining, 0) + " health remaining.");
                    if (remaining <= 0) {
                        playable.GameCharacter.partyAlive = false;
                        System.out.println("Party has been defeated by the Clown!");
                        System.out.println("The party has been defeated. Game over.");
                        // Terminate JVM so outer loops and callers stop immediately
                        System.exit(0);
                        break outer;
                    }
                }
            }
            // stop early if clown dead
            if (!clown.isAlive()) break;
        }

        // shutdown executor
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) executor.shutdownNow();
        } catch (InterruptedException ie) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        // No periodic clown thread to stop here — encounters finish after per-step orchestration

        // After players finish, if clown is defeated, distribute remaining loot to players
        if (!clown.isAlive()) {
            System.out.println("\nDistributing loot to survivors...\n");
            java.util.List<playable.GameCharacter> playersList = java.util.Arrays.asList(strongMan, fortuneTeller, acrobat);
            java.util.concurrent.atomic.AtomicInteger idx = new java.util.concurrent.atomic.AtomicInteger(0);

            // Use a generated stream that polls the chest until it returns null; distribute items round-robin.
            java.util.stream.Stream.generate(chest::takeItem)
                .takeWhile(java.util.Objects::nonNull)
                .forEach(it -> {
                    int i = idx.getAndIncrement();
                    playable.GameCharacter p = playersList.get(i % playersList.size());
                    p.addItem(it);
                    stats.recordItemCollected(p.getName(), it);
                    System.out.println(p.getName() + " picks up " + it.getName() + " after the clown's defeat.");
                });
        }

        System.out.println("Encounter finished. Remaining loot: " + chest.remaining());
        stats.printSummary();

        System.out.println("Inventories:");
        java.util.stream.Stream.of(strongMan, fortuneTeller, acrobat)
            .map(p -> " - " + p.getName() + ": " + p.getInventory())
            .forEach(System.out::println);
    }
}
