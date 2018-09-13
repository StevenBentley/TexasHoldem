package texasholdem;

import texasholdem.Card.Rank;
import texasholdem.Card.Suit;

// Standard 52 card poker deck
public class Deck {

	private Card[] cards = new Card[52];
	
	public Deck() {
		refill();
	}
	
	// Create deck of all card values by looping through each
	// suit and rank and adding a card of each type to the deck
	public final void refill() {
		int i = 0;
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				cards[i++] = new Card(suit, rank);
			}
		}
	}
	
	// Returns card from the deck
	public Card takeCard() {
		Card card = null;
		while (card == null) {
			int index = (int)(Math.random()*cards.length);
			card = cards[index];
			cards[index] = null;
		}
		return card;
	}
}
