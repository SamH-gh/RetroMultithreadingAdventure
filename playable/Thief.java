package playable;

public class Thief extends GameCharacter implements Runnable {

    public Thief(int partyAlive, String name) {
        super(partyAlive, name);
    }

    @Override
    public void run() {
        super.run();
    }

    @Override
    public void attack() {
        System.out.println("Thief attacks with a dagger!");
        if (stats != null) stats.recordEvent(name + " attacked");
    }

    @Override
    public int getAttackDamage() {
        return 25;
    }

    @Override
    public void defend() {
        System.out.println("Thief dodges the attack!");
    }

    @Override
    public void interact() {
        System.out.println("Steals something");
    }
}
