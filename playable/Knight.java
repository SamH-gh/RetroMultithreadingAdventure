package playable;

public class Knight extends GameCharacter implements Runnable {

    public Knight(int partyAlive, String name) {
        super(partyAlive, name);
    } 

    @Override
    public void run() {
        super.run();
    }

    @Override
    public void attack() {
        System.out.println("Knight attacks with a sword!");
    }
    
    @Override
    public int getAttackDamage() {
        return 30;
    }

    @Override
    public void defend() {
        System.out.println("Knight raises his shield to defend!");
    }

    @Override
    public void interact() {
        System.out.println("Knight shouts a battle cry!");
    }
}