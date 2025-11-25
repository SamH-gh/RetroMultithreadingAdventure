package playable;

import java.util.concurrent.atomic.AtomicInteger;
import main.Clown;

public abstract class GameCharacter {
    protected boolean partyAlive;
    protected String name;
    protected Clown target; // shared enemy target
    

    public GameCharacter(int partyAlive, String name) {
        this.partyAlive = true;
        this.name = name;
    }

    public AtomicInteger getClownHealth() {
        return target != null ? new AtomicInteger(target.getHp()) : new AtomicInteger(0);
    }

    /**
     * Public accessor for the character name so other packages can identify players.
     */
    public String getName() {
        return name;
    }

    public void setTarget(Clown target) {
        this.target = target;
    }

    /**
     * Return how much damage this character deals when attacking.
     */
    public abstract int getAttackDamage();

    public void run() {
        try {
            while (target != null && target.isAlive()) {
                // synchronize on the shared target so an attack (attack print + damage application)
                // happens atomically relative to other threads attacking the same Clown.
                synchronized (target) {
                    attack();
                    target.takeDamage(getAttackDamage(), name);
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println(name + "'s thread was interrupted.");
        } catch (Exception e) {
            System.err.println(name + "'s adventure encountered an error: " + e.getMessage());
        }
        // System.out.println(name + " has finished its actions.");
    }
    public abstract void attack();
    public abstract void defend();
    public abstract void interact();
}
