import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Deck {
	ArrayList<Card> deck;
	
	public Deck() { //initializes a deck of a standard 52 card deck
		String[] suits = {"♠", "♣", "♡", "♢"};
		String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
		deck = new ArrayList<>();
		for (String suit : suits) {
			for (String value : values) {
				deck.add(new Card(suit, value));
			}
		}
	}
	
	public String toString() { //string of deck for testing purposes
		String toReturn = "";
		for (Card card : deck) {
			toReturn += card.toString() + ", ";
		}
		return toReturn;
	}
	
	public Queue<Card> shuffle() { // shuffles the arrayList of cards by swapping the last card with a random
		for(int i=51;i>=0;i--) { // other card ,then moves one closer to start.
			int j = (int) (Math.random()*i+1);
			Card temp = deck.get(i);
			deck.set(i, deck.get(j));
			deck.set(j,temp);
		}
		Queue<Card> dek = new LinkedList<>(); //feeds in the shuffled deck into a queue for drawing purposes
		for (Card c : deck) {
			dek.add(c);
		}
		return dek;
	}
	
}
