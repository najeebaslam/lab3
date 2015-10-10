import java.util.*;
import java.io.*;
import java.lang.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


class clientThread implements Runnable {
  private Thread t;
  private String clientName;
  private TicketSystem ticketHandler; // ticket handler, each thread will get ticketHader as perameter at time of creation 

  public clientThread(TicketSystem th, String city){
      ticketHandler = th;
   	  clientName = city;
    }
   
  public void run(){
    try {
        Random randomGenerator = new Random();
        int sleepTime = randomGenerator.nextInt(100000);
        Thread.sleep(sleepTime);
        int ticketCount = randomGenerator.nextInt(15)+1;
	   	  String status = ticketHandler.reserve(ticketCount, clientName);
        System.out.println(status);
      }catch(Exception e){
        e.printStackTrace();
      }
    } 

  }

}

public class Client
{
  public static void main(String args[]) {
    String[] cities = {"Paris    ","Nice     ", "Rennes   ", "Nantas   ", "Milan    ", "Bolonigia", "Verona   ", "Como     "};
    int i= 1;
      try{
          System.out.print("Installing security manager...");
          if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
          }
          System.out.println("done.");
          System.out.print("Getting the registry...");
          Registry registry = LocateRegistry.getRegistry();
          System.out.println("done.");
          System.out.print("Getting the stub...");
          TicketSystem ticketHandler = (TicketSystem)registry.lookup("reserve_t");
          do{
            for (String city : cities){
                clientThread R1 = new clientThread(ticketHandler, city);
                R1.start();
            }
            i++;
          }while(i<50);  
      } catch (Exception e) {
          e.printStackTrace();
        }
  }

} // Client



