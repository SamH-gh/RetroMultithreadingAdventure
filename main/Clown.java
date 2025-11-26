package main;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Clown {
    private final AtomicInteger health;
    public boolean isDefeated = false;

    public Clown(int initialHp) {
        this.health = new AtomicInteger(initialHp);
    }

    public Clown() {
        this(75);
    }

    /**
     * Apply damage to the clown in a thread-safe way and return remaining HP.
     */
    public int takeDamage(int damage, String attackerName) {
        if (damage <= 0) return health.get();
        int remaining = health.addAndGet(-damage);
        System.out.println(attackerName + " deals " + damage + " damage to the Clown, clown is now at " + Math.max(remaining, 0) + " health!");
        if (remaining <= 0) {
            System.out.println("The Clown has been defeated by " + attackerName + "!");
        }
        return remaining;
    }

    public int getHp() {
        return health.get();
    }

    public boolean isAlive() {
        return health.get() > 0;
    }

    public void tellJoke() {
        JokeAPI.main(new String[0]);
    }

    /**
     * Clown attack: returns damage amount and prints action.
     */
    public int clownAttack() {
        Random rand = new Random();
        int damage = rand.nextInt(11) + 5; // 5..15
        System.out.println("Clown attacks with a pie, dealing " + damage + " damage!");
        return damage;
    }
}
