package jason.games.blackjack.model;

import java.util.List;

import jason.games.blackjack.ChangeListener;
import jason.games.blackjack.ChangeObservable;

import java.util.ArrayList;
import java.util.Collections;

public class GameModel implements ChangeObservable{
	private List<CasinoPlayer> players = new ArrayList<>();
	private Dealer dealer;
	private List<String> gameLogs = new ArrayList<>();
	private ChangeListener listener;
	
	public List<CasinoPlayer> getPlayers() {
		return Collections.unmodifiableList(players);
	}
	
	public void removePlayer(Player p) {
		this.players.remove(p);
		listener.statusChanged();
	}
	
	public void addPlayer(CasinoPlayer player) {
		this.players.add(player);
		listener.statusChanged();
	}
	
	public Dealer getDealer() {
		return dealer;
	}
	
	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public List<String> getGameLogs() {
		return gameLogs;
	}

	public void addGameLog (String displayString) {
		this.gameLogs.add(displayString);
		if (this.gameLogs.size() > 20) {
			this.gameLogs.remove(0);
		}
		
		this.listener.statusChanged();
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
