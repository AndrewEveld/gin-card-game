import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;

public class Player {
	ArrayList<Card> hand;
	int score;
	int deadwood;
	ArrayList<Sets> curSets;
	ArrayList<Run> curRuns;
	
	public Player() {
		hand = new ArrayList<Card>();
		score = 0;
		deadwood = 0;
		curSets = new ArrayList<>();
		curRuns = new ArrayList<>();
	}
	
	public int getScore() { // score getter
		return score;
	}

	public void setScore(int score) { //score setter
		this.score = score;
	}

	public int getDeadwood() { //calculates the sum of the values of the cards that don't fit into any sets/runs.
		deadwood = 0;
		for (Card c : hand) {
			int val = c.getIntValue();
			if(val<10)
				deadwood+= val;
			else
				deadwood+=10;
		}
		return deadwood;
	}

	public void addCard(Card card) { // adds card  to the hand and insertions sorts the card into the correct place.
		hand.add(card);
		int i = hand.size() - 2;
		while (i>=0 && hand.get(i).getIntValue() > card.getIntValue()) {
			Card toSwitch = hand.get(i);
			hand.set(i, card);
			hand.set(i + 1, toSwitch);
			i--;
		}
	}
	public void draw(Queue<Card> d) { // draws card from a queue of cards.
		addCard(d.remove());
	}
	
	public Card remove(String value, String suit) { // returns and removes the card from hand if card is in the hand.
		String convertValue = convert(value);
		String convertSuit = convert(suit);
		if (convertSuit == null || convertValue == null) {
			return null;
		}
		for(Card c : hand) {
			if (convertValue.equals(c.getValue()) && convertSuit.equals(c.getSuit())) {
				hand.remove(c);
				return c;
			}
		}
		return null;
	}
	public void printSets() { // prints list of possible sets (not including cards currently in runs/sets.
		ArrayList<Sets> possSets = new ArrayList<>();
		for (int i = 0; i < hand.size(); i++) {
			possSets.add(new Sets(hand.get(i)));
			for (int j = i + 1; j < hand.size(); j++) {
				possSets.get(i).addCard(hand.get(j));
			}
		}
		//System.out.println(possSets);
		String validSets = "";
		for (Sets set : possSets) {
			if (set.isValid()) {
				validSets += set + " ";
			}
		}
		if (validSets.length() > 0) {
			System.out.println(validSets);
		}
	}
	
	public void buildSet() { // asks players for cards to build a set out of.
		Scanner sc = new Scanner(System.in);
		System.out.println("What is the first card of the set you wish to add?");
		Command com = new Command(sc.nextLine());
		Card addCard = remove(com.getValue().toString(), com.getSuit().toString());
		while (addCard == null) {
			System.out.println("Invalid Card. Try Again: ");
			com = new Command(sc.nextLine());
			addCard = remove(com.getValue().toString(), com.getSuit().toString());
		}
		Sets newSet = new Sets(addCard);
		boolean done = false;
		while (!done) {
			System.out.println("What is the next card you wish to add? If done type 'QUIT'.");
			com = new Command(sc.nextLine());
			Card nextCard = null;
			if (com.getValue().toString().equals("QUIT")) {
				break;
			}
			else if (!com.getValue().equals(Value.UNKNOWN) && !com.getSuit().equals(Suits.UNKNOWN)) {
				nextCard = remove(com.getValue().toString(), com.getSuit().toString()); 
				}
			while (nextCard == null) {
				System.out.println("Invalid Card. Try Again: ");
				com = new Command(sc.nextLine());
				nextCard = remove(com.getValue().toString(), com.getSuit().toString());
			}
			int preLength = newSet.length;
			newSet.addCard(nextCard);
			if (preLength == newSet.length) {
				addCard(nextCard);
			}
			if (newSet.isValid()) {
				System.out.println("If done type 'QUIT', if not press enter/return.");
				com = new Command(sc.nextLine());
				if (com.getValue().toString().equals("QUIT")) {
					done = true;
				}
			}
		}
		if (newSet.isValid()) {
			curSets.add(newSet);
		} else {
			System.out.println("INVALID SET");
			for (Card card : newSet.cards) {
				addCard(card);
			}
		}
	}
	
	public void buildRun() { // asks players for cards to build a run out of.
		Scanner sc = new Scanner(System.in);
		System.out.println("What is the first card of the run you wish to add?");
		Command com = new Command(sc.nextLine());
		Card addCard = remove(com.getValue().toString(), com.getSuit().toString());
		while (addCard == null) {
			System.out.println("Invalid Card. Try Again: ");
			com = new Command(sc.nextLine());
			addCard = remove(com.getValue().toString(), com.getSuit().toString());
		}
		Run newRun = new Run(addCard);
		boolean done = false;
		while (!done) {
			System.out.println("What is the next card you wish to add? If done type 'QUIT'.");
			com = new Command(sc.nextLine());
			Card nextCard = null;
			if (com.getValue().toString().equals("QUIT")) {
				break;
			}
			else if (!com.getValue().equals(Value.UNKNOWN) && !com.getSuit().equals(Suits.UNKNOWN)) {
				nextCard = remove(com.getValue().toString(), com.getSuit().toString()); 
				}
			while (nextCard == null) {
				System.out.println("Invalid Card. Try Again: ");
				com = new Command(sc.nextLine());
				nextCard = remove(com.getValue().toString(), com.getSuit().toString());
			}
			int preLength = newRun.length;
			newRun.addCard(nextCard);
			if (preLength == newRun.length) {
				addCard(nextCard);
			}
			if (newRun.isValid()) {
				System.out.println("If done type 'QUIT', if not press enter/return.");
				com = new Command(sc.nextLine());
				if (com.getValue().toString().equals("QUIT")) {
					done = true;
				}
			}
		}
		if (newRun.isValid()) {
			curRuns.add(newRun);
		} else {
			System.out.println("INVALID RUN");
			for (Card card : newRun.cards) {
				addCard(card);
			}
		}
	}
	
	public void printRuns() { // prints a list of possible runs (not including cards currently in runs/sets.
		ArrayList<Run> possRuns = new ArrayList<>();
		for (int i = 0; i < hand.size(); i++) {
			possRuns.add(new Run(hand.get(i)));
			for (int j = i + 1; j < hand.size(); j++) {
				possRuns.get(i).addCard(hand.get(j));
			}
		}
		//System.out.println(possRuns);
		String validRuns = "";
		for (Run run : possRuns) {
			if (run.isValid()) {
				validRuns += run + " ";
			}
		}
		if (validRuns.length() > 0) {
			System.out.println(validRuns);
		}
	}
	
	
	public void addRun() { // loops through all the runs and prompts the player if they wish to add a card to each run.
		Scanner sc = new Scanner(System.in);
		for (Run run : curRuns) {
			System.out.println(run);
			System.out.println("Would you like to add to this run?");
			String yesNo = sc.nextLine();
			while (!yesNo.equals("yes") && !yesNo.equals("no")) {
				System.out.println("Please answer only 'yes' or 'no':");
				yesNo = sc.nextLine();
			}
			if(yesNo.equals("yes")) {
				System.out.println("Which card would you like to add?");
				Command com = new Command(sc.nextLine());
				Card addCard = remove(com.getValue().toString(), com.getSuit().toString());
				while (addCard == null) {
					System.out.println("Invalid Card. Try Again: To quit type 'QUIT'. ");
					if (com.getValue().equals(Value.QUIT)) {break;}
					com = new Command(sc.nextLine());
					addCard = remove(com.getValue().toString(), com.getSuit().toString());
				}
				run.addCard(addCard);
			}
		}
		
	}
	
	public void addSet() { // loops through all of the sets and prompts the player if they wish to add a card to set.
		Scanner sc = new Scanner(System.in);
		for (Sets set : curSets) {
			System.out.println(set);
			System.out.println("Would you like to add to this set?");
			String yesNo = sc.nextLine();
			while (!yesNo.equals("yes") && !yesNo.equals("no")) {
				System.out.println("Please answer only 'yes' or 'no':");
				yesNo = sc.nextLine();
			}
			if(yesNo.equals("yes")) {
				System.out.println("Which card would you like to add?");
				Command com = new Command(sc.nextLine());
				Card addCard = remove(com.getValue().toString(), com.getSuit().toString());
				while (addCard == null) {
					System.out.println("Invalid Card. Try Again: To quit type 'QUIT'. ");
					if (com.getValue().equals(Value.QUIT)) {break;}
					com = new Command(sc.nextLine());
					addCard = remove(com.getValue().toString(), com.getSuit().toString());
				}
				set.addCard(addCard);
			}
		}
		
	}
	
	public void dissolve() { // puts all cards in curSets and curRuns into hand and reinitializes curRuns/curSets.
		for (Run run : curRuns) {
			for (Card card : run.cards) {
				addCard(card);
			}
		}
		for (Sets set : curSets) {
			for (Card card : set.cards) {
				addCard(card);
			}
		}
		curRuns = new ArrayList<>();
		curSets = new ArrayList<>();
		System.out.println("Sets and runs dissolved");
	}
	
	public int knock(Card c) { //check if a knock is valid.
		int sum = 0;
		sum = getDeadwood();
		if(sum<=10) {
			System.out.println("Valid knock");
			return sum;
		}
		else {
			System.out.println("Invalid knock");
			addCard(c);
			return -1; 
		}
	}
	
	public String convert(String val) { // converts string values/suits into the single character values/suits.
		if(val.equals("ACE"))
			return "A";
	    else if(val.equals("TWO"))
			return "2";
	    else if(val.equals("THREE"))
			return "3";
	    else if(val.equals("FOUR"))
			return "4";
	    else if(val.equals("FIVE"))
			return "5";
	    else if(val.equals("SIX"))
			return "6";
	    else if(val.equals("SEVEN"))
			return "7";
	    else if(val.equals("EIGHT"))
			return "8";
	    else if(val.equals("NINE"))
			return "9";
	    else if(val.equals("TEN"))
			return "10";
	    else if(val.equals("JACK"))
			return "J";
	    else if(val.equals("QUEEN"))
			return "Q";
	    else if(val.equals("KING"))
			return "K";
	    else if(val.equals("DIAMONDS"))
			return "♢";
	    else if(val.equals("CLUBS"))
			return "♣";
	    else if(val.equals("SPADES"))
			return "♠";
	    else if(val.equals("HEARTS"))
			return "♡";
	    else
	    	return null;
	}
}

