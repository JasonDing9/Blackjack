package jason.games.blackjack;

import java.util.List;

import jason.games.blackjack.model.Bet;
import jason.games.blackjack.model.Card;
import jason.games.blackjack.model.GameModel;
import jason.games.blackjack.model.Player;

public class Display implements ChangeListener{
	private String userInput;
	private int waitTime = 300;
	private GameModel gameModel;
	
	public void setGameModel(GameModel gameModel, int waitTime) {
		this.gameModel = gameModel;
		this.waitTime = waitTime;
	}
	
	private void printGameLog() {
		printBorderTitle("Game Log");
		
		for (String log : this.gameModel.getGameLogs()) {
			System.out.println(log);
		}
		
		for (int spacing = 0; spacing < 20 - this.gameModel.getGameLogs().size(); spacing ++) {
			System.out.println();
		}
	}
	
	private void printTitle() {
		System.out.print("Name");
		System.out.print(" ".repeat(6));
		System.out.print("Cash");
		System.out.print(" ".repeat(12));
		System.out.print("Total");
		System.out.print(" ".repeat(7));
		System.out.print("Cards");
		System.out.print(" ".repeat(59));
		System.out.print("Wager");
		System.out.println();
	}
	
	public void paint () {
		for (int spacing = 0; spacing < 100; spacing ++) {
			System.out.println();
		}
		
		printBorderTitle("Game Board");
		printTitle();
		
		printDealer();
		
		System.out.println();
		
		if(this.gameModel.getPlayers() != null) {
			for(Player player: this.gameModel.getPlayers()) {
				printPlayer(player);
			}
		}
		
		printGameLog();
		
		printBorderTitle("User Input");
		System.out.println(userInput);
		
		try {
		    Thread.sleep(waitTime);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}

	public void setUserInput (String displayString) {
		this.userInput = displayString;
		paint();
	}
	
	private void printBorderTitle(String displayType) {
		if (displayType.length() % 2 == 0) {
			int repeatAmmount = (120 - displayType.length()) / 2;
			System.out.println("=".repeat(repeatAmmount) + " " + displayType + " " + "=".repeat(repeatAmmount));
		} else {
			int repeatAmmount = (119 - displayType.length()) / 2;
			System.out.println("=".repeat(repeatAmmount) + " " + displayType + " " + "=".repeat(repeatAmmount + 1));
		}
	}

	private void printPlayer (Player player) {
		System.out.print(player.getName() + " ");
		
		if (10 - (player.getName() + " ").length() <= 0) {
			System.out.print(" ");
		} else {
			System.out.print(" ".repeat(10 - (player.getName() + " ").length()));
		}
		
		System.out.print(" $" + player.getCash());
		if (10 - (" $" + player.getCash()).length() <= 0) {
			System.out.print(" ");
		} else {
			System.out.print(" ".repeat(10 - (" $" + player.getCash()).length()));
		}
		System.out.print(" ".repeat(6));
			
		if(player.getPlayerBets() == null || player.getPlayerBets().isEmpty()) {
			System.out.print(" ".repeat(12));
			System.out.print("-".repeat(63));
			System.out.println();
		} else {
			Bet bet = player.getPlayerBets().get(0);
			printBet(bet);
			
			for(int index = 1; index < player.getPlayerBets().size(); index++) {
				Bet nextBet = player.getPlayerBets().get(index);
				System.out.print(" ".repeat(26));
				printBet(nextBet);
				
			}
		}
	}
	
	private void printDealer() {
		String cardsPossibleValues = getCardsPossibleValueDisplay(this.gameModel.getDealer().getCardsPossibleSum(), this.gameModel.getDealer().getCards().size(), false);
		
		System.out.print("Dealer" + " ".repeat(14)); // 20 space width

		System.out.print(" ".repeat(6));
			
		System.out.print(cardsPossibleValues);
		if (12 - cardsPossibleValues.length() <= 0) {
			System.out.print(" ");
		} else {
			System.out.print(" ".repeat(12 - cardsPossibleValues.length()));
		}
		
		StringBuilder playerHandDisplay = getCardsDisplayBuilder(this.gameModel.getDealer().getCards()); 
		System.out.print(playerHandDisplay);
		
		if (63 - playerHandDisplay.length() <= 0) {
			System.out.print("-");
		} else {
			System.out.print("-".repeat(63 - playerHandDisplay.length()));
		}
		
		System.out.println();
	}

	private void printBet(Bet bet) {
		String cardsPossibleValues = getCardsPossibleValueDisplay(bet.getCardsPossibleSum(), bet.getCards().size(), bet.isSplit());
		System.out.print(cardsPossibleValues);
		
		if (12 - cardsPossibleValues.length() <= 0) {
			System.out.print(" ");
		} else {
			System.out.print(" ".repeat(12 - cardsPossibleValues.length()));
		}
		
		StringBuilder playerHandDisplay = getCardsDisplayBuilder(bet.getCards());
		System.out.print(playerHandDisplay);
		
		if (63 - playerHandDisplay.length() <= 0) {
			System.out.print("-");
		} else {
			System.out.print("-".repeat(63 - playerHandDisplay.length()));
		}
		
		System.out.print(" $" + bet.getWager());
		
		System.out.println();
	}

	private StringBuilder getCardsDisplayBuilder (List<Card> cards) {
		StringBuilder output = new StringBuilder("");
		for (int cardIndex = 0; cardIndex < cards.size(); cardIndex++) {
			String cardDisplay = cards.get(cardIndex).displayCard();
			output.append(cardDisplay + " ".repeat(4 - cardDisplay.length()));
		}
		
		return output;
	}

	private String getCardsPossibleValueDisplay (List<Integer> sums, int numberOfCard, boolean isSplit) {
		
		if (sums.size() == 1 || sums.get(0) > 21) {
			if (sums.get(0) > 21) {
				return sums.get(0) + " (BUST!)";
			}  else {
				return String.valueOf(sums.get(0));
			}
		} else {
			String output = String.valueOf(sums.get(0));
			for (int sumIndex = 1; sumIndex < sums.size(); sumIndex ++) {
				if (sums.get(sumIndex) < 21) {
					output = String.valueOf(sums.get(0)) + "/" + sums.get(sumIndex);
				} else if (sums.get(sumIndex) == 21 && numberOfCard == 2 && !isSplit) {
					return "21 (BJ!)";
				} else if (sums.get(sumIndex) == 21){
					return "21";
				}
			}
			
			return output;
		}
	}
	
	@Override
	public void statusChanged() {
		paint();
	}
}
