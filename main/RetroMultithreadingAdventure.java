package main;
import playable.Cleric;
import playable.Knight;
import playable.Thief;

public class RetroMultithreadingAdventure {
    public static void main(String[] args) {
        Knight knight = new Knight(0, null);
        Cleric cleric = new Cleric(0, null);
        Thief thief = new Thief(0, null);

        for (int i = 0; i < 2; i++) {
            if (i > 0) {
                System.out.println("A new adventure begins!");
            }
            Thread knightThread = new Thread(knight);
            Thread clericThread = new Thread(cleric);
            Thread thiefThread = new Thread(thief);

        
            knightThread.start();
            clericThread.start();
            thiefThread.start();

            try {
                knightThread.join();
                clericThread.join();
                thiefThread.join();
            } catch (InterruptedException e) {
                System.err.println("Adventure interrupted!");
            }
        }
        System.out.println("The adventure has concluded!");
    }
}
