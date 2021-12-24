package jason.games.blackjack.model;

import java.util.List;

public interface Player {
	int getCash();
	
	String getName();
	List<Bet> getPlayerBets();
}
