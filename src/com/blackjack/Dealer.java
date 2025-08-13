/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blackjack;

/**
 *
 * @author neels
 */
public class Dealer extends Player {
    public Dealer() { super("Dealer", Integer.MAX_VALUE); }

    /** Dealer plays by the house rule: hit below 17, stand on 17+. */
    public void autoPlay(Deck deck) {
        while (hand.getTotalValue() < 17) {
            hit(deck);
        }
    }
}
