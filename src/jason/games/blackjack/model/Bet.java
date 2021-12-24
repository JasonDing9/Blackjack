package jason.games.blackjack.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jason.games.blackjack.ChangeListener;
import jason.games.blackjack.ChangeObservable;

public class Bet implements ChangeObservable{
	private int wager = 0;
	private List<Card> cards = new ArrayList<>();
	private boolean stillPlaying = true;
	private boolean isSplit = false;
	private ChangeListener listener;
	
	public Bet(int betAmmount) {
		this(betAmmount, false);
	}
	
	public Bet(int betAmmount, boolean isSplit) {
		this.wager = betAmmount;
		this.cards = new ArrayList<>();
		this.isSplit = isSplit;
	}
	
	public Card splitCard() {
		this.isSplit = true;
		return cards.remove(this.cards.size() - 1);
	}
	
	
	public int getWager() {
		return this.wager;
	}
	
	public List<Card> getCards() {
		return Collections.unmodifiableList(this.cards);
	}
	
	public void addCard(Card card) {
		this.cards.add(card);
		listener.statusChanged();
	}
	
	public void doubleBet () {
		this.wager *= 2;
	}
	
	/**
	 * Get a list of possible sums of the cards in the current bet.
	 * @return
	 */
	public List<Integer> getCardsPossibleSum() {
		int sum = 0;
		int aceCount = 0;
		ArrayList<Integer> sums = new ArrayList<>();
		
		for (int i = 0; i < this.cards.size(); i++) {
			if (this.cards.get(i).getValue() == 1) {
				aceCount ++;
			} else {
				sum += this.cards.get(i).getValue();
			}
		}
		
		for (int elevenAces = 0; elevenAces < aceCount + 1; elevenAces++) {
			sums.add(sum + 1 * (aceCount - elevenAces) + 11 * (elevenAces));
		}
		
		return sums;
	}

	/**
	 * Get the possible maximum sum of the cards in the current bet that is not over 21
	 * or the smallest sum if all of the possible sums are over 21.
	 * @return
	 */
	public int getCardsMaxSum() {
		List<Integer> possibleSums = getCardsPossibleSum();
		int maxSum = possibleSums.get(0);
		
		for (int sum : possibleSums) {
			if (sum <= 21) {
				maxSum = sum;
			}
		}
		
		return maxSum;
	}
	
	public boolean isHandStillPlaying() {
		if (getCardsMaxSum() >= 21) {
			this.stillPlaying = false;
		}
		
		return this.stillPlaying;
	}
	
	public void setStillPlaying(boolean in) {
		this.stillPlaying = in;
	}
	
	public boolean isBust() {
		return (getCardsPossibleSum().get(0) > 21);
	}
	
	public boolean isSplit() {
		return this.isSplit;
	}
	
	public boolean hasBlackJack() {
		List<Integer> sums = getCardsPossibleSum();
		return (!isSplit && sums.size() >= 2 && sums.get(1) == 21 && cards.size() == 2);
	}

	@Override
	public void addChangeListener(ChangeListener listener) {
		this.listener = listener;
	}
	
	@Override
	public void removeChangeListener(ChangeListener listener) {
		this.listener = null;
	}
}
