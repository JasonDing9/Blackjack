package jason.games.blackjack;

import java.util.Scanner;

import jason.games.blackjack.model.GameModel;
import jason.games.blackjack.validator.InputValidator;

public class UserInput {
	private Scanner inputs;
	private Display display;
	private GameModel gameModel;
	
	public UserInput (Display display, GameModel gameModel) {
		this.inputs = new Scanner(System.in);
		this.display = display; 
		this.gameModel = gameModel;
	}
	
	public <V> void getUserInput (String question, InputValidator<V> validator) {
		while(!validator.isValid()) {
			if (validator.getErrorMessage() != null) {
				this.gameModel.addGameLog(validator.getErrorMessage());
			}
			this.display.setUserInput(question);
			String input = inputs.nextLine();
			validator.validate(input);
		}
	}
}
