package jason.games.blackjack.validator;

import java.util.regex.Pattern;

public class NumberOfPlayersValidator implements InputValidator<Integer> {
	private static Pattern pattern = Pattern.compile("[1-8]");
	private boolean isValid = false;
	private String errorMessage;
	private Integer value;
	
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
	    	this.errorMessage = "Your input '" + input + "' is invalid. We can only accept up to 8 players.";
	    } else {
	    	this.isValid = true;
	    	value = Integer.valueOf(input);
	    }
	}

}
