package jason.games.blackjack.validator;

import jason.games.blackjack.model.Bet;
import jason.games.blackjack.model.CasinoPlayer;

public class ActionValidator implements InputValidator<String> {
	private boolean isValid = false;
	private String errorMessage;
	private String value;
	private CasinoPlayer player;
	private Bet bet;
	
	public ActionValidator(CasinoPlayer player, Bet bet) {
		this.player = player;
		this.bet = bet;
	}
	
	@Override
	public boolean isValid() {
		return this.isValid;
	}

	@Override
	public String getErrorMessage() {
		return this.errorMessage;
	}

	@Override
	public String getValue() {
		return this.value;
	}

	@Override
	public void validate(String input) {
		int initialBetAmmount = bet.getWager();
		int playerCash = player.getCash();
		
		if (input == null) {
	        this.isValid = false; 
	        this.errorMessage = "Invalid input. Your input is empty.";
	        return;
	    } 
		
		if (input.equalsIgnoreCase("hit")) {
			this.isValid = true;
			this.value = "hit";
			return;
		}
		
		if (input.equalsIgnoreCase("stand")) {
			this.isValid = true;
			this.value = "stand";
			return;
		}
		
		if (input.equalsIgnoreCase("split") && player.canSplit(bet)) {
			if (playerCash >= initialBetAmmount) {
				this.isValid = true;
				this.value = "split";
				return;
			} else {
				this.isValid = false;
				this.errorMessage = "Not enough money to do that action.";
				return;
			}
		}
		
		if ((input.equalsIgnoreCase("double") || input.equalsIgnoreCase("double down") 
				|| input.equalsIgnoreCase("double-down")) && player.canDouble(bet)) {
			if (playerCash >= initialBetAmmount) {
				this.isValid = true;
				this.value = "double";
				return;
			} else {
				this.isValid = false;
				this.errorMessage = "Not enough money to do that action.";
				return;
			}
		}
		
		this.isValid = false;
		this.value = "invalid";
		this.errorMessage = "Your input '" + input + "' is invalid.";
	}

}
