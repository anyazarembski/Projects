/*
Name: Anya Zarembski
Date: May 4th 2021
Description: This code creates a Tweet and methods that can be used to interact with the Tweet;
Sources Cited: Starting Out With Java by Tony Gaddis and Godfrey Muganda 
*/

package tweetlist;

import java.util.Scanner;

public class Tweet {
	//creating all variables needed for a Tweet: latitude, longitude, year, month, day, time, and text;
	private double lat, lon;
	private int year, month, day;
	private String time, text;
	
	public Tweet(String s) //creates a Tweet;
	{
		//creates Scanner for each tweet, parses through the tweet data, assigning data to latitude and longitude first;
		Scanner scan = new Scanner(s);
		lat = scan.nextDouble();
		lon = scan.nextDouble();
		scan.nextInt();
		//creates a date String first, then splits that string into the year, month, and day variables;
		String date = scan.next();
		String[] d = date.split("-");
		year = Integer.parseInt(d[0]);
		month = Integer.parseInt(d[1]);
		day = Integer.parseInt(d[2]);
		//assigns the time and text of the Tweet to variables.
		time = scan.next();
		text = scan.nextLine();
		scan.close();
	}
	
	public void print() //prints a Tweet;
	{
		//prints each part of a Tweet with a label for each part;
		System.out.println("Text: " + text);
		System.out.println("Location: " + lat + ", " + lon);
		System.out.println("Date: " + month + "/" + day + "/" + year);
		System.out.println("Time: " + time + "\n");
	}
	
	public boolean textContains(String searchTerm) //Determines if the text of a Tweet contains a certain keyword;
	{
		//Initializes an index variable to -1, then calls the indexOf() method to set the index variable to the index if the keyword is in the text;
		int index =  -1;
		index = text.indexOf(searchTerm);
		if (index >=0) //returns true if the keyword is in the text, shown by a changed index;
			return true;
		else //returns false if the keyword is not in the text, shown by an unchanged index;
			return false;
	}
}
