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

public class GameManager {
    private final Game game;         
    private final Scanner in = new Scanner(System.in);

    public GameManager(Game game) { this.game = game; }

    public void startRound() {
        game.getDeck().reset();

        // Reset hands & collect bets
        for (Player p : game.getPlayers()) {
            p.resetForRound();
            System.out.println(p.getName() + " has " + p.getChips() + " chips.");
            int max = Math.max(1, p.getChips());
            int bet = readInt("Enter bet for " + p.getName() + " (1-" + max + "): ", 1, max);
            while (!p.placeBet(bet)) {
                bet = readInt("Invalid. Enter bet (1-" + max + "): ", 1, max);
            }
        }
        game.getDealer().resetForRound();

        // Initial deal: two cards to each player then dealer
        for (int i = 0; i < 2; i++) {
            for (Player p : game.getPlayers()) p.hit(game.getDeck());
            game.getDealer().hit(game.getDeck());
        }

        // Show table (hide dealer hole card)
        showTable(true);

        // Immediate blackjacks
        for (Player p : game.getPlayers()) {
            if (p.getHand().isBlackjack()) {
                if (!game.getDealer().getHand().isBlackjack()) {
                    System.out.println(p.getName() + " has Blackjack! (3:2)");
                    p.winBlackjack();
                }
            }
        }
    }

    public void handleTurns() {
        for (Player p : game.getPlayers()) {
            if (p.getHand().isBlackjack()) continue;
            boolean stood = false;
            while (!stood && !p.getHand().isBust()) {
                System.out.println("\n" + p.getName() + ": " + p.getHand());
                String choice = readString("(H)it or (S)tand? ").trim().toUpperCase(Locale.ROOT);
                if (choice.startsWith("H")) {
                    p.hit(game.getDeck());
                    Card last = p.getHand().getCards().get(p.getHand().getCards().size() - 1);
                    System.out.println(p.getName() + " drew " + last);
                    if (p.getHand().isBust()) {
                        System.out.println(p.getName() + " busts at " + p.getHand().getTotalValue());
                    }
                } else if (choice.startsWith("S")) {
                    stood = true;
                } else {
                    System.out.println("Enter H or S.");
                }
            }
        }
    }

    public void dealerPlaysAndSettle() {
        // If everyone busted, dealer need not play
        boolean anyAlive = game.getPlayers().stream().anyMatch(p -> !p.getHand().isBust() && !p.getHand().isBlackjack());
        if (anyAlive) {
            System.out.println("\nDealer reveals: " + game.getDealer().getHand());
            if (!game.getDealer().getHand().isBlackjack()) {
                game.getDealer().autoPlay(game.getDeck());
            }
            System.out.println("Dealer stands with: " + game.getDealer().getHand());
        } else {
            System.out.println("\nAll players busted. Dealer does not play.");
        }

        int dealerTotal = game.getDealer().getHand().getTotalValue();
        boolean dealerBust = game.getDealer().getHand().isBust();

        for (Player p : game.getPlayers()) {
            int pt = p.getHand().getTotalValue();
            boolean pb = p.getHand().isBust();

            if (p.getHand().isBlackjack() && !game.getDealer().getHand().isBlackjack()) {
                // already paid in startRound()
                continue;
            }
            if (pb) {
                System.out.println(p.getName() + " loses (bust).");
                p.lose();
            } else if (dealerBust) {
                System.out.println("Dealer busts — " + p.getName() + " wins.");
                p.winEven();
            } else if (game.getDealer().getHand().isBlackjack() && !p.getHand().isBlackjack()) {
                System.out.println("Dealer has Blackjack — " + p.getName() + " loses.");
                p.lose();
            } else if (pt > dealerTotal) {
                System.out.println(p.getName() + " beats dealer " + pt + " vs " + dealerTotal + ".");
                p.winEven();
            } else if (pt < dealerTotal) {
                System.out.println(p.getName() + " loses to dealer " + pt + " vs " + dealerTotal + ".");
                p.lose();
            } else {
                System.out.println(p.getName() + " pushes at " + pt + ".");
                p.push();
            }
        }

        System.out.println("\nChip status:");
        for (Player p : game.getPlayers()) {
            System.out.println(" - " + p.getName() + ": " + p.getChips());
        }
    }

    public void showTable(boolean hideDealerSecondCard) {
        System.out.println("\n=== TABLE ===");
        if (hideDealerSecondCard) {
            var dc = game.getDealer().getHand().getCards();
            if (dc.size() >= 2) {
                System.out.println("Dealer shows: [" + dc.get(0) + ", HIDDEN]");
            } else {
                System.out.println("Dealer: " + game.getDealer().getHand());
            }
        } else {
            System.out.println("Dealer: " + game.getDealer().getHand());
        }
        for (Player p : game.getPlayers()) {
            System.out.println(p.getName() + ": " + p.getHand());
        }
        System.out.println("=============");
    }

    private int readInt(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int v = Integer.parseInt(in.nextLine().trim());
                if (v < min || v > max) throw new NumberFormatException();
                return v;
            } catch (Exception e) {
                System.out.println("Enter a number between " + min + " and " + max + ".");
            }
        }
    }

    private String readString(String prompt) {
        System.out.print(prompt);
        return in.nextLine();
    }
}
