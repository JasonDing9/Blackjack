package jason.games.blackjack.validator;

public interface InputValidator<V> {
	boolean isValid();
	String getErrorMessage();
	V getValue();
	void validate(String input);
}
