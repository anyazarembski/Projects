/*
Name: Anya Zarembski
Date: 03/20/2020
Description: This code plays a game of War, using the deck class;
Sources Cited: Starting Out With Java by Tony Gaddis and Godfrey Muganda
*/

package cardGame;

import java.io.IOException;

public class War {
	private static Card player; //creates a card for the player;
	private static Card computer; //creates a card for the computer;
	private static int playerPoints = 0; //creates a value for the player's points;
	private static int computerPoints = 0; //creates a value for the computer's points;
	private static Deck warDeck; //creates a deck for the game of war;
	
	public static void main(String[] args) {
		System.out.println("Let's play war!"); //asks the user to play;
		pressEnter();
		warDeck = new Deck(); //makes a new deck;
		warDeck.shuffle(); //shuffles that deck;
		while (warDeck.isEmpty()) //creates a loop to continue while the deck still has cards;
		{
			Turn(); //calls the turn method;
			pressEnter();
		}
		pressEnter();
		System.out.println("The game is over!"); //tells the user that the game is over;
		if (playerPoints > computerPoints) //runs and outputs that the player won, if they have more points;
		{
			System.out.println("You won with " + playerPoints + " points! Congrats!");
		}
		if (playerPoints < computerPoints) //runs and outputs that the computer won, if they have more points;
		{
			System.out.println("The computer won with " + computerPoints + " points! Try again next time!");
		}
		if (playerPoints == computerPoints) //runs and outputs if the computer and player tied;
		{
			System.out.println("You tied! Fight harder next time!");
		}
	}
	
	public static void Turn() { //method that takes a turn;
		drawCards();
		if (player.getRank() > computer.getRank()) //runs if player wins;
		{
			ifPlayer(); 
		}
		if (player.getRank() < computer.getRank()) //runs if computer wins;
		{
			ifComputer();
		}
		if (player.getRank() == computer.getRank()) //runs if computer and player tie;
		{
			ifWar();
		}
	}
	
	public static void drawCards() { //draws cards for both players;
		player = warDeck.draw(); //draws card for player;
		System.out.println("You drew a " + player.getRank() + " of " + player.getSuit());
		computer = warDeck.draw(); //draws card for the computer;
		System.out.println("Your opponent drew a " + computer.getRank() + " of " + computer.getSuit());
	}
	
	public static void ifPlayer() //method for when the player wins a round;
	{
		
			System.out.println("You win the round!");
			playerPoints += 2; //adds two points for both cards to the player's points;
	}
	
	public static void ifComputer()//method for when the computer wins a round;
	{
		computerPoints += 2; //adds two points for both cards to the computer's points;
		System.out.println("The computer wins the round!");
	}
	
	public static void ifWar() //method for if players tie;
	{
			System.out.println("Uh oh... you and the computer tied. Time to go to WAR!!!!");
			warTurn();
	}
	
	public static void warTurn() { //turn for when there's a war turn;
		System.out.println("You need to draw another set of cards");
		pressEnter();
		drawCards();
		if (player.getRank() > computer.getRank())//player wins war;
		{
			ifPlayer();
			playerPoints += 2;//extra points awarded for the extra turn;
		}
		if (player.getRank() < computer.getRank())//computer wins war;
		{
			ifComputer();
			computerPoints += 2;//extra  points for extra turn;
		}
		if (player.getRank() == computer.getRank())//tie again;
		{
			System.out.println("Uh oh... you and the computer tied again. Let's try one more time!!!!");//goes to war again;
			pressEnter();
			drawCards();
			if (player.getRank() > computer.getRank())//player wins;
			{
				ifPlayer();
				playerPoints += 4;//extra points for two extra turns;
			}
			if (player.getRank() < computer.getRank())//computer wins;
			{
				ifComputer();
				computerPoints += 4;//extra points for two extra turns;
			}
			if (player.getRank() == computer.getRank())//final tie;
			{
				System.out.println("You and the computer tied again! No one wins this round!");
				//points split evenly between player and computer;
				playerPoints += 3;
				computerPoints += 3;
			}
			
		}
	}
	
	public static void pressEnter(){  //changed the name so it flows better in the code and is easier to understand;
		try {
		System.in.read(new byte[2]);
		}
		catch (IOException e) {
		e.printStackTrace();
		}

	 }

}
