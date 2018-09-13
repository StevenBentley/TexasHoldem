package texasholdem;

import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/* Card Class for Texas Holdem */
public class Card extends Parent implements Comparable<Card> {
	
	enum Suit {
		HEARTS(0), DIAMONDS(1), CLUBS(2), SPADES(3);
		
		final int value;
		private Suit(int value) {
			this.value = value;
		}
	};
	
	// Rank values to compare high card
	enum Rank {
		ACE(0), TWO(1), THREE(2), FOUR(3), FIVE(4), SIX(5), SEVEN(6), EIGHT(7), NINE(8), TEN(9),
		JACK(10), QUEEN(11), KING(12);
		
		final int value;
		private Rank(int value) {
			this.value = value;
		}
	};
	
	public final Suit suit;
	public final Rank rank;
	public final int value;
	
	public Card(Suit suit, Rank rank) {
		this.suit = suit;
		this.rank = rank;
		this.value = rank.value;
		
		// FX Graphics of card
		Rectangle background = new Rectangle (80, 100);
		background.setArcWidth(20);
		background.setArcHeight(20);
		background.setFill(Color.WHITE);
		
		Text text = new Text(toString());
		text.setWrappingWidth(70);
		
		// Text on top of card
		getChildren().add(new StackPane(background, text));
	}
	
	@Override // Type of card text display Ex. 'Three Of Clubs'
	public String toString() {
		return rank.toString() + " OF " + suit.toString(); 
	}
	
	public int compareTo(Card card) {
		return Integer.compare(this.rank.value, card.rank.value);
	}
}
