package playable;

public class Cleric extends GameCharacter implements Runnable {

    public Cleric(int partyAlive, String name) {
        super(partyAlive, name);
    }

    @Override
    public void run() {
        super.run();
    }

    @Override
    public void attack() {
        System.out.println("Cleric attacks with a mace!");
        if (stats != null) stats.recordEvent(name + " attacked");
    }

    @Override
    public int getAttackDamage() {
        return 20;
    }

    @Override
    public void defend() {
        System.out.println("Casts a protective spell!");
    }

    @Override
    public void interact() {
        System.out.println("Cleric heals the party");
    }
}
