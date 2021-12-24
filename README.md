### Installation and Usage

Build and run the application using Maven with the following steps:
- Run `mvn clean package`: this will generate the jar file in the `target` folder
- Run `mvn exec:java -Dexec.mainClass="jason.games.blackjack.Blackjack"`: this will run the Blackjack application

One can also edit the number of decks in the shoe and the display delay by passing in arguments:
- Run `mvn exec:java -Dexec.mainClass="jason.games.blackjack.Blackjack" -Dexec.args="speed={delayInMS} deck={numberOfDecksInShoe}"`

Change {delayInMS} to be equal to how long you want the delay to be in (in milliseconds, 0-9999). Change {numberOfDecksInShoe} to be equal to how many decks you want to be in the shoe (1-9). Default delay time is 200ms, default decks in shoe is 6.

### Images

![A preview of the Blackjack application.](/images/Blackjack2.png "Blackjack Preview 1")

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

Model-view-controller and observer design patterns were used.

ActionListeners were used to signal to the display whenever there was an update to the players, dealer, or game log.

A model class was used to hold all of the data. The viewer class was used for all displays and a controller class was used for all of the game logic. The viewer and the controller classes never called on each other. Rather, then model class was used to store, edit and, get data that were needed between the viewer and model classes.

### Rules Implemented

- Up to 8 players can play at once.
- Players will start with $1000.
- Players must bet more than $0 every round.
- The dealer will only  draw one card for themself at the start of the round and not show their second card until all players finish their turns.
- The dealer will continue to hit until they get 17 or higher.
- Players are given the choice to hit, stand, split, or double.
- Players can only split if their hand consists of two cards and both cards have the same face value (e.g. a King and a King).
- Players can re-split a split hand.
- Players can double only when their hand consists of two cards and their hand sums up to 9, 10, or 11.
- Players can double a split hand.
- A player/dealer gets a Blackjack if their hand consists of an Ace and a 10-value card and if the hand is not a split hand (an Ace + 10 on a hand that was split is not considered Blackjack).
- Players who split two aces will recieve one more card for each split ace, but then their turn ends for both hands.
- Players are not allowed to split if they do not have the money to do so.
- If the dealer gets a Blackjack, Players will loose their hand unless they have a Blackjack (in which case, they tie).
- A win with a Blackjack pays 2.5:1 whereas a regular win pays 2:1.
- Players who run out of cash are eliminated from the game.
- The deck will be shuffled only if it runs out of cards during the middle of the round, or if it has less than 40% of its cards left at the end of a round.
- The game stops when there are no players left.

### UI Implemented

- An updated board display is constantly shown in the command line.
- The board display consists of the playing board (showing player names, cash, wagers, cards, and hand totals).
- The board display consists of a game log that keeps track of past game events.
- The board display will constantly be re-drawn every time it recieves an update.
- A delay is given between re-draws to give a more realistic feeling.
- User inputs are validated for correct formatting.
- The board will display possible totals for a player's hand (e.g. an ace and 4 will show "5/15")