package playable;

import java.util.concurrent.atomic.AtomicInteger;
import main.Clown;

public abstract class GameCharacter {
    public static boolean partyAlive = true; // whether the party is still alive
    protected String name;
    protected Clown target; // shared enemy target
    public int hp; // health points

    public GameCharacter(int partyAlive, String name) {
        this.hp = 100; // default health points
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
                    target.clownAttack();
                }
                Thread.sleep(1000);
                hp -= target.clownAttack();
                System.out.println("the party now has " + hp + " health remaining.");
                if (hp <= 0) {
                    partyAlive = false;
                    System.out.println("Party has been defeated by the Clown!");
                    break;
                }
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
