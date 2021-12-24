### Installation and Usage

Build and run the application using Maven with the following steps:
- Run `mvn clean package`: this will generate the jar file in the `target` folder
- Run `mvn exec:java -Dexec.mainClass="jason.games.blackjack.Blackjack"`: this will run the Blackjack application

One can also edit the number of decks in the shoe and the display delay by passing in arguments:
- Run `mvn exec:java -Dexec.mainClass="jason.games.blackjack.Blackjack" -Dexec.args="speed={delayInMS} deck={numberOfDecksInShoe}"`

Change {delayInMS} to be equal to how long you want the delay to be in (in milliseconds, 0-9999). Change {numberOfDecksInShoe} to be equal to how many decks you want to be in the shoe (1-9). The default delay time is 200ms, default deck number in the shoe is 6.

When running the application, if you are not able to see the full board display, either decrease your font size or increase the size of your window.

### Images
![A preview of the Blackjack application.](/images/Blackjack1.png "Blackjack Preview 1")
Player 1 went bankrupt and was kicked off the table. Players 2-7 have finished their turns and it is currently player 8's turn.


![A preview of the Blackjack application.](/images/Blackjack2.png "Blackjack Preview 2")
It is the end of the round. The dealer got a Blackjack, so everyone lost, except for player 1 (who tied).

### Software Versions

This application was developed and tested using:
- Apache Maven 3.6.3
- Java version: 11.0.12
- MacOS 10.14.6

### Description

This is a command-line implementation of BlackJack.

The rules that were followed can be found here:
https://bicyclecards.com/how-to-play/blackjack/

### Design

Model-view-controller and observer design patterns are used.

ActionListeners is used to signal to the display whenever there is an update to the players, dealer, or game log.

A model class is used to hold all of the data. The viewer class is used for all displays and a controller class is used for all of the game logic. The viewer and the controller classes never call on each other. Rather, the model class is used to store, edit and, get data that are needed between the viewer and model classes.

### Rules Implemented

- Up to 8 players can play at once.
- Players will start with $1000.
- Players must bet more than $0 every round.
- The dealer will draw two cards for themself at the start of the round and will not show the second card until all players finish their turns.
- The dealer will continue to hit until they get 17 or higher.
- Players are given the choice to hit, stand, split, or double.
- Players can only split if their hand consists of two cards and both cards have the same face value (e.g. a King and a King).
- Players can re-split a split hand.
- Players can double only when their hand consists of two cards and their hand sums up to 9, 10, or 11.
- Players can double a split hand.
- A player/dealer gets a Blackjack if their hand consists of an Ace and a 10-value card and if the hand is not a split hand (an Ace + 10 on a hand that was split is not considered Blackjack).
- Players who split two aces will receive one more card for each split ace, but then their turn ends for both hands.
- Players are not allowed to split if they do not have the money to do so.
- If the dealer gets a Blackjack, Players will lose their hand unless they have a Blackjack (in which case, they tie).
- A win with a Blackjack pays 2.5:1 whereas a regular win pays 2:1.
- Players who run out of cash are eliminated from the game.
- The deck will be shuffled only if it runs out of cards during the middle of the round, or if it has less than 40% of its cards left at the end of a round.
- The game stops when there are no players left.

### UI Implemented

- An updated board display is constantly shown in the command line.
- The board display consists of the playing board (showing player names, cash, wagers, cards, and hand totals).
- The board display consists of a game log that keeps track of past game events.
- The board display will constantly be re-drawn every time it receives an update.
- A delay is given between re-draws to give a more realistic feeling.
- User inputs are validated for correct formatting.
- The board will display possible totals for a player's hand (e.g. an ace and 4 will show "5/15")