package jason.games.blackjack.model;

public class Card {
	private int number;
	private int suit;

	public Card(int number, int suit) {
		this.number = number;
		this.suit = suit;
	}
	
	public int getNumber() {
		return this.number;
	}
	
	public int getValue() {
		if (this.number >= 10) {
			return 10;
		} else {
			return this.number;
		}
	}
	
	public int getSuit() {
		return this.suit;
	}
	
	public String displayCard() {
		String faceValue;
		String faceSuit;
		if (this.number <= 10 && this.number > 1) {
			faceValue = String.valueOf(this.number);
		} else {
			if (this.number == 11) {
				faceValue = "J";
			} else if (this.number == 12) {
				faceValue = "Q";
			} else if (this.number == 13) {
				faceValue = "K";
			} else {
				faceValue = "A";
			}
		}
		
		if (this.suit == 0) {
			faceSuit = "♠";
		} else if (this.suit == 1) {
			faceSuit = "♣";
		} else if (this.suit == 3) {
			faceSuit = "♥";
		} else {
			faceSuit = "♦";
		}
		
		return faceValue + faceSuit;
	}

}
