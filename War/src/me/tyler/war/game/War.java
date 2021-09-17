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

    private final LinkedList<Card> gameDeck, playerOne, playerTwo;
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
        for(int i=0; i<this.gameDeck.size(); i++) {
            Card changingCard = this.gameDeck.get(i);
            this.gameDeck.remove(i);

            int newPosition = ThreadLocalRandom.current().nextInt(0, this.gameDeck.size()-1);
            this.gameDeck.add(changingCard, newPosition);
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
        while(this.playerOne.size() != 0 || this.playerTwo.size() != 0) {
            System.out.println("(Round " + round.getAndIncrement() + ") Player 1 has " + this.playerOne.size() + " cards. Player 2 has " + this.playerTwo.size() + " cards.\n");

            System.out.println("Press enter to deal cards.");
            this.scanner.nextLine();
            dealRound();
        }

        System.out.println("Player " + (this.playerOne.size() > this.playerTwo.size() ? "1" : "2") + " wins the game!");
    }

    private void dealRound() {
        // get the first cards from each deck and compare them to eachother
        Card playerOneCard = this.handleCard(this.playerOne);
        Card playerTwoCard = this.handleCard(this.playerTwo);

        System.out.println("Player 1 plays the " + playerOneCard.getNumber() + " of " + playerOneCard.getSuit());
        System.out.println("Player 2 plays the " + playerTwoCard.getNumber() + " of " + playerTwoCard.getSuit());
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
            cards.add(this.handleCard(this.playerOne));
            cards.add(this.handleCard(this.playerTwo));
        }

        Card playerOneCard = this.handleCard(this.playerOne);
        Card playerTwoCard = this.handleCard(this.playerTwo);

        cards.add(playerOneCard);
        cards.add(playerTwoCard);

        System.out.println("Player 1 plays the " + playerOneCard.getNumber() + " of " + playerOneCard.getSuit());
        System.out.println("Player 2 plays the " + playerTwoCard.getNumber() + " of " + playerTwoCard.getSuit());

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
        return linkedList.remove(1);
    }

    public static War getInstance() {
        return instance;
    }
}
