package playable;

public abstract class GameCharacter {
    protected boolean partyAlive;
    protected String name;

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

    public synchronized void run() {
        try {
            for (int i = 1; i <= 3; i++) {
                System.out.println(name + ": Step" + i);
                if (i == 1) {
                    attack();
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
        }
        System.out.println(name + " has slain the dragon!");
    };
    public abstract void attack();
    public abstract void defend();
    public abstract void interact();
}
