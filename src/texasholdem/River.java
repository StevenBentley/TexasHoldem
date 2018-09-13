package texasholdem;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.Node;

public class River {

	private ObservableList<Node> cards;
	
	public River(ObservableList<Node> cards) {
		this.cards = cards;
	}
	
	// Restart Game
	public void reset() {
		cards.clear();
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
	
	public int getCardCount() {
		return cards.size();
	}
}
