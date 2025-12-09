package main;
import java.util.Scanner;
import playable.GameCharacter;

public class RetroMultithreadingAdventure {

    // Entry point for the Retro Multithreading Adventure game
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Haunted Carnival!");

        // Main game loop for multiple encounters
        int encounterNumber = 4;
        for (int i = 0; i < encounterNumber; i++) {
            if (GameCharacter.partyAlive == false) {
                System.out.println("The party has been defeated. Game over.");
                break;
            }

            System.out.println("The party approaches an undead clown telling jokes...");
            Clown clown = new Clown();
            clown.tellJoke();
            System.out.println("Was that joke funny? ('yes' or 'no') If not, prepare to fight!");
            String playerResponse = scanner.nextLine();
            playerResponse = playerResponse.toLowerCase();
            
            if (playerResponse.equals("no")) {
                // Start the encounter
                GameEngine.runEncounter();
            } else {
                System.out.println("The party decides the joke was funny and spares the clown.");
            }
        }
        scanner.close();
        if (GameCharacter.partyAlive) {
            System.out.println("You survived the haunted carnival!");
        }
    }
}