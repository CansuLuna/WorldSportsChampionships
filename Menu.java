import java.io.*;
import java.util.*;
/**
 * This is the Menu class. It has the main method. This class provides the end user with a console.
 * it gives user the chance to check event information, client information, buy tickets and return tickets. 
 * It reads infromation through input file and also prints a message on an output file. 
 * The class contains WriteLetter method to print text to the outFile, and a PrintEvents method that
 * prints event information on console. 
 * @author Cansu Karaboga
 * @version 1.0 12 Dec 2017
 */
public class Menu 
{   private SortedArrayList<Event> eventList = new SortedArrayList<Event>(); //List to hold Events 
    private SortedArrayList<Client> clientList = new SortedArrayList<Client>(); //List to hold Clients 
    private static PrintWriter outFile; 
    private static Scanner inFile;

    /**
     * Main method for WorldSportsChampionships
     */
    public static void main(String[] args) throws FileNotFoundException
    {
        Menu m = new Menu();
        Scanner input = new Scanner(System.in);

        Scanner inFile = new Scanner(new FileReader ("//Users//cansukaraboga//Desktop//WorldSportsChampionships//EventsAndClients.txt"));
        outFile = new PrintWriter ("//Users//cansukaraboga//Desktop//WorldSportsChampionships//Output.txt");
        //Read everything in from the file:
        //Reading in the events:
        int number = inFile.nextInt();
        inFile.nextLine();
        for (int i = 1; i <= number; i++)
        {
            String eventName = inFile.nextLine();
            int tickets = inFile.nextInt();
            inFile.nextLine();
            Event e = new Event(eventName, tickets);
            m.eventList.add(e);
        }

        //Reading in the clients:
        number = inFile.nextInt();
        inFile.nextLine();       
        for (int i = 1; i <= number; i++)
        {
            String clientName = inFile.nextLine();
            String[] cName = clientName.split("\\s");//Split up the clientName into firstname and surname
            int count = 0;
            String firstName= " "; 
            String lastName= " ";
            for(String x:cName)
            {
                if(count == 0)
                {
                    firstName = x;
                }
                else
                {
                    lastName = x;
                }
                count++;
            }
            //Construct client object using first and last name as variables for the constructor 
            Client c= new Client(firstName, lastName);
            m.clientList.add(c);//Add the client to the clientList
        }

        while(true)
        {
            menu();
            m.response();
        }
    }

    //display the menu
    public static void menu()
    {
        System.out.println("\n");

        System.out.println("Please choose one :) \n"); 
        System.out.println("f- finish running the porgram"); 
        System.out.println("e- display information about all events");
        System.out.println("c- display information about all clients");
        System.out.println("b- update data when existing client makes a purchase");
        System.out.println("r- update data when existing client return/cancels purchase");

        System.out.println("\n");          

    }

    public void response() throws FileNotFoundException
    {
        Scanner input = new Scanner(System.in);
        String choice=input.nextLine();

        if(choice.equalsIgnoreCase("f"))
        { 
            System.exit(0);
        } 
        else if(choice.equalsIgnoreCase("e"))// displays event information 
        {
            PrintEvents();           
        }
        else if(choice.equalsIgnoreCase("c"))//displays client information
        {

            for(Client c : clientList)
            {
                c.DisplayClient(); 
            }

        }
        else if(choice.equalsIgnoreCase("b"))// buys tickets for max 3 events per client
        {
            System.out.print("Enter clients first name: ");
            String firstName = input.nextLine().trim();
            System.out.print("Enter clients last name : ");
            String lastName = input.nextLine().trim();
            boolean success = false;
            int count = 0;

            for (int i = 0; i < clientList.size(); i++) 
            {
                if (firstName.equalsIgnoreCase(clientList.get(i).getFirstName())&&
                lastName.equalsIgnoreCase (clientList.get(i).getLastName()))//check if client is in the list
                {       
                    System.out.println("These are the available events");
                    PrintEvents();
                    System.out.println("Please type the event you like to attend to");
                    String eventName = input.nextLine().trim();
                    eventName=eventName.toUpperCase();
                    int temp=0;
                    for(int j=0; j<eventList.size();j++)
                    {
                        if(eventName.equalsIgnoreCase(eventList.get(j).getEventName()))//check if event is in the list
                        { 
                            try{
                                temp=1;
                                System.out.println("This event exist"); 
                                System.out.println("How many tickets would you like to purchase for this event?");
                                int ticketNumber = input.nextInt();

                                if(ticketNumber <= eventList.get(j).getUnsoldTickets() && ticketNumber > 0)//check if required ticket number is available
                                {
                                    boolean valid = clientList.get(i).setBuyTickets(ticketNumber,eventName);
                                    if(valid == true)//buy tickets.
                                    {
                                        eventList.get(j).setUnsoldTickets(ticketNumber);
                                        System.out.println("Purchase complete.");
                                    }
                                    else
                                    {
                                        System.out.println("You cannot add anymore events! List full.");
                                    }
                                }
                                else if(ticketNumber <= 0)
                                {
                                    System.out.println("Please enter a value that is bigger than 0");
                                }
                                else // print note to outFile if requested event doesnt have the requested ticket amount.
                                { 

                                    String sentence = ("Dear  " +clientList.get(i).getFirstName()+ "  "+ clientList.get(i).getLastName()+" "+ " Sorry there are not enough tickets available for this event: " + eventList.get(j).getEventName());
                                    WriteLetter(sentence); 
                                    success = true;
                                    outFile.close(); 
                                    break;
                                }   
                            } catch(InputMismatchException ex)
                            {System.out.println("Try again. (" +"Incorrect input: an integer is required)"); }
                        }

                    }
                    if(temp==0)// print line if event is not in the list
                    {
                        System.out.println("This is not a valid event name");

                    }
                }             
                else// print line if client is not in the list
                {
                    count++;
                    if(count == clientList.size())
                    {

                        System.out.println("Sorry, cannot process your request.This client is not registered ");

                    }
                }

            }
        }

        else if(choice.equalsIgnoreCase("r"))//returns tickets
        {
            System.out.print("Enter clients first name: ");
            String clientName = input.nextLine().trim();
            System.out.print("Enter clients last name : ");
            String clientLastName = input.nextLine().trim();
            boolean temp = false;
            int number = 0;

            for (int x = 0; x < clientList.size(); x++) 
            {
                if (clientName.equalsIgnoreCase(clientList.get(x).getFirstName())&&
                clientLastName.equalsIgnoreCase (clientList.get(x).getLastName()))//check if client is in the list
                {       

                    PrintEvents();
                    System.out.println("Please type the event you like to return tickets for");
                    String event = input.nextLine().trim();
                    event=event.toUpperCase();
                    int t=0;
                    for(int y=0; y<eventList.size();y++)
                    {
                        if(event.equalsIgnoreCase(eventList.get(y).getEventName()))//check if event is in the list
                        {  
                            try{
                                t=1;
                                System.out.println("This event exist"); 
                                System.out.println("How many tickets would you like to return for this event?");
                                int returnNumber = input.nextInt();
                                if(returnNumber > 0)
                                {

                                    boolean valid = clientList.get(x).setReturnTickets(returnNumber, event);
                                    if(valid == true)//return tickets
                                    {
                                        eventList.get(y).setUpdateTickets(returnNumber);
                                        System.out.println("Your return is complete"); 
                                    }   
                                }
                                else
                                {
                                    System.out.println("Please enter a value that is bigger than 0"); 
                                }
                            } catch(InputMismatchException ex)
                            {System.out.println("Try again. (" +"Incorrect input: an integer is required)"); }
                        } 
                    }
                    if(t==0)//print line if event is not in the list
                    {
                        System.out.println("This is not a valid event name");

                    }
                }             
                else
                {
                    number++;
                    if(number == clientList.size())//print line if client is not in the list
                    {

                        System.out.println("Sorry, cannot process your request.This client is not registered ");

                    }
                }               
            }
        }                    
        else// print line if user types unavailable option in the menu
        {
            System.out.println("Please choose a valid option"); 
        }

    }

    /**
     * Method to print event information to the console, including event name and tickets available for that event. 
     * @param no parameter is taken 
     * @return event name and unsold tickets
     */
    public void PrintEvents()
    {
        for(Event e : eventList)
        {
            System.out.println( e.getEventName()+"---> ticketcs available: " +
                e.getUnsoldTickets());

        }
    }

    /**
     * Method to print text to the output file. Also prints apology statement on the console 
     * @param String is taken
     * @return String to console and to outFile. 
     */
    public void WriteLetter(String toPrint) throws FileNotFoundException
    {

        System.out.println("Sorry, we dont have enough tickets, we have sent you a letter");
        outFile.println(toPrint);

    }

}

