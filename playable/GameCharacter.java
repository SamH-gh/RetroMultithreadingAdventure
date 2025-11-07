package playable;

import main.Clown;

public abstract class GameCharacter {
    protected boolean partyAlive;
    protected String name;
    protected Clown target; // shared enemy target

    public GameCharacter(int partyAlive, String name) {
        this.partyAlive = true;
        this.name = name;
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
            for (int i = 1; i <= 3; i++) {
                System.out.println(name + ": Step" + i);
                if (i == 1) {
                    attack();
                    // apply damage to target if available
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
