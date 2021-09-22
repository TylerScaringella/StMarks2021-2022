package me.tyler.war.game;

import me.tyler.war.util.LinkedList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class War {

    private static War instance;

    private LinkedList<Card> gameDeck, playerOne, playerTwo;
    private final Scanner scanner;

    public War() {
        this.gameDeck = new LinkedList<>();
        this.playerOne = new LinkedList<>();
        this.playerTwo = new LinkedList<>();

        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        instance = new War();
        instance.startGame();
    }

    private void startGame() {
        addInitialCards();
        shuffleCards();
        giveCards();
        startRounds();
    }

    private void addInitialCards() {
        List<String> suits = Arrays.asList(
                "Spades",
                "Hearts",
                "Diamonds",
                "Clubs"
        );

        // Ace(1)-10, Jack (11), Queen (12), King (13)

        for(String suit : suits) {
            for(int i=1; i<=13; i++) {
                this.gameDeck.add(new Card(suit, i));
            }
        }
    }

    private void shuffleCards() {
        // randomize cards
        for(int i=0; i<this.gameDeck.size(); i++) {
            Card changingCard = this.gameDeck.get(i);
            this.gameDeck.remove(i);

            int newPosition = ThreadLocalRandom.current().nextInt(0, this.gameDeck.size()-1);
            this.gameDeck.add(changingCard, newPosition);
        }

        // Shuffling algorithm | Not working


        // first half
        LinkedList<Card> firstHalf = new LinkedList<>();
        for(int i=0; i<26; i++) {
            Card removingCard = this.gameDeck.remove(0);
            firstHalf.add(removingCard, i);
        }

        System.out.println(firstHalf.size());

        System.out.println(this.gameDeck.size());

        LinkedList<Card> secondHalf = new LinkedList<>();
        for(int i=0; i<25; i++) {
            Card removingCard = this.gameDeck.remove(0);
            secondHalf.add(removingCard, i);
        }

        // combine cards (pack groups together in 1-3)

        while(firstHalf.size() >= 1 || secondHalf.size() >= 1) {
            // first group
            int firstTogether = ThreadLocalRandom.current().nextInt(1, 3);
            for(int i=0; i<firstTogether; i++) {
                if(firstHalf.size() > 0) {
                    Card addingCard = firstHalf.remove(0);
                    this.gameDeck.add(addingCard);
                }
            }

            // second group
            int secondTogether = ThreadLocalRandom.current().nextInt(1, 3);
            for(int i=0; i<secondTogether; i++) {
                if(secondHalf.size() > 0) {
                    Card addingCard = secondHalf.remove(0);
                    this.gameDeck.add(addingCard);
                }
            }
        }

        for(int i=0; i<this.gameDeck.size(); i++) {
            System.out.println(this.gameDeck.get(i).getNumber() + " of " + this.gameDeck.get(i).getSuit());
        }
    }

    private void giveCards() {
        // Give player one cards
        for(int i=0; i<(this.gameDeck.size() / 2); i++) {
            this.playerOne.add(this.gameDeck.get(i));
        }

        // Give player two cards
        for(int i=(this.gameDeck.size() / 2); i<this.gameDeck.size(); i++) {
            this.playerTwo.add(this.gameDeck.get(i));
        }
    }

    private void startRounds() {
        AtomicInteger round = new AtomicInteger(1);
        while(this.playerOne.size() > 0 || this.playerTwo.size() > 0) {
            if(this.playerOne.size() == 0 || this.playerTwo.size() == 0) {
                System.out.println("Player " + (this.playerOne.size() > this.playerTwo.size() ? "1" : "2") + " wins the game!");
                break;
            }
            System.out.println("(Round " + round.getAndIncrement() + ") Player 1 has " + this.playerOne.size() + " cards. Player 2 has " + this.playerTwo.size() + " cards.\n");

            System.out.println("Press enter to deal cards.");
            this.scanner.nextLine();
            dealRound();
        }
    }

    private void dealRound() {
        // get the first cards from each deck and compare them to eachother
        Card playerOneCard = this.handleCard(this.playerOne);
        Card playerTwoCard = this.handleCard(this.playerTwo);

        System.out.println("Player 1 plays the " + playerOneCard.toString());
        System.out.println("Player 2 plays the " + playerTwoCard.toString());
        if(playerOneCard.getNumber() == playerTwoCard.getNumber()) {
            System.out.println("War!");
            this.handleWar(Arrays.asList(
                    playerOneCard,
                    playerTwoCard
            ));
        } else if(playerOneCard.getNumber() > playerTwoCard.getNumber()){
            System.out.println("Player 1 wins the round!");
            this.playerOne.add(playerOneCard);
            this.playerOne.add(playerTwoCard);
        } else {
            System.out.println("Player 2 wins the round!");
            this.playerTwo.add(playerOneCard);
            this.playerTwo.add(playerTwoCard);
        }
    }

    // fake war doesnt actually work (it does but its not the real thing)
    private void handleWar(List<Card> ogCards) {
        List<Card> cards = new ArrayList<>(ogCards);


        for(int i=0; i<3; i++) {
            if(this.playerOne.size() > 2) cards.add(this.handleCard(this.playerOne));
            if(this.playerTwo.size() > 2) cards.add(this.handleCard(this.playerTwo));
        }

        Card playerOneCard = this.handleCard(this.playerOne);
        Card playerTwoCard = this.handleCard(this.playerTwo);

        cards.add(playerOneCard);
        cards.add(playerTwoCard);

        System.out.println("Player 1 plays the " + playerOneCard.toString());
        System.out.println("Player 2 plays the " + playerTwoCard.toString());

        if(playerOneCard.getNumber() == playerTwoCard.getNumber()) {
            System.out.println("War!");
            this.handleWar(cards);
        } else if(playerOneCard.getNumber() > playerTwoCard.getNumber()){
            System.out.println("Player 1 wins the war!");
            cards.forEach(this.playerOne::add);
        } else {
            System.out.println("Player 2 wins the war!");
            cards.forEach(this.playerTwo::add);
        }
    }

    private Card handleCard(LinkedList<Card> linkedList) {
        return linkedList.remove(0);
    }

    public static War getInstance() {
        return instance;
    }
}
