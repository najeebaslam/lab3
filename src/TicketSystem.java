import java.util.*;
import java.io.*;

import java.rmi.*;

public interface TicketSystem extends Remote
{

    public String reserve (int nb_tickets, String location) throws RemoteException;  // called by the clients to reserve tickets,
    public void addTickets(int a) throws RemoteException; //called periodically to add new tickets

}
