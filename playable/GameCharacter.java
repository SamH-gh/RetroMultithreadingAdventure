package playable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import main.Clown;
import main.GameStats;
import main.Item;
import main.TreasureChest;

public abstract class GameCharacter {
    // Global flag used by RetroMultithreadingAdventure to determine if party is alive
    public static volatile boolean partyAlive = true;
    protected boolean partyAliveInstance;
    protected String name;
    protected Clown target; // shared enemy target
    protected List<Item> inventory = Collections.synchronizedList(new ArrayList<>());
    protected TreasureChest chest;
    protected GameStats stats;

    public GameCharacter(int partyAlive, String name) {
        this.partyAlive = true;
        this.name = name;
    }

    // Public accessor for the character name so other packages can identify players.
    public String getName() {
        return name;
    }

    // Set the shared target clown for this character
    public void setTarget(Clown target) {
        this.target = target;
    }

    public void setChest(TreasureChest chest) { this.chest = chest; }
    public void setStats(GameStats stats) { this.stats = stats; }
    public List<Item> getInventory() { return inventory; }
    public void addItem(Item i) { if (i != null) inventory.add(i); }

    // Return how much damage this character deals when attacking.
    public abstract int getAttackDamage();

    // Perform a single numbered step for this character (1=attack,2=defend,3=interact).
    // This allows the engine to schedule steps individually.
    public void performStep(int step) {
        // Map step numbers to actions using lambdas/method references for clarity
        java.util.Map<Integer, Runnable> actions = java.util.Map.of(
            1, () -> {
                attack();
                if (target != null && target.isAlive()) {
                    target.takeDamage(getAttackDamage(), name);
                }
            },
            2, this::defend,
            3, this::interact
        );

        actions.getOrDefault(step, () -> {}).run();
    }

    public synchronized void run() {
        try {
            for (int i = 1; i <= 3; i++) {
                if (i == 1) {
                    attack();
                    if (target != null && target.isAlive()) {
                        target.takeDamage(getAttackDamage(), name);
                    }
                }
                if (i == 2 ) {
                    defend();
                }
                if (i == 3) {
                    interact();
                }
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            System.err.println(name + "'s adventure was interrupted!");
            Thread.currentThread().interrupt();
        }
        System.out.println(name + " has finished its actions.");
    };
    public abstract void attack();
    public abstract void defend();
    public abstract void interact();
}
