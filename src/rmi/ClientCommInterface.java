package rmi;

import java.rmi.Remote;

/**
 * RMI interface implemented by the client.
 *
 * The teacher server uses this interface to publish new MD5 cracking
 * problems to the client.
 */


public interface ClientCommInterface extends Remote {
    
    void publishProblem(byte[] hash, int problemsize) throws Exception;
}
