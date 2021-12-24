package jason.games.blackjack.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jason.games.blackjack.ChangeListener;
import jason.games.blackjack.ChangeObservable;

public class Dealer implements Player, ChangeObservable{
	private int cash = 10000;
	private ArrayList<Card> dealerCards;
	private ChangeListener listener;
	
	public Dealer() {
		this.dealerCards = new ArrayList<>();
	}
	
	public void clearCards() {
		this.dealerCards = new ArrayList<>();
		listener.statusChanged();
	}
	
	public List<Card> getCards() {
		return Collections.unmodifiableList(this.dealerCards);
	}
	
	public void addCard (Card card) {
		this.dealerCards.add(card);
		listener.statusChanged();
	}

	public int getCardsMaxSum() {
		int sum = 0;
		int aceCount = 0;
		
		for (int i = 0; i < this.dealerCards.size(); i++) {
			if (this.dealerCards.get(i).getValue() == 1) {
				aceCount ++;
			} else {
				sum += this.dealerCards.get(i).getValue();
			}
		}
		
		int returnSum = sum + aceCount;
		
		for (int elevenAces = 0; elevenAces < aceCount + 1; elevenAces++) {
			int total = sum + 1 * (aceCount - elevenAces) + 11 * (elevenAces);
			if (total <= 21) {
				returnSum = total;
			}
		}
		
		return returnSum;
	}
	
	public List<Integer> getCardsPossibleSum() {
		int sum = 0;
		int aceCount = 0;
		List<Integer> sums = new ArrayList<>();
		
		for (int i = 0; i < this.dealerCards.size(); i++) {
			if (this.dealerCards.get(i).getValue() == 1) {
				aceCount ++;
			} else {
				sum += this.dealerCards.get(i).getValue();
			}
		}
		
		for (int elevenAces = 0; elevenAces < aceCount + 1; elevenAces++) {
			sums.add(sum + 1 * (aceCount - elevenAces) + 11 * (elevenAces));
		}
		
		return sums;
	}
	
	public boolean isDealerBlackJack () {
		return (getCardsMaxSum() == 21 && this.dealerCards.size() == 2);
	}

	@Override
	public int getCash() {
		return cash;
	}

	@Override
	public String getName() {
		return "Dealer";
	}
	
	@Override
	public void addChangeListener(ChangeListener listener) {
		this.listener = listener;
	}
	
	@Override
	public void removeChangeListener(ChangeListener listener) {
		this.listener = null;
	}

	@Override
	public List<Bet> getPlayerBets() {
		return Collections.emptyList();
	}
	
}
