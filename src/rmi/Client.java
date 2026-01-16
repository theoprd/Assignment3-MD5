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

        // rmi://SERVER_IP/Server --> SERVER_IP should be changed with the actual IP address
        ServerCommInterface teacherServer = (ServerCommInterface) Naming.lookup("rmi://192.168.0.10/Server");
        ClientCommHandler clientHandler = new ClientCommHandler(teacherServer, teamName);

        teacherServer.register(teamName, clientHandler);

        System.out.println("Registration to teacher server was successful!");

    }
}
