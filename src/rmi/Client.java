package rmi;

import server.ServerCommInterface;

import java.rmi.Naming;

/**
 * Entry point of the master node.
 *
 * This class connects to the teacher server using Java RMI,
 * registers the client, and waits for MD5 cracking problems.
 *
 * Only this node communicates with the teacher server.
 */

public class Client {

    public static void main(String[] args) throws Exception {

        String teamName = "PACKET_SNIFFERS";

        ServerCommInterface teacherServer = (ServerCommInterface) Naming.lookup("rmi://SERVER_IP/Server"); //SERVER_IP string to replace with actual IP
        ClientCommHandler clientHandler = new ClientCommHandler(teacherServer, teamName);

        teacherServer.register(teamName, clientHandler);

        System.out.println("Registration to teacher server was successful!");

    }
}
