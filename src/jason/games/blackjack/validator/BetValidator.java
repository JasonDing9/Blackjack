package jason.games.blackjack.validator;

import java.util.regex.Pattern;

import jason.games.blackjack.model.Player;

public class BetValidator implements InputValidator<Integer> {
	private static Pattern pattern = Pattern.compile("[1-9]\\d*");
	private static Pattern bigInt = Pattern.compile("[1-9]\\d{0,8}");
	private boolean isValid = false;
	private String errorMessage;
	private Integer value;
	private Player player;
	
	public BetValidator(Player player) {
		this.player = player;
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
	public Integer getValue() {
		return this.value;
	}

	@Override
	public void validate(String input) {
		if (input == null) {
	        this.isValid = false; 
	        this.errorMessage = "Invalid input. Your input is empty.";
	    } else if (!pattern.matcher(input).matches()) {
	    	this.isValid = false; 
	    	this.errorMessage = "Your input '" + input + "' is invalid. Please input an integer greater than 0.";
	    }  else if (!bigInt.matcher(input).matches()) {
	    	this.isValid = false; 
	    	this.errorMessage = "Your input '" + input + "' is invalid. The maximum bet size is 999,999,999.";
	    } else {
	    	if (Integer.valueOf(input) <= player.getCash()) {
				this.isValid = true;
				value = Integer.valueOf(input);
			} else {
				this.isValid = false;
				this.errorMessage = "You do not have enough money to make that bet!";
			}
	    }
		
			
	}

}
