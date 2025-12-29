package server;

import rmi.ClientCommInterface;
import java.rmi.Remote;

/**
 * RMI interface provided by the teacher server.
 *
 * The client uses this interface to register itself and submit
 * correct MD5 cracking solutions.
 */


public interface ServerCommInterface extends Remote {

    public void register (String teamName, ClientCommInterface cc) throws Exception;
    
    public void submitSolution(String name, String sol) throws Exception;
}
