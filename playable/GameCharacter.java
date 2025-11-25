package playable;

import java.util.concurrent.atomic.AtomicInteger;
import main.Clown;

public abstract class GameCharacter {
    protected boolean partyAlive;
    protected String name;
    protected Clown target; // shared enemy target
    protected boolean Attacked = false;

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

    public synchronized void run() {
        try {
            while (target != null && target.isAlive()) {
                if (Attacked == false) {
                    Attacked = true;
                    attack(); 
                    target.takeDamage(getAttackDamage(), name);
                } else {
                    Thread.sleep(1000);
                }
            }
        } catch (Exception e) {
            System.err.println(name + "'s adventure encountered an error: " + e.getMessage());
        }
        System.out.println(name + " has finished its actions.");
    };
    public abstract void attack();
    public abstract void defend();
    public abstract void interact();
}
