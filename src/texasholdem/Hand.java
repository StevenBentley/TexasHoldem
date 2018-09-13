package texasholdem;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import texasholdem.Card.Rank;
import texasholdem.ScoreHand.HoldEmHand;

// Player or dealer texas holdem hand
public class Hand {

	private ObservableList<Node> cards;
	private HoldEmHand value;
	
	public Hand(ObservableList<Node> cards) {
		this.cards = cards;
	}
	
	public void takeCard(Card card) {
		cards.add(card);
	}
	
	public ArrayList<Card> getCards() {
		ArrayList<Card> cardList = new ArrayList<>();
		for (Node currentNode : cards) {
			if (currentNode instanceof Card) {
				cardList.add((Card)currentNode);
			}
		}
		return cardList;
	}
	
	// Restart Game
	public void reset() {
		cards.clear();
		value = null;
	}
	
	public HoldEmHand valueProperty() {
		return value;
	}
	
}
