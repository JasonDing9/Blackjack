package jason.games.blackjack.validator;

import jason.games.blackjack.model.Bet;
import jason.games.blackjack.model.CasinoPlayer;

public class ActionValidator implements InputValidator<ActionEnum> {
	private boolean isValid = false;
	private String errorMessage;
	private ActionEnum value;
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
	public ActionEnum getValue() {
		return this.value;
	}

	@Override
	public void validate(String input) {
		int initialBetAmmount = bet.getWager();
		int playerCash = player.getCash();
		ActionEnum action = ActionEnum.getAction(input);
		
		if (action == null) {
	        this.isValid = false; 
	        this.errorMessage = "Your input '" + input + "' is invalid.";
	        return;
	    } 
		
		switch (action) {
			case STAND:
			case HIT:
				this.isValid = true;
				this.value = action;
				break;
			case DOUBLE:
				if (! player.canDouble(bet)) {
					this.isValid = false;
					this.errorMessage = "You cannot double with your current hand.";
					break;
				}
				if (playerCash >= initialBetAmmount) {
					this.isValid = true;
					this.value = action;
				} else {
					this.isValid = false;
					this.errorMessage = "Not enough money to do that action.";
				}
				break;
			case SPLIT:
				if (! player.canSplit(bet)) {
					this.isValid = false;
					this.errorMessage = "You cannot split with your current hand.";
					break;
				}
				if (playerCash >= initialBetAmmount) {
					this.isValid = true;
					this.value = action;
				} else {
					this.isValid = false;
					this.errorMessage = "Not enough money to do that action.";
				}
				break;
				
		}
			
	}

}