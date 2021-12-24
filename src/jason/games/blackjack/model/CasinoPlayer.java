package jason.games.blackjack.model;

import java.util.ArrayList;
import java.util.List;

import jason.games.blackjack.ChangeListener;
import jason.games.blackjack.ChangeObservable;

public class CasinoPlayer implements Player, ChangeObservable{
	private int cash;
	private ArrayList<Bet> bets;
	private String name;
	private ChangeListener listener;
	
	public CasinoPlayer (int index) {
		this.cash = 1000;
		this.bets = new ArrayList<>();
		this.name = "Player " + index;
	}
	
	@Override
	public String getName () {
		return this.name;
	}
	
	public List<Bet> getPlayerBets() {
		return this.bets;
	}
	
	public void clearBets() {
		this.bets = new ArrayList<>();
		listener.statusChanged();
	}
	
	public void makeBet(int betAmmount) {
		addCash(-betAmmount);
		Bet bet = new Bet(betAmmount);
		bet.addChangeListener(this.listener);
		this.bets.add(bet);
		listener.statusChanged();
	}
	
	public Bet splitBet(Bet bet) {
		addCash(-bet.getWager());
		Bet splitBet = new Bet(bet.getWager(), true);
		splitBet.addChangeListener(this.listener);
		this.bets.add(splitBet);
		listener.statusChanged();
		return splitBet;
	}
	
	@Override
	public int getCash() {
		return this.cash;
	}

	public void addCash(int addCash) {
		this.cash += addCash;
		listener.statusChanged();
	}
	
	public boolean canDouble(Bet bet) {
		List<Card> betCards = bet.getCards();
		int sum = betCards.get(0).getValue() + betCards.get(1).getValue();
		return (betCards.size() == 2 && sum >= 9 && sum <= 11);
	}
	
	public boolean canSplit(Bet bet) {
		int firstCardNumber = bet.getCards().get(0).getNumber();
		int secondCardNumber = bet.getCards().get(1).getNumber();
		
		return (bet.getCards().size() == 2 && firstCardNumber == secondCardNumber && getPlayerBets().size() < 4);
	}
	
	public void doubleBet(Bet bet) {
		addCash(- bet.getWager());
		bet.doubleBet();
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
