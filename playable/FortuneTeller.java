package playable;

public class FortuneTeller extends GameCharacter implements Runnable {

    public FortuneTeller(int partyAlive, String name) {
        super(partyAlive, name);
    }

    @Override
    public void run() {
        super.run();
    }

    @Override
    public void attack() {
        System.out.println("Fortune Teller reads the cards and casts a spectral omen!");
        if (stats != null) stats.recordEvent(name + " attacked");
    }

    @Override
    public int getAttackDamage() {
        return 20;
    }

    @Override
    public void defend() {
        System.out.println("Fortune Teller conjures a veil of mist to protect the party!");
    }

    @Override
    public void interact() {
        System.out.println("Fortune Teller whispers cryptic prophecies to the party.");
    }
}
