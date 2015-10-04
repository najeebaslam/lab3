import java.util.*;
import java.io.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

class TicketMachineImpl implements TicketSystem 
{
	private int totalTickets = 1000;

    public String reserve(int nb_tickets, String location) throws RemoteException
    {           
		System.out.printf("Client : "+ location +" : "+ nb_tickets);
		return handleRequest(nb_tickets, location);
    }
    public void addTickets(int a) throws RemoteException{
    	System.out.print("This method will be used latter");
    }
    // synchronized method for dealing with shared critial data <totalTickets>
    // somehow Implementing monitor construct  
    private synchronized String handleRequest(int nb_tickets, String location) throws RemoteException
    {
        String result;
        if (totalTickets >= nb_tickets){
        	totalTickets = totalTickets - nb_tickets;
        	result = "Congrats!! "+ nb_tickets+ " tickets has been researved for : "+ location;
        }else{
        	result = "Sorry !! "+location +" "+  nb_tickets + " Tickets are not available  now.";
        }
        System.out.println("\t Remaining tickets : " + totalTickets); 
        return result;
    }
    
    public static void main(String args[])
    {
		try {

		    System.out.print("Installing security manager...");
		    if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		    }
		    System.out.println("done.");

		    System.out.println("Creating the remotely accessible TicketMachineImpl (and the stub)...");
		    TicketSystem ticket_system = new TicketMachineImpl();	    
		    TicketSystem stub = (TicketSystem) UnicastRemoteObject.exportObject(ticket_system, 0);
		    System.out.println("done.");

		    System.out.println("Registering the stub...");
		    // by default, registry is supposed on localhost:1099
		    Registry registry = LocateRegistry.getRegistry();
		    registry.rebind("reserve_t", stub);
		    System.out.println("done.");

		}
		catch(Exception re){
		    re.printStackTrace();
		}
    }


} 
