package playable;

public class StrongMan extends GameCharacter implements Runnable {

    public StrongMan(int partyAlive, String name) {
        super(partyAlive, name);
    } 

    @Override
    public void run() {
        super.run();
    }

    @Override
    public void attack() {
        System.out.println("Strong Man punches with his fists!");
        if (stats != null) stats.recordEvent(name + " attacked");
    }
    
    @Override
    public int getAttackDamage() {
        return 30;
    }

    @Override
    public void defend() {
        System.out.println("Strong Man raises his fists to defend!");
    }

    @Override
    public void interact() {
        System.out.println("Strong Man shows off his muscles!");
    }
}
