package jason.games.blackjack;

import java.util.ArrayList;
import java.util.List;

import jason.games.blackjack.model.Bet;
import jason.games.blackjack.model.Card;
import jason.games.blackjack.model.CasinoPlayer;
import jason.games.blackjack.model.Dealer;
import jason.games.blackjack.model.Deck;
import jason.games.blackjack.model.GameModel;
import jason.games.blackjack.validator.ActionEnum;
import jason.games.blackjack.validator.ActionValidator;
import jason.games.blackjack.validator.BetValidator;
import jason.games.blackjack.validator.ContinueValidator;
import jason.games.blackjack.validator.InputValidator;
import jason.games.blackjack.validator.NumberOfPlayersValidator;

public class GameState {
	private Deck playingDeck;
	private Display display;
	private UserInput userInput;
	private GameModel gameModel = new GameModel();
	private int deckCount = 6;
	
	public GameState(int waitTime, int deckCount) {
		this.deckCount = deckCount;
		initialize(waitTime);
	}
	
	private void initialize(int waitTime) {
		System.out.println(waitTime);
		this.display = new Display();
		this.display.setGameModel(this.gameModel, waitTime);
		this.gameModel.addChangeListener(this.display);
		Dealer dealer = new Dealer();
		dealer.addChangeListener(this.display);
		
		this.gameModel.setDealer(dealer);
		this.userInput = new UserInput(this.display, this.gameModel);

		this.playingDeck = new Deck(deckCount);
		
		InputValidator<Integer> numberOfPlayersValidator = new NumberOfPlayersValidator();
		this.userInput.getUserInput("How many players?", numberOfPlayersValidator);
		int numberOfPlayers = numberOfPlayersValidator.getValue();
		
		for (int i = 1; i <= numberOfPlayers; i++) {
			CasinoPlayer p = new CasinoPlayer(i);
			p.addChangeListener(this.display);
			this.gameModel.addPlayer(p);
		}
		
	}
	
	public void startGame() {
		while (!this.gameModel.getPlayers().isEmpty()) {
			this.gameModel.addGameLog("Starting new round.");

			playRound();
			removeBankruptPlayers();
			
			if (this.playingDeck.isLowOnCards()) {
				shuffleDeck();
			}
		}
		
		this.gameModel.addGameLog("All players out.");
		this.display.setUserInput("Game Over");
	}
	
	private void shuffleDeck () {
		this.gameModel.addGameLog("Shuffling a new deck...");
		this.playingDeck.createNewDeck();
	}

	private void removeBankruptPlayers() {
		List<CasinoPlayer> playersToRemove = new ArrayList<>();
		
		for (CasinoPlayer p : this.gameModel.getPlayers()) {
			if (p.getCash() == 0) {
				this.gameModel.addGameLog(p.getName() + " is out of money.");
				playersToRemove.add(p);
			}
		}
		
		for (CasinoPlayer p : playersToRemove) {
			this.gameModel.removePlayer(p);
		}
	}
	
	private void playRound() {
		this.gameModel.getDealer().clearCards();
		for (CasinoPlayer player : this.gameModel.getPlayers()) {
			player.clearBets();
		}
		
		getBetsFromPlayers();
		dealInitialCards();
		
		performAllPlayerTurns();
		
		drawDealerCards();
		
		compareHands();
		
		InputValidator<String> validator = new ContinueValidator();
		userInput.getUserInput("Would you like to continue? (Enter yes to continue, no to exit)", validator);
		if (validator.getValue().equals("no")) {
			this.display.setUserInput("Game Over");
			System.exit(0);
		}
	}
	
	private void getBetsFromPlayers() {
		
		for (CasinoPlayer player : this.gameModel.getPlayers()) {
			InputValidator<Integer> validator = new BetValidator(player);
			userInput.getUserInput(player.getName() + ": How much would you like to bet?", validator);
			int betAmmount = validator.getValue();
			player.makeBet(betAmmount);
			this.gameModel.addGameLog(player.getName() + " bet $" + betAmmount);
		}
	}
	
	private void dealInitialCards() {
		this.display.setUserInput("Dealing cards.");
		addDealerCard();
		
		for (int i = 0; i < 2; i++) {
			for (CasinoPlayer player : this.gameModel.getPlayers()) {
				for (Bet bet : player.getPlayerBets()) {
					dealCard(player, bet);
				}
			}
		}
	}
	
	private void performAllPlayerTurns () {
		for (CasinoPlayer player : this.gameModel.getPlayers()) {
			for (int betIndex = 0; betIndex < player.getPlayerBets().size(); betIndex ++) {
				Bet bet = player.getPlayerBets().get(betIndex);
				performPlayerTurn(player, bet);
			}
		}
	}
	
	private StringBuilder buildValidPlayerChoices (CasinoPlayer player, Bet bet) {
		StringBuilder validChoices = new StringBuilder("(");
		validChoices.append(ActionEnum.HIT.name()).append("<" + ActionEnum.HIT.getShortCut() + ">");
		validChoices.append(" | ");
		validChoices.append(ActionEnum.STAND.name()).append("<" + ActionEnum.STAND.getShortCut() + ">");
		
		if (player.canDouble(bet) && player.getCash() >= bet.getWager()) {
			validChoices.append(" | ");
			validChoices.append(ActionEnum.DOUBLE.name()).append("<" + ActionEnum.DOUBLE.getShortCut() + ">");
		}
		
		if (player.canSplit(bet) && player.getCash() >= bet.getWager()) {
			validChoices.append(" | ");
			validChoices.append(ActionEnum.SPLIT.name()).append("<" + ActionEnum.SPLIT.getShortCut() + ">");
		}
		
		validChoices.append(")");
		return validChoices;
	}

	private void performPlayerTurn(CasinoPlayer player, Bet bet) {
		while (bet.isHandStillPlaying()) {
			InputValidator<ActionEnum> validator = new ActionValidator(player, bet);
			
			this.userInput.getUserInput(player.getName() + 
					": What would you like to do? " + buildValidPlayerChoices(player, bet), validator);
			ActionEnum action = validator.getValue();
			
			if (action == ActionEnum.STAND) {
				doPlayerStandAction(player, bet);
			} else if (action == ActionEnum.HIT) {
				doPlayerHitAction(player, bet);
			} else if (action == ActionEnum.DOUBLE) {
				doPlayerDoubleAction(player, bet);
			} else if (action == ActionEnum.SPLIT) {
				doPlayerSplitAction(player, bet);
			}
		}
	}
	
	private void doPlayerStandAction (CasinoPlayer player, Bet bet) {
		this.gameModel.addGameLog(player.getName() + " stood.");
		bet.setStillPlaying(false);
	}
	
	private void doPlayerHitAction (CasinoPlayer player, Bet bet) {
		this.gameModel.addGameLog(player.getName() + " hit.");
		dealCard(player, bet);
	}
	
	private void doPlayerDoubleAction (CasinoPlayer player, Bet bet) {
		this.gameModel.addGameLog(player.getName() + " doubled.");
		player.doubleBet(bet);
		bet.setStillPlaying(false);
		
		dealCard(player, bet);
	}
	
	private void doPlayerSplitAction (CasinoPlayer player, Bet originalBet) {
		this.gameModel.addGameLog(player.getName() + " split.");
		
		Bet splitBet = player.splitBet(originalBet);
		Card splitCard = originalBet.splitCard();
		boolean isAce = (splitCard.getNumber() == 1);
		
		splitBet.addCard(splitCard);
		
		dealCard(player, originalBet);
		dealCard(player, splitBet);
		if (isAce) {
			originalBet.setStillPlaying(false);
			splitBet.setStillPlaying(false);
		}
	}
	
	private void payBetWin(CasinoPlayer player, Bet bet) {
		if (bet.hasBlackJack()) {
			player.addCash((int) (bet.getWager() * 2.5));
			this.gameModel.addGameLog(player.getName() + " won $" + 
					(int) (bet.getWager() * 2.5) + ".");
		} else {
			player.addCash(bet.getWager() * 2);
			this.gameModel.addGameLog(player.getName() + " won $" + 
					bet.getWager() * 2 + ".");
		}
	}
	
	private void payBetTie(CasinoPlayer player, Bet bet) {
		player.addCash(bet.getWager());
		this.gameModel.addGameLog(player.getName() + " won $" + 
				(bet.getWager()) + ".");
	}
	
	private void addDealerCard() {
		Card cardDrawn = drawCard();
		this.gameModel.getDealer().addCard(cardDrawn);
		this.gameModel.addGameLog("Dealer was dealt a " + cardDrawn.displayCard() + ".");
		
		if (this.gameModel.getDealer().getCardsMaxSum() > 21) {
			this.gameModel.addGameLog("Dealer bust!");
		}
	}
	
	private void drawDealerCards() {
		while (this.gameModel.getDealer().getCardsMaxSum() <= 16) {
			addDealerCard();
		}
	}
	
	private void dealCard(CasinoPlayer player, Bet bet) {
		Card cardDrawn = drawCard();
		bet.addCard(cardDrawn);
		
		this.gameModel.addGameLog(player.getName() + " was dealt a " + cardDrawn.displayCard() + ".");
		
		if (bet.isBust()) {
			this.gameModel.addGameLog(player.getName() + " bust!");
		}
	}
	
	private void compareHands() {
		boolean dealerBlackJack = this.gameModel.getDealer().isDealerBlackJack();
		int dealerSum = this.gameModel.getDealer().getCardsMaxSum();
		for (int playerIndex = 0; playerIndex < this.gameModel.getPlayers().size() ; playerIndex++) {
			CasinoPlayer player = this.gameModel.getPlayers().get(playerIndex);
			for (int betIndex = 0; betIndex < player.getPlayerBets().size(); betIndex ++) {
				Bet bet = player.getPlayerBets().get(betIndex);
				
				int playerSum = bet.getCardsMaxSum();
				if (!bet.isBust()) {
					if (dealerBlackJack) {
						if (bet.hasBlackJack()) {
							payBetTie(player, bet);
						}
					} else if (dealerSum > 21){
						payBetWin(player, bet);
					} else {
						if (playerSum > dealerSum || bet.hasBlackJack()) {
							payBetWin(player, bet);
						} else if (playerSum == dealerSum) {
							payBetTie(player, bet);
						}
					}
				}
			}
		}
		
		this.gameModel.addGameLog("Displaying final board.");
	}
	
	private Card drawCard () {
		if (this.playingDeck.getDeckSize() == 0) {
			this.gameModel.addGameLog("Deck out of cards! Shuffling new deck...");
			this.playingDeck.createNewDeck();
		}
		
		return this.playingDeck.pop();
	}
}