/*
Name: Anya Zarembski
Date: May 4th 2021
Description: This code creates a list of Tweets and methods to interact with that list;
Sources Cited: Starting Out With Java by Tony Gaddis and Godfrey Muganda 
*/

package tweetlist;

public class TweetList {
    private class Node 
    {
        //variables for the Nodes, a Tweet and the Tweet the node is pointing at;
    	Tweet value;   
        Node next;      
        
        // Constructor for a node.
        Node(Tweet val, Node n)
        {
            value = val;
            next = n;
        } 
    }	
	 
    private Node head;
    
    //creates an empty Tweet list;
    public TweetList()
    {
    	head = null;
    }
    
    //prints each Tweet in the Tweet list;
    public void print()
    {
    	Node curr = head; //Initializes the current Node to the head Node;
    	while (curr != null) //iterates through the Tweet list and prints each individual Tweet;
    	{ 
    		curr.value.print();
    		curr = curr.next;
    	}
    }
    
    //adds a new Tweet to the beginning of the list;
    public void prepend(Tweet newValue)
    {
    	 Node curr = new Node(newValue, head); //creates a new Node;
         head = curr; //makes the head the new value;
    }
    
    //returns the number of Tweets in the Tweet list;
    public int size()
    {
    	int i = 0; //counter variable;
        Node index = head;  
        while (index != null) //iterates through the Tweet list;
        {
            i ++; //adds to counter for each Tweet;
            index = index.next; //moves to next Tweet;
        }
        return i; //returns number of Tweets;
    }
    
    //filters the Tweet List so that only Tweets with a keyword remain;
    public void filter(String keyword)
    {
    	//Initializes a current node and a previous node;
    	Node curr = head;
    	Node prev = null;
    	
    	while(curr != null) //iterates through the Tweet list;
    	{
    		if (!head.value.textContains(keyword)) //checks if the head value doesn't contain the keyword, then makes the head the second value in the list;
    		{
    			head = head.next;
    			curr=head;
    		}
    		else if (!curr.value.textContains(keyword)) //checks if the current Tweet doesn't contain the keyword, then makes the previous next variable equal to the current next;
    		{
    			prev.next = curr.next;
    			curr = curr.next;
    		}
    		else //runs if the Tweet contains the keyword, makes the current value the previous value and the current value equal to the next value;
    		{
    			prev = curr;
    			curr = curr.next;
    		}
    	}
    }
    
    //filters out the replies from the Tweet list;
    public void filterReplies() {
    	
    	//Initializes a current node and a previous node;
    	Node curr = head;
    	Node prev = null;
    
    	while(curr != null) //iterates through the Tweet list;
    	{
    		if (head.value.textContains("@"))//runs if the head contains a reply, removing the Tweet from the list;
    		{
    			head = head.next;
    			curr=head;
    		}
    			
    		else if (curr.value.textContains("@"))//runs if the Tweet contains a reply, removing the Tweet from the list;
    		{
    			prev.next = curr.next;
    			curr = curr.next;
    		}
    			
    		else //runs if the Tweet does not contain a reply, keeping the Tweet in the list;
    		{
    			prev = curr;
    			prev.next = curr.next;
    		}
    	}
    }
}


