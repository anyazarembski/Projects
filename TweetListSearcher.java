/*
Name: Anya Zarembski
Date: May 4th 2021
Description: This code interacts with the user to sort through a list of tweets to find keywords or phrases;
Sources Cited: Starting Out With Java by Tony Gaddis and Godfrey Muganda 
*/

package tweetlist;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TweetListSearcher {

	public static void main(String[] args) {
		
		TweetList list = new TweetList(); //creates a new TweetList called list;
		
		//creates a Scanner, asks for the name of the file, and initializes the name of the file to the variable fname;
		Scanner keyboard = new Scanner(System.in);
		System.out.println("What is the name of the file containing Tweets?: " );
		String fname;
		fname = keyboard.nextLine();
		
		//trys to run the following, will catch any errors that occur;
		try
		{
			//reads file, creates variable read to read through the file, line by line;
			FileReader file = new FileReader(fname);
			BufferedReader read = new BufferedReader(file);
			String line =read.readLine();
			
			while (line != null) //Iterates through the file;
			{
				Tweet nextTweet = new Tweet(line); //creates a new Tweet from each line;
				list.prepend(nextTweet); //adds Tweet to the list;
				line = read.readLine(); //moves to next line;
			}
			
			//asks user if they would like to filter out replies using the keyboard scanner;
			System.out.println("Would you like to exclude replies from your search? \n1 = Yes \n2 = No\nEnter a Choice: ");
			int replies = keyboard.nextInt();
			if (replies == 1) //filters out replies if the user wants to, using the filterReplies function;
				list.filterReplies();	
			
			search(read, list, keyboard); //calls the search function;
			read.close();//closes the BufferedReader;
		}
		catch (FileNotFoundException e)//catches an error if the program cannot find the file;
		{
		System.out.println("The file " + fname + " has not been found.");
		}
		catch (IOException e)//catches an error if the program cannot read the file properly;
		{
		System.out.println("An error occurred while reading " + fname + ".");
		}
		catch (java.util.InputMismatchException e) //catches an error if the user doesn't input an integer when selecting a choice;
		{
			System.out.println("That is not a valid choice.");
		}
		
		keyboard.close();//closes the keyboard;
	}
	
	//search function, takes BufferedReader, TweetList, and Scanner variables;
	static private void search(BufferedReader r, TweetList t, Scanner scan) 
	{
		String search; //creates a string named search, asks the user what they want to search for, and assigns that string to the search variable.
		System.out.println("What word or phrase do you want to search for?: ");
		search = scan.next();
			
		t.filter(search); //calls filter function for search variable;
	
		//asks the user what they want to do, using keyboard;
		System.out.println("Your search produced " + t.size() + " results. What would you like to do?");
		System.out.println("1 = Search for another value within these Tweets \n2 = Print Results \n3 = Exit \nEnter a Choice: ");				
		int choice = scan.nextInt();
		
		if (choice == 1) //runs if the user chooses to search for another string, and calls the search function recursively;
			search(r, t, scan);
		
		else if (choice == 2) //runs if the user wants to print the Tweet list, prints the list, asks what the user wants to do;
		{
			t.print();
			System.out.println("What would you like to do?");
			System.out.println("1 = Search for another value within these results \n2 = Exit \nEnter a Choice: ");
			choice = scan.nextInt();
				
			if (choice == 1) //runs if the user chooses to search for another string, and calls the search function recursively;
				search(r, t, scan);
			else//runs if the user wants to exit;
				System.out.println("\nThank you for using our Tweet-searching service!");
			}
		else//runs if the user wants to exit;
			System.out.println("\nThank you for using our Tweet-searching service!");

	}
}
	

