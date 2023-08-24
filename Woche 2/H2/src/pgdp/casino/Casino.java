package pgdp.casino;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Casino {
	private static int balance;
	private static int cardNumber_player;
	private static int score_player;
	private static int cardNumber_dealer;
	private static int score_dealer;

	public static void penguBlackJack() {

		// Here is a card deck for your games :)
		// Remember for testing you can use seeds:
//		CardDeck deck = CardDeck.getDeck(420);
		CardDeck deck = CardDeck.getDeck();
		balance = 1000;
		System.out.println("Welcome to Pengu-BlackJack!");

		A: while (balance > 0) {
			while (true) {
				System.out.println("(1) Start a game or (2) exit");
				int input = readInt();
				if (input == 1) {
					cardNumber_player = 1;
					score_player = 0;
					cardNumber_dealer = 1;
					score_dealer = 0;
					continueGame(deck);
					break;
				} else if (input == 2) {
					endGame();
					break A;
				} else
					System.out.println("What?!");
			}
		}
		if (balance <= 0) {
			System.out.println("Sorry, you are broke. Better Luck next time.");
			endGame();
		}
	}

	private static void endGame() {
		System.out.println("Your final balance: " + balance);
		if (balance > 1000)
			System.out.println("Wohooo! Ez profit! :D");
		else
			System.out.println("That's very very sad :(");
		System.out.println("Thank you for playing. See you next time.");
		// System.exit(0);
	}

	private static void continueGame(CardDeck deck) {
		System.out.println("Your current balance: " + balance);
		int betAmount;
		do {
			System.out.println("How much do you want to bet?");
			betAmount = readInt();
		} while (betAmount <= 0 || betAmount > balance);
		System.out.println("Player cards:");
		int card;
		for (byte i = 0; i < 2; i++) {
			card = deck.drawCard();
			System.out.println(cardNumber_player + " : " + card);
			score_player += card;
			cardNumber_player++;
		}
		System.out.println("Current standing: " + score_player);
		while (score_player < 21) {
			System.out.println("(1) draw another card or (2) stay");
			int input = readInt();
			if (input == 1) { // draw another card
				card = deck.drawCard();
				System.out.println(cardNumber_player + " : " + card);
				score_player += card;
				cardNumber_player++;
				System.out.println("Current standing: " + score_player);
			} else if (input == 2) { // stay
				break;
			} else
				System.out.println("What?!");
		}

		if (score_player > 21) {
			System.out.println("You lost " + betAmount + " tokens.");
			balance -= betAmount;
		}

		else if (score_player == 21) {
			System.out.println("Blackjack! You won " + (betAmount * 2) + " tokens.");
			balance += (betAmount * 2);
		} else {
			System.out.println("Dealer cards:");
			while (score_dealer <= score_player && score_dealer < 21) {
				card = deck.drawCard();
				System.out.println(cardNumber_dealer + " : " + card);
				score_dealer += card;
				cardNumber_dealer++;
			}
			System.out.println("Dealer: " + score_dealer);
			if (score_dealer > 21) {
				System.out.println("You won " + betAmount + " tokens.");
				balance += betAmount;
			} else {
				System.out.println("Dealer wins. You lost " + betAmount + " tokens.");
				balance -= betAmount;
			}
		}

	}

	public static String readString() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int readInt() {
		while (true) {
			String input = readString();
			try {
				return Integer.parseInt(input);
			} catch (Exception e) {
				System.out.println("This was not a valid number, please try again.");
			}
		}
	}

	public static void main(String[] args) {
		penguBlackJack();
	}

}
