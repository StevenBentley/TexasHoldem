package texasholdem;

import java.util.ArrayList;
import java.util.Collections;

import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import texasholdem.ScoreHand.HoldEmHand;
import javafx.scene.Node;


public class HoldemMain extends Application {
	
	private Deck deck = new Deck();
	private Hand player4, player, player2, player3;
	private River river;
	private Text message = new Text();
	private Text playerScore = new Text();
	private Text player2Score = new Text();
	private Text player3Score = new Text();
	private Text player4Score = new Text();
	private ScoreHand playerScore1, playerScore2, playerScore3, playerScore4;
	private HoldEmHand player1Hand, player2Hand, player3Hand, player4Hand; 
	
	// Determine if game is still playable ie. game is over
	private SimpleBooleanProperty playable = new SimpleBooleanProperty(false);
	
	// Layout for cards
	private HBox dealerCards = new HBox(20);
	private HBox playerCards = new HBox(20);
	private HBox player2Cards = new HBox(40);
	private HBox player3Cards = new HBox(40);
	private HBox riverCards = new HBox(20);

	private Parent createContent() {
		
		player4 = new Hand(dealerCards.getChildren());
		player = new Hand(playerCards.getChildren());
		player2 = new Hand(player2Cards.getChildren());
		player3 = new Hand(player3Cards.getChildren());
		river = new River(riverCards.getChildren());
		
		
		Pane root = new Pane();
		root.setPrefSize(1600, 1000);
		
		Region background = new Region();
		background.setPrefSize(1600, 1000);
		background.setStyle("-fx-background-color: rgba(0, 0, 0, 1)");
		
		HBox rootLayout = new HBox(5);
		rootLayout.setPadding(new Insets(5, 5, 5, 5));
		Rectangle leftBackground = new Rectangle(1150, 950);
		leftBackground.setArcWidth(50);
		leftBackground.setArcHeight(50);
		leftBackground.setFill(Color.GREEN);
		Rectangle rightBackground = new Rectangle(430, 950);
		rightBackground.setArcWidth(50);
		rightBackground.setArcHeight(50);
		rightBackground.setFill(Color.ORANGE);
		
		// LEFT
		// Dealer
		VBox leftTopCenterVBox = new VBox(30);
		leftTopCenterVBox.setAlignment(Pos.TOP_CENTER);
		player4Score = new Text("Player 4: ");
		dealerCards.setAlignment(Pos.CENTER);
		leftTopCenterVBox.getChildren().addAll(player4Score, dealerCards, message);
		
		// Player 1
		VBox leftBottomCenterVBox = new VBox(30);
		leftBottomCenterVBox.setAlignment(Pos.BOTTOM_CENTER);
		playerScore = new Text("Player: ");
		playerCards.setAlignment(Pos.CENTER);
		leftBottomCenterVBox.getChildren().addAll(playerCards, playerScore);
		
		// Player 2
		HBox leftLeftCenterVBox = new HBox(15);
		leftLeftCenterVBox.setAlignment(Pos.CENTER_LEFT);
		player2Score = new Text("Player2: ");
		player2Score.setRotate(90);
		player2Cards.setAlignment(Pos.CENTER);
		Group g = new Group();
		StackPane p = new StackPane();
		p.getChildren().addAll(player2Cards);
		g.getChildren().add(p);
		g.setRotate(90);
		leftLeftCenterVBox.getChildren().addAll(player2Score, g);
		
		// Player 3
		HBox leftRightCenterVBox = new HBox(15);
		leftRightCenterVBox.setAlignment(Pos.CENTER_RIGHT);
		player3Score = new Text("Player3: ");
		player3Score.setRotate(270);
		player3Cards.setAlignment(Pos.CENTER);
		Group g2 = new Group();
		StackPane p2 = new StackPane();
		p2.getChildren().addAll(player3Cards);
		g2.getChildren().add(p2);
		g2.setRotate(270);
		leftRightCenterVBox.getChildren().addAll(g2, player3Score);
		
		// River
		VBox leftCenterVBox = new VBox();
		leftCenterVBox.setAlignment(Pos.CENTER);
		riverCards.setAlignment(Pos.CENTER);
		leftCenterVBox.getChildren().addAll(riverCards);
		
		// RIGHT
		VBox rightVBox = new VBox(20);
		rightVBox.setAlignment(Pos.CENTER);
		
		final TextField bet = new TextField("BET");
		bet.setDisable(true);
		bet.setMaxWidth(50);
		Text money = new Text("MONEY");
		
		Button btnPlay = new Button("PLAY");
		Button btnHit = new Button("HIT");
		
		HBox buttonsHBox = new HBox(15, btnHit);
		buttonsHBox.setAlignment(Pos.CENTER);
		
		rightVBox.getChildren().addAll(bet, btnPlay,  money, buttonsHBox);
		
		// Add both stacks to root layout 
		
		rootLayout.getChildren().addAll(new StackPane(leftBackground, leftTopCenterVBox, leftBottomCenterVBox, leftCenterVBox,
				leftLeftCenterVBox, leftRightCenterVBox), new StackPane(rightBackground, rightVBox));
		root.getChildren().addAll(background, rootLayout);
		
		// Bind properties
		
		btnPlay.disableProperty().bind(playable);
		btnHit.disableProperty().bind(playable.not());
		
		
		playerScore1 = new ScoreHand(player.getCards());
		player1Hand = playerScore1.rank;
		playerScore2 = new ScoreHand(player2.getCards());
		player2Hand = playerScore2.rank;
		playerScore3 = new ScoreHand(player3.getCards());
		player3Hand = playerScore3.rank;
		playerScore4 = new ScoreHand(player4.getCards());
		player4Hand = playerScore4.rank;
		
		playerScore.setText("Player: " + player1Hand.getName());
		player4Score.setText("Player4: " + player4Hand.getName());
		player2Score.setText("Player2: " + player2Hand.getName());
		player3Score.setText("Player3: " + player3Hand.getName());
		System.out.println("Test");
		
		// INIT buttons
		
		btnPlay.setOnAction(event -> {
			startNewGame();
		});
		
		btnHit.setOnAction(event -> {
			
			if (river.getCardCount() == 0) {
				river.takeCard(deck.takeCard());
				river.takeCard(deck.takeCard());
				river.takeCard(deck.takeCard());
			}
			else {
				river.takeCard(deck.takeCard());
			}
			
			
			ArrayList<Card> playerAndRiverCards = river.getCards();
			playerAndRiverCards.addAll(player.getCards());
			Collections.sort(playerAndRiverCards);
			
			System.out.println("Player 1: ");
			for (Node card : playerAndRiverCards) {
				System.out.println(card);
			}
			playerScore1 = new ScoreHand(playerAndRiverCards);
			System.out.println("PLAYER SCORE: " + playerScore1.rank + "\n");
			player1Hand = playerScore1.rank;
			playerScore.setText("Player: " + player1Hand.getName());
			
			ArrayList<Card> player2AndRiverCards = river.getCards();
			player2AndRiverCards.addAll(player2.getCards());
			Collections.sort(player2AndRiverCards);
			
			System.out.println("Player 2: ");
			for (Node card : player2AndRiverCards) {
				System.out.println(card);
			}
			playerScore2 = new ScoreHand(player2AndRiverCards);
			System.out.println("PLAYER2 SCORE: " + playerScore2.rank + "\n");
			player2Hand = playerScore2.rank;
			player2Score.setText("Player2: " + player2Hand.getName());
			
			ArrayList<Card> player3AndRiverCards = river.getCards();
			player3AndRiverCards.addAll(player3.getCards());
			Collections.sort(player3AndRiverCards);
			
			System.out.println("Player 3: ");
			for (Node card : player3AndRiverCards) {
				System.out.println(card);
			}
			playerScore3 = new ScoreHand(player3AndRiverCards);
			System.out.println("PLAYER3 SCORE: " + playerScore3.rank + "\n");
			player3Hand = playerScore3.rank;
			player3Score.setText("Player3: " + player3Hand.getName());
			
			ArrayList<Card> player4AndRiverCards = river.getCards();
			player4AndRiverCards.addAll(player4.getCards());
			Collections.sort(player4AndRiverCards);
			
			System.out.println("player4: ");
			for (Node card : player4AndRiverCards) {
				System.out.println(card);
			}
			playerScore4 = new ScoreHand(player4AndRiverCards);
			System.out.println("DEALER SCORE: " + playerScore4.rank + "\n");
			player4Hand = playerScore4.rank;
			player4Score.setText("Player4: " + player4Hand.getName());
			
			System.out.println("\n");
			
			if (playerAndRiverCards.size() >= 7) {
				endGame();
			}
		});;
		
		return root;
	}
	
	private void startNewGame() {
		playable.set(true);
		message.setText("");
		
		deck.refill();
		
		player4.reset();
		player.reset();
		player2.reset();
		player3.reset();
		river.reset();
		
		player4.takeCard(deck.takeCard());
		player4.takeCard(deck.takeCard());
		
		player.takeCard(deck.takeCard());
		player.takeCard(deck.takeCard());
		
		player2.takeCard(deck.takeCard());
		player2.takeCard(deck.takeCard());
		
		player3.takeCard(deck.takeCard());
		player3.takeCard(deck.takeCard());
	}
	
	private void endGame() {
		playable.set(false);
		
		String winner = "Player 1";
		HoldEmHand winningHand = player1Hand;
		
		if (player2Hand.compareTo(winningHand) > 0) {
			System.out.println("p2 should");
			System.out.println(player2Hand.compareTo(winningHand));
			winningHand = player2Hand;
			winner = "Player 2";
		}
		if (player3Hand.compareTo(winningHand) > 0) {
			System.out.println("p3 should");
			System.out.println(player3Hand.compareTo(winningHand));
			winningHand = player3Hand;
			winner = "Player 3";
		}
		if (player4Hand.compareTo(winningHand) > 0) {
			System.out.println("p4 should");
			System.out.println(player4Hand.compareTo(winningHand));
			winningHand = player4Hand;
			winner = "Player 4";
		}
		
		message.setText(winner + " Wins with: " + winningHand.getName());
		
	}
	
	@Override 
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(new Scene(createContent()));
		primaryStage.setWidth(1600);
		primaryStage.setHeight(1000);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Texas Holdem");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
