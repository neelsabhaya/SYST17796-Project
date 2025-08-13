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

public class Deck {
    private final Deque<Card> cards = new ArrayDeque<>();

    public Deck() { reset(); }

    /** Build a fresh 52-card deck and shuffle. */
    public final void reset() {
        cards.clear();
        List<Card> list = new ArrayList<>(52);
        for (Card.Suit s : Card.Suit.values()) {
            for (Card.Rank r : Card.Rank.values()) {
                list.add(new Card(s, r));
            }
        }
        Collections.shuffle(list, new Random());
        for (Card c : list) cards.push(c);
    }

    /** Draw one card from top of deck (auto-resets if empty). */
    public Card drawCard() {
        if (cards.isEmpty()) reset();
        return cards.pop();
    }

    public int remaining() { return cards.size(); }
}
