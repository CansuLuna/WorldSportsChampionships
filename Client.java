import java.lang.Comparable;
import java.util.ArrayList;
import java.io.*;
import java.util.*;
/**
 * This is the Client class. It hold information about Clients. This class holds a Compare method to compare client names,
 * setter and getter methods for the objects, A hashmap that holds String as key and int as value , a Display method for
 * client information, and buy and return methods. 
 * 
 * @author Cansu Karaboga
 * @version 1.0 12 Dec 2017
 */
public class Client implements Comparable<Client>
{

    private String firstName;
    private String lastName;
    private int maxEventAttending = 3;
    private HashMap<String,Integer> buy;  

    /**
     * Constructor for objects of class Client
     */
    public Client( String firstName, String lastName)
    {
        this.firstName=firstName;
        this.lastName=lastName;
        buy = new HashMap<String,Integer>();
    }
    
    public int getMaxEventAttending()
    {
        return maxEventAttending;
    }

    /**
     * Method to compare clients based on their last name first, if same, then compared the first names. 
     * @param Client is taken
     */
    public int compareTo(Client c)
     { 
         
         int lName = lastName.compareToIgnoreCase(c.lastName) ;
        if (lName !=0) return lName;
        int fName = firstName.compareToIgnoreCase(c.firstName) ;
        if (fName !=0) return fName;
        else return 0;    
    }
    
    public void setFirstName(String firstName)
    {
        this.firstName=firstName;
    }

    public String getFirstName()
    {
        return firstName;   
    }

    public void setLastName(String lastName)
    {
        this.lastName=lastName;
    }

    public String getLastName()
    {
        return lastName;   
    }
    /**
     * Method to buy tickets for client. It will update the hashmap accordingly.  
     * @param integer and string values
     */
    public boolean setBuyTickets (int buyTickets, String eventName) 
    {
        if(buy.containsKey(eventName))// checks if hashmap already has the event, if yes, then only adds up new value
        {
            int ticketsAlreadyPurchased = buy.get(eventName);
            int totalTickets = ticketsAlreadyPurchased + buyTickets;
            buy.replace(eventName,totalTickets);           
            return true;
        }
        else if(buy.size() == 0)//if hashmap doesnt have the event, adds the event as key and adds the value
        {
            buy.put(eventName,buyTickets);
            return true;
        }
        else if(buy.size() < maxEventAttending)// if Hashmap size less than the max. event (3), then updates value
        {
            buy.put(eventName, buyTickets);
            return true;
        }
        else//if client already attending 3 events
        {
            return false;
        }
    }

    /**
     * Method to return tickets for client. It will update the hashmap accordingly.  
     * @param integer and string values
     */
    public boolean setReturnTickets(int returnTickets, String eventName)
    {
        if(buy.containsKey(eventName))// checks if hashmap has the event 
        {
            int ticketsAlreadyPurchased = buy.get(eventName);
            if(buy.get(eventName) == returnTickets)//return the ticket and removes the key from hashmap
            {
                buy.remove(eventName);
                return true;
            }
            else if(ticketsAlreadyPurchased < returnTickets)//prints if client attempts to return more tickets than they bought
            {
                System.out.println("You cannot return more tickets than you have bought for this event.");
                return false;
            }
            else //return tickets and update the hashmap accordingly
            {
                int totalTickets = ticketsAlreadyPurchased - returnTickets;
                buy.replace(eventName,totalTickets);
                return true;
            }
        }
        else if(buy.size() == 0) // if the client has epthy hashmap
        {
            
            System.out.println("Sorry, you do not have tickets for this event.");
            return false;
        }
        else//prints out if hashmap doesnt have the event name as a key
        {
            System.out.println("Event is not on your list, so cannot return tickets for an event you are not going to.");
            return false;
        }
    }
   
    public Map<String, Integer> getHashMap()
    {
        return this.buy;
    }
    /**
     * Method to display client infromation including first name, last name, events they are attending, # of tickets
     * they have purchased for each event.
     */
    public void DisplayClient()
    {
        System.out.println("Name: "+ firstName + ", "+"Last Name: "+ lastName);

        for(Map.Entry<String, Integer> e: buy.entrySet())
        {  
 
            if(buy.size()>0)
            {             
             System.out.println("event name: " + e.getKey() + ", "+ "number of tickets: " + e.getValue());           
            }
            
        }       
    }    
}