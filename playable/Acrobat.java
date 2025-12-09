package playable;

public class Acrobat extends GameCharacter implements Runnable {

    public Acrobat(int partyAlive, String name) {
        super(partyAlive, name);
    }

    @Override
    public void run() {
        super.run();
    }

    @Override
    public void attack() {
        System.out.println("Acrobat strikes with a baton!");
        if (stats != null) stats.recordEvent(name + " attacked");
    }

    @Override
    public int getAttackDamage() {
        return 25;
    }

    @Override
    public void defend() {
        System.out.println("Acrobat flips away to dodge the attack!");
    }

    @Override
    public void interact() {
        System.out.println("Acrobat performs an impressive somersault!");
    }
}
