package main;

public class RetroMultithreadingAdventure {
    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                System.out.println("party approaches monster 1");
                // PlayerThreadControl.main(args);
            }
            if (i == 1) {
                System.out.println("party approaches monster 2");
            }
            PlayerThreadControl.main(args);
        }
    }
}