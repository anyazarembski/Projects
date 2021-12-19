/*
Name: Anya Zarembski
Date: 03/20/2020
Description: This code creates a deck class using the card class. it also creates methods for the deck class, like shuffle and draw;
Sources Cited: Starting Out With Java by Tony Gaddis and Godfrey Muganda
*/

package cardGame;

import java.util.Random;

public class Deck{
	private Card[] deck; //creates a deck;
	private int top = 0; //initalizes the value of the top of the deck;
	
	public Deck() //creates a deck of 52 cards through nested for loops;
	{
		int[] ranks = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
		char[] suits = {'C', 'D', 'S', 'H'};
		deck = new Card[52]; //the deck class is an array of 52 Cards;
		int deckCount = 0; //intializes a counter for the # within the array;
		
		for (int countS = 0; countS < 4; countS++) // interates through each suit;
		{
			for (int countR = 0; countR < 13; countR++) //interates through each rank;
			{
				deck[deckCount] = new Card(ranks[countR], suits[countS]); //gives each Card within the Deck array a suit and rank;
				deckCount++;
			}
		}
	}
	public void shuffle() //shuffles the deck;
	{
		Random ranNum = new Random(); //creates random variable;
		for (int count = 0; count < 52; count++) //randomizes each card through the swap method within the deck;
		{
			int swap = ranNum.nextInt(51); //chooses random number from 0-51;
			swap(count, swap);
		}
	}
	
	public Card draw() //draws a card;
	{
		Card cardDraw = deck[top]; //pulls card top value in array;
		top++; //makes next card in the deck at the top;
		return cardDraw; //returns the card that was drawn;
		
		
	}
	
	public boolean isEmpty() //checks to see if deck is empty;
	{
		if (top >= 52) //runs if the deck is empty;
		{
			System.out.println("The deck is empty!");
			return false;
		}
		else // runs is the deck isn't empty (aka top is less than 52);
		{
			return true;
		}
	}
	
	private void swap(int i, int j) //swaps two cards in a deck;
	{
		Card temp = deck[i]; //creates a temporary variable to hold value of the first card;
		deck[i] = deck[j]; // makes the first card's value the second card's value;
		deck[j] = temp; //makes the second card's value the same as the temp card;
 	}
	
}