package jason.games.blackjack.model;
import java.util.*;

public class Deck {
	private Stack<Card> cards;
	private int numberOfDecks;

	public Deck (int numberOfDecks) {
		this.numberOfDecks = numberOfDecks;
		createNewDeck();
	}
	
	public void create(int numberOfDecks) {
		this.cards = new Stack<>();
		for (int deckNumber = 0; deckNumber < numberOfDecks; deckNumber++) {
			for (int suit = 0; suit < 4; suit++) {
				for (int number = 1; number <= 13; number++) {
					this.cards.push(new Card(number, suit));
				}
			}
		}
	}
	
	public int getDeckSize() {
		return this.cards.size();
	}

	public Card pop() {
		return this.cards.pop();
	}
	
	public boolean isLowOnCards () {
		return cards.size() < (int) (0.4 * 52 * this.numberOfDecks);
	}
	
	public void createNewDeck () {
		create(this.numberOfDecks);
		Collections.shuffle(this.cards);
	}
}
