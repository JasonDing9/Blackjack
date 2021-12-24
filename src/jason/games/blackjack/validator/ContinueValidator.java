package jason.games.blackjack.validator;

public class ContinueValidator implements InputValidator<String> {
	private boolean isValid = false;
	private String errorMessage;
	private String value;
	
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
		if (input == null) {
	        this.isValid = false; 
	        this.errorMessage = "Invalid input. Your input is empty.";
	        return;
	    } 
		
		if (input.equalsIgnoreCase("yes")) {
			this.isValid = true;
			this.value = "yes";
			return;
		}
		
		if (input.equalsIgnoreCase("no")) {
			this.isValid = true;
			this.value = "no";
			return;
		}
		
		this.isValid = false;
		this.value = "invalid";
		this.errorMessage = "Your input '" + input + "' is invalid.";
	}

}
