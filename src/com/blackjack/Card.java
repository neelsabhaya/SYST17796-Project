/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blackjack;

/**
 *
 * @author neels
 */
public final class Card {
    public enum Suit { CLUBS, DIAMONDS, HEARTS, SPADES }
    public enum Rank {
        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6),
        SEVEN(7), EIGHT(8), NINE(9), TEN(10),
        JACK(10), QUEEN(10), KING(10), ACE(11);
        private final int value;
        Rank(int v) { this.value = v; }
        public int getValue() { return value; }
    }

    private final Suit suit;
    private final Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() { return suit; }
    public Rank getRank() { return rank; }
    public int getValue() { return rank.getValue(); }

    @Override
    public String toString() {
        String r = rank.name().substring(0,1) + rank.name().substring(1).toLowerCase();
        String s = suit.name().substring(0,1) + suit.name().substring(1).toLowerCase();
        return r + " of " + s;
    }
}
