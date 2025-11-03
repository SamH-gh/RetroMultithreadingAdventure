package playable;

import java.util.Random;

public class Thief extends GameCharacter implements Runnable {

    public Thief(int partyAlive, String name) {
        super(partyAlive, name);
    }

    @Override
    public synchronized void run() {
        System.out.println("Thief sets out to slay the dragon!");
        Random rand = new Random();
        try {
            for (int i = 1; i <= 2; i++) {
                System.out.println("Thief: Step" + i);
                if (i == 1) {
                    System.out.println("Thief encounters a fierce dragon!");
                }
                if (i == 2 ) {
                    System.out.println("Thief battles the dragon!");
                }
                Thread.sleep(rand.nextInt(1000) + 500);
            }
        } catch (InterruptedException e) {
            System.err.println("Thief's adventure was interrupted!");
        }
        System.out.println("Thief's has slain the dragon!");
    }

    @Override
    public void attack() {
        System.out.println("Thief attacks with a sword!");
    }

    @Override
    public void defend() {
        System.out.println("Thief raises his shield to defend!");
    }

    @Override
    public void interact() {
        System.out.println("Thief shouts a battle cry!");
    }
}
