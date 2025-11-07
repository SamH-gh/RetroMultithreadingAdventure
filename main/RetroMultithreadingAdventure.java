package main;
import java.util.Scanner;

public class RetroMultithreadingAdventure {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 2; i++) {
            System.out.println("The party approaches an undead clown telling jokes...");
            Clown clown = new Clown();
            clown.tellJoke();
            System.out.println("Was that joke funny? ('yes' or 'no') If not, prepare to fight!");
            String playerResponse = scanner.nextLine();
            playerResponse = playerResponse.toLowerCase();
            if (playerResponse.equals("no")) {
                // Start the threaded encounter; PlayerThreadControl creates and manages the fight
                PlayerThreadControl.main(args);
            } else {
                System.out.println("The party decides the joke was funny and spares the clown.");
            }
        }
        scanner.close();
        System.out.println("You survived the haunted carnival!");
    }
}