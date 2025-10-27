package playable;
import java.util.Random;

public class Knight extends GameCharacter implements Runnable {

    public Knight(int partyAlive, String name) {
        super(partyAlive, name);
    }
    public void run() {
        System.out.println("Knight sets out to slay the dragon!");
        Random rand = new Random();
        try {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Knight: Step" + i);
                if (i == 3) {
                    System.out.println("Knight encounters a fierce dragon!");
                }
                if (i == 4 ) {
                    System.out.println("Knight battles the dragon!");
                }
                Thread.sleep(rand.nextInt(1000) + 500);
            }
        } catch (InterruptedException e) {
            System.err.println("Knight's adventure was interrupted!");
        }
        System.out.println("Knight's has slain the dragon!");
    }

    @Override
    public void attack() {
        System.out.println("Knight attacks with a sword!");
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