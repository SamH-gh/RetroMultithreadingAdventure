package playable;

import java.util.Random;

public class Cleric extends GameCharacter implements Runnable {

    public Cleric(int partyAlive, String name) {
        super(partyAlive, name);
    }
    public void run() {
        System.out.println("Cleric sets out to slay the dragon!");
        Random rand = new Random();
        try {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Cleric: Step" + i);
                if (i == 3) {
                    System.out.println("Cleric encounters a fierce dragon!");
                }
                if (i == 4 ) {
                    System.out.println("Cleric battles the dragon!");
                }
                Thread.sleep(rand.nextInt(1000) + 500);
            }
        } catch (InterruptedException e) {
            System.err.println("Cleric's adventure was interrupted!");
        }
        System.out.println("Cleric's has slain the dragon!");
    }

    @Override
    public void attack() {
        System.out.println("Cleric attacks with a sword!");
    }

    @Override
    public void defend() {
        System.out.println("Cleric raises his shield to defend!");
    }

    @Override
    public void interact() {
        System.out.println("Cleric shouts a battle cry!");
    }
}
