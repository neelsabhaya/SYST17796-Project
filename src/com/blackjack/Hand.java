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

public class Hand {
    private final List<Card> cards = new ArrayList<>();

    public void addCard(Card card) { cards.add(card); }

    public List<Card> getCards() { return Collections.unmodifiableList(cards); }

    /** Total with Ace = 11 then demoted to 1 while busting. */
    public int getTotalValue() {
        int sum = 0, aces = 0;
        for (Card c : cards) {
            sum += c.getValue();
            if (c.getRank() == Card.Rank.ACE) aces++;
        }
        while (sum > 21 && aces > 0) {
            sum -= 10; // demote one Ace from 11 to 1
            aces--;
        }
        return sum;
    }

    public boolean isBlackjack() { return cards.size() == 2 && getTotalValue() == 21; }

    public boolean isBust() { return getTotalValue() > 21; }

    public void clear() { cards.clear(); }

    @Override public String toString() {
        return cards.toString() + " (" + getTotalValue() + ")";
    }
}
