package jason.games.blackjack;

import java.util.regex.Pattern;

public class Blackjack {
	// ♠♣♥♦

	public static void main(String... args) {
		int speed = 200;
		int deckSize = 6;
		
		Pattern validSpeed = Pattern.compile("\\d{1,4}");
		Pattern validDeckSize = Pattern.compile("[1-9]");
		
		for (String arg : args) {
			if (arg.contains("speed")) {
				String speedInput = arg.split("=")[1];
				System.out.println(speedInput);
				if (validSpeed.matcher(speedInput).matches()) {
					speed = Integer.valueOf(speedInput);
					System.out.println(speed);
				}
			}
			
			if (arg.contains("deck")) {
				String deckSizeInput = arg.split("=")[1];
				if (validDeckSize.matcher(deckSizeInput).matches()) {
					deckSize = Integer.valueOf(deckSizeInput);
				}
			}
		}
		
		GameState game = new GameState(speed, deckSize);
		game.startGame();
	}

}