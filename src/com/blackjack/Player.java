/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blackjack;

/**
 *
 * @author neels
 */

public class Player {
    protected final String name;
    protected final Hand hand = new Hand();

    private int betAmount; 
    private int chips;      

    public Player(String name, int startingChips) {
        this.name = name;
        this.chips = startingChips;
    }

    public String getName() { return name; }
    public Hand getHand() { return hand; }
    public int getChips() { return chips; }
    public int getBetAmount() { return betAmount; }

    /** Place bet if player has enough chips. */
    public boolean placeBet(int amount) {
        if (amount <= 0 || amount > chips) return false;
        betAmount = amount;
        chips -= amount;
        return true;
    }

    /** Take one card from the deck. */
    public void hit(Deck deck) { hand.addCard(deck.drawCard()); }

    /** No-op here – marker so GameManager knows the player stands. */
    public void stand() {  }

    /** Round settlement helpers */
    public void winEven() { chips += betAmount * 2; betAmount = 0; }
    public void winBlackjack() { chips += (int)Math.round(betAmount * 2.5); betAmount = 0; }
    public void push() { chips += betAmount; betAmount = 0; }
    public void lose() { betAmount = 0; }

    public void resetForRound() {
        hand.clear();
        betAmount = 0;
    }
}
