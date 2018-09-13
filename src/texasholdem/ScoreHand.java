package texasholdem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScoreHand {
	
	public enum HoldEmHand {
	 	
		HIGH_CARD("High Card"),
	    ONE_PAIR("One Pair"),
	    TWO_PAIR("Two Pairs"),
	    THREE_OF_A_KIND("Three of a Kind"),
	    STRAIGHT("Straight"),
	    ROYAL_STRAIGHT("Royal Straight"),
	    FLUSH("Flush"),
	    FULL_HOUSE("Full House"),
	    FOUR_OF_A_KIND("Four of a Kind"),
	    STRAIGHT_FLUSH("Straight Flush"),
	    ROYAL_FLUSH("Royal Flush");

	    private String name;

	    HoldEmHand(String name) {
	        this.name = name;
	    }

	    public String getName() {
	    	return name;
	    }

		public void addListener(Object object) {
			// TODO Auto-generated method stub
			
		}
	}
	
	// constants for evaluating pairs
    public static final int ONE_PAIR = 7;
    public static final int TWO_PAIR = 9;
    public static final int THREE_OF_A_KIND = 11;
    public static final int FULL_HOUSE = 13;
    public static final int FOUR_OF_A_KIND = 17;
    
    ArrayList<Card> hand;               // the hand
    int[] faceFrequency = new int[13];  // frequency table for face values
    int[] suitFrequency = new int[4];   // frequency table for suits
    boolean hasAce;                     // hand contains at least one Ace
    boolean isRoyal;                    // hand contains a straight that is 'royal' i.e. A-10-J-Q-K
    HoldEmHand rank;                    // calculated rank of the hand
    int highCard;                       // highest card in hand for tie breakers
    
    public ScoreHand(ArrayList<Card> cards) {
    	
    	this.hand = cards;
    	getFrequencies();
    	rank = HoldEmHand.HIGH_CARD;
    	findHighCard();
    	rankHand();
    }
    
    private void rankHand() {
    	
    	// Find all straight possibilities
    	if (isStraight()) {
    		if (isFlush()) {
    			// Figure out how to set isRoyal
    			rank = HoldEmHand.STRAIGHT_FLUSH;
    		} else {
    			rank = HoldEmHand.STRAIGHT;
    		}
    	} else {
    		if (isFlush()) rank = HoldEmHand.FLUSH;
    	}
    	
    	// Find pairs 
    	int pairs = getPairSum();
    	System.out.println(" pairs is : " + pairs);
    	switch(pairs) {
    		case ONE_PAIR:
    			if (rank.compareTo(HoldEmHand.ONE_PAIR) < 0) rank = HoldEmHand.ONE_PAIR;
    			break;
    		case TWO_PAIR:
    			if (rank.compareTo(HoldEmHand.TWO_PAIR) < 0) rank = HoldEmHand.TWO_PAIR;
    			break;
    		case THREE_OF_A_KIND:
    			if (rank.compareTo(HoldEmHand.THREE_OF_A_KIND) < 0) rank = HoldEmHand.THREE_OF_A_KIND;
    			break;
    		case FULL_HOUSE:
    			if (rank.compareTo(HoldEmHand.FULL_HOUSE) < 0) rank = HoldEmHand.FULL_HOUSE;
    			break;
    		case FOUR_OF_A_KIND:
    			if (rank.compareTo(HoldEmHand.FOUR_OF_A_KIND) < 0) rank = HoldEmHand.FOUR_OF_A_KIND;
    			break;
    		default:
    	}
    	
    }
    
    // Fill suit and number frequency arrays with hand data
    private void getFrequencies() {
    	for (Card card : hand) {
    		if (card.rank.value == 0) hasAce = true;
    		faceFrequency[card.rank.value]++;
    		suitFrequency[card.suit.value]++;
    	}
    }
    
    // Use suit frequency array to determine if hand has flush
    private boolean isFlush() {
    	for (int each : suitFrequency) {
    		if (each == 5) return true;
    	}
    	return false;
    }
    
    private int[] getSortedHandRankValues() {
    	int[] sorted = new int[hand.size()];
    	int i = 0;
    	for (Card card : hand) {
    		sorted[i++] = card.rank.value;
    	}
    	Arrays.sort(sorted);
    	return sorted;
    }
    
    // Check for straight -- need to implement check for royal flush
    private boolean isStraight() {
    	int[] sorted = getSortedHandRankValues();
    	
    	int straightCount = 0;
    	for (int i=1; i<sorted.length; i++) {
    		if (sorted[i] - sorted[i - 1] > 1) return false;
    		else straightCount++;
    		if (straightCount == 5) return true;
    	}
    	return false;
    }
    
    // Get number for the different pair possibilities using frequency table
    private int getPairSum() {
    	int handSize = hand.size();
    	int sum = 0;
    	for (int each : faceFrequency) {
    		sum += each * each;
    	}
    	// If hand size is larger than five subtract extra card to properly evaluate pairs
    	if (handSize == 6) sum = sum - 1;
    	if (handSize == 7) sum = sum - 2;
    	return sum;
    }
    
    // Find and set high card for tie breaker
    private void findHighCard() {
    	int[] sorted = getSortedHandRankValues();
    	if (hasAce) {
    		for (Card card : hand) {
    			if (card.value == 0) highCard = card.value;
    		}
    	} else {
    		int max = -1;
    		for (Card card : hand) {
    			if (card.value > max) {
    				max = card.value;
    				highCard = card.value;
    			}
    		}
    	}
    }
    
}


