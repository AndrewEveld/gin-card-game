import java.util.ArrayList;

public class Run {
	boolean isValid;
	ArrayList<Card> cards;
	int length;
	
	public Run(Card start) {
		cards = new ArrayList<>();
		cards.add(start);
		isValid = false;
		length = 1;
	}
	
	public void addCard(Card newCard) { //adds card to list of cards if the card fits into the run
		Card preceding = cards.get(length - 1);
		if (preceding.getIntValue() + 1 == newCard.getIntValue() && preceding.getSuit().equals(newCard.getSuit())) {
			cards.add(newCard);
			length++;
			if (length >= 3) isValid = true;
		}
	}


	public boolean isValid() { // checks to see if the run is at least 3 cards long
		return isValid;
	}
	
	@Override
	public String toString() {
		String toPrint = "[";
		for (int i = 0; i < cards.size(); i++) {
			if (i == cards.size() - 1) {
				toPrint += cards.get(i) + "]";
			} else {
				toPrint += cards.get(i) + ", ";
			}
		}
		return toPrint;
	}
}
