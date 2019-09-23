import java.util.ArrayList;

public class Sets {
	boolean isValid;
	ArrayList<Card> cards;
	int length;
	String value;
	public Sets(Card start) {
		cards = new ArrayList<>();
		cards.add(start);
		isValid = false;
		length = 1;
		value = start.getValue();
	}
	public void addCard(Card newCard) { // adds card to the set if the card fits into the sets value.
		Card preceding = cards.get(length - 1);
		if (preceding.getIntValue()== newCard.getIntValue()) {
			cards.add(newCard);
			length++;
			if (length >= 3) isValid = true;
		}
	}
	
	

	public boolean isValid() { // checks to see if the set is at least 3 cards long.
		return isValid;
	}
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
