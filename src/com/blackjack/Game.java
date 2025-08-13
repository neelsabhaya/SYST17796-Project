/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blackjack;

/**
 *
 * @author neels
 */
import java.util.*;

public class Game {
    // Attributes from your class diagram
    private final List<Player> players = new ArrayList<>();
    private final Dealer dealer = new Dealer();
    private final Deck deck = new Deck();

    public List<Player> getPlayers() { return players; }
    public Dealer getDealer() { return dealer; }
    public Deck getDeck() { return deck; }

    /** Start – aligns with the diagram's +start(). */
    public void start() {
        try (Scanner in = new Scanner(System.in)) {
            System.out.print("How many players (1-4)? ");
            int n = readInt(in, 1, 4);
            for (int i = 1; i <= n; i++) {
                System.out.print("Enter player " + i + " name: ");
                String name = in.nextLine().trim();
                if (name.isEmpty()) name = "Player" + i;
                players.add(new Player(name, 100)); 
            }
            System.out.println("All players start with 100 chips.\n");

            GameManager gm = new GameManager(this);

            boolean again = true;
            while (again) {
                gm.startRound();         
                gm.handleTurns();        
                gm.dealerPlaysAndSettle(); 
                System.out.print("\nPlay another round? (y/n): ");
                again = in.nextLine().trim().toLowerCase().startsWith("y");
            }
            System.out.println("Thanks for playing!");
        }
    }

    private int readInt(Scanner in, int min, int max) {
        while (true) {
            try {
                int v = Integer.parseInt(in.nextLine().trim());
                if (v < min || v > max) throw new NumberFormatException();
                return v;
            } catch (Exception e) {
                System.out.print("Enter a number between " + min + " and " + max + ": ");
            }
        }
    }

    public static void main(String[] args) {
        new Game().start();
    }
}
