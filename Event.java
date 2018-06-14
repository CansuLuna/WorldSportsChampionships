import java.util.ArrayList;
import java.io.*;
import java.util.*;
/**
 * This is the Event class. It holds information about events. Has a compare to method that compare event names, 
 * methids to uodate unsoldTickets when tickets are bought or returned. 
 * 
 * @author Cansu Karaboga
 * @version 1.0 12 Dec 2017
 */
public class Event implements Comparable<Event>
{
    private String eventName;
    private int unsoldTickets;
    /**
     * Constructor for objects of class Event
     */
    public Event( String eventName, int unsoldTickets)
    {
        this.eventName=eventName;
        this.unsoldTickets=unsoldTickets;
    }
    /**
     * Method to compare events based on their names. 
     * @param Event is taken
     */   
    public int compareTo(Event e)
    {   
       int x =eventName.compareToIgnoreCase(e.eventName);
       if (x != 0 )
       return x;
       else return 0;
       
    }
    public String getEventInformation()
    {
        return this.eventName+"\t"+this.unsoldTickets;
    
    }
    public void setEventName(String eventName)
    {
        this.eventName=eventName;
    }

    public String getEventName()
    {
        return eventName;   
    }
    /**
     * Method to update available ticket numbers when tickets are bought. 
     * @param int is taken
     */   
    public void setUnsoldTickets(int ticketsWanted) 
    {
        this.unsoldTickets = this.unsoldTickets - ticketsWanted;
    }
    
    /**
     * Method to update available ticket numbers when tickets are returned/canceled. 
     * @param int is taken
     */   
    public void setUpdateTickets (int updateTickets)
    {
       this.unsoldTickets=this.unsoldTickets + updateTickets; 
    }
    
    public int getUnsoldTickets()
    {
        return unsoldTickets;
    }
    
    public void setTicketsAvailable ( int ticketsAvailable)
    { 
      unsoldTickets=ticketsAvailable;
    }
}

