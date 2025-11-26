package main;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import playable.Cleric;
import playable.Knight;
import playable.Thief;

public class GameEngine {
    public static void runEncounter() {
        // Use Clown default constructor (default HP set in Clown.java)
        Clown clown = new Clown();
        TreasureChest chest = new TreasureChest();
        GameStats stats = new GameStats();

        // shared party health (characters share this pool)
        java.util.concurrent.atomic.AtomicInteger partyHp = new java.util.concurrent.atomic.AtomicInteger(100);

        Knight knight = new Knight(0, "Knight");
        Cleric cleric = new Cleric(0, "Cleric");
        Thief thief = new Thief(0, "Thief");

        // share target and resources
        knight.setTarget(clown);
        cleric.setTarget(clown);
        thief.setTarget(clown);

        knight.setChest(chest); knight.setStats(stats);
        cleric.setChest(chest); cleric.setStats(stats);
        thief.setChest(chest); thief.setStats(stats);

        // We'll use an ExecutorService to control execution order of character steps.
        // A single-thread executor will run submitted Runnables sequentially.
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Clown will attack deterministically between character steps (no separate clown thread)

        // Execute per-step: for each step, run each character in order, then let the clown attack.
        java.util.List<playable.GameCharacter> players = java.util.Arrays.asList(knight, cleric, thief);
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
            System.out.println("Clown defeated — distributing loot to survivors...");
            java.util.List<playable.GameCharacter> playersList = java.util.Arrays.asList(knight, cleric, thief);
            int idx = 0;
            main.Item it;
            while ((it = chest.takeItem()) != null) {
                playable.GameCharacter p = playersList.get(idx % playersList.size());
                p.addItem(it);
                stats.recordItemCollected(p.getName(), it);
                System.out.println(p.getName() + " picks up " + it.getName() + " after the clown's defeat.");
                idx++;
            }
        }

        System.out.println("Encounter finished. Remaining loot: " + chest.remaining());
        stats.printSummary();

        System.out.println("Inventories:");
        System.out.println(" - " + knight.getName() + ": " + knight.getInventory());
        System.out.println(" - " + cleric.getName() + ": " + cleric.getInventory());
        System.out.println(" - " + thief.getName() + ": " + thief.getInventory());
    }
}
