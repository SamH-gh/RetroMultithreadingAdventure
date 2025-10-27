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

    public abstract void run();
    public abstract void attack();
    public abstract void defend();
    public abstract void interact();
}
