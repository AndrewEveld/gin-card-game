
public class Card {
	String suit;
	String value;
	
	public Card(String suit, String value) {
		this.suit = suit;
		this.value = value;
	}
	public String getSuit() {
		return suit;
	}
	public int getIntValue() { //return the numerical (int) value of a card
		int toReturn = 0;
		if (value.equals("A")) {
			toReturn = 1;
		}
		else if (value.equals("J")) {
			toReturn = 11;
		}
		else if (value.equals("Q")) {
			toReturn = 12;
		}
		else if (value.equals("K")) {
			toReturn = 13;
		}
		else if (value.contentEquals("10")) {
			toReturn = 10;
		}else {
			return value.charAt(0)-48;
		}
		return toReturn;
	}
	
	public String getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return getValue() + getSuit();
	}
}
