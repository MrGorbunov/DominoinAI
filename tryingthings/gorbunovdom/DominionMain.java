package tryingthings.gorbunovdom;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DominionMain {

	final static int numCards = DomCard.values().length;
	final static Random r = new Random(102312314l);

	public static void main(String[] args) {
		
		// Setup
		int[] deckSupplyPiles = new int[numCards];

		Arrays.fill(deckSupplyPiles, 10);
		deckSupplyPiles[DomCard.COPPER.ordinal()] = 60;
		deckSupplyPiles[DomCard.SILVER.ordinal()] = 40;
		deckSupplyPiles[DomCard.GOLD.ordinal()] = 30;

		deckSupplyPiles[DomCard.ESTATE.ordinal()] = 24;
		deckSupplyPiles[DomCard.DUCHY.ordinal()] = 12;
		deckSupplyPiles[DomCard.PROVENCE.ordinal()] = 12;


		// Player Deck
		int[] deckPlayerHand = new int[numCards];
		int[] deckPlayerDiscard = new int[numCards];
		int[] deckPlayerDeck = new int[numCards];
		int[] deckPlayerAll = new int[numCards];

		deckPlayerAll[DomCard.COPPER.ordinal()] = 7;
		deckPlayerAll[DomCard.ESTATE.ordinal()] = 3;

		deckPlayerDeck[DomCard.COPPER.ordinal()] = 7;
		deckPlayerDeck[DomCard.ESTATE.ordinal()] = 3;


		// Start
		printSupplyPile(deckSupplyPiles);

		drawFiveCards(deckPlayerDeck, deckPlayerDiscard, deckPlayerHand);
		printPlayerHand(deckPlayerHand);
	}

	static void drawFiveCards (int[] deckPlayerDeck, int[] deckPlayerDiscard, int[] deckPlayerHand) {
		for (int i=0; i<5; i++) {
			int playerDeckSize = totalSumOfArr(deckPlayerDeck);

			if (playerDeckSize == 0) {
				for (int card=0; card<numCards; card++) {
					deckPlayerDeck[card] += deckPlayerDiscard[card];
					deckPlayerDiscard[card] = 0;
				}

				playerDeckSize = totalSumOfArr(deckPlayerDeck);

				// All of the player's cards are in their hand (<5 cards total)
				if (playerDeckSize == 0)
					break;
			}

			int nextCard = r.nextInt(playerDeckSize);
			int cardIndex = 0;

			while (nextCard > 0) {
				int numOfThisCard = deckPlayerDeck[cardIndex];
				nextCard -= numOfThisCard;

				if (nextCard < 0)
					break;

				cardIndex++;
			}

			deckPlayerHand[cardIndex]++;
			deckPlayerDeck[cardIndex]--;
		}
	}

	static int totalSumOfArr (int[] values) {
		int sum = 0;
		for (int x : values)
			sum += x;
		return sum;
	}


	static void printPlayerHand (int[] deckPlayerHand) {
		System.out.println(" == Player Hand");

		for (int i=0; i<deckPlayerHand.length; i++) {
			int numOfCardInHand = deckPlayerHand[i];
			if (numOfCardInHand == 0)
				continue;
			if (numOfCardInHand > 1)
				System.out.print(String.format("%dx ", numOfCardInHand));
			
			System.out.print(toTitleCase(DomCard.values()[i].name()) + "\t");
		}

		System.out.println();
	}

	static String toTitleCase (String s) {
		return s.toUpperCase().charAt(0) + s.toLowerCase().substring(1);
	}

	static void printSupplyPile (int[] deckSupplyPiles) {
		System.out.println(" == Common Cards");
		System.out.println(String.format(
			"Estate: %2d\tDuchy: %2d\tProvence: %2d",
			deckSupplyPiles[DomCard.ESTATE.ordinal()],
			deckSupplyPiles[DomCard.DUCHY.ordinal()],
			deckSupplyPiles[DomCard.PROVENCE.ordinal()]
		));
		System.out.println(String.format(
			"Copper: %2d\tSilver: %2d\tGold: %2d",
			deckSupplyPiles[DomCard.COPPER.ordinal()],
			deckSupplyPiles[DomCard.SILVER.ordinal()],
			deckSupplyPiles[DomCard.GOLD.ordinal()]
		));
		System.out.println();
	}

}