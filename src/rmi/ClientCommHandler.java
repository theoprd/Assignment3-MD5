package rmi;

import server.ServerCommInterface;

/**
 * RMI callback handler for receiving MD5 cracking problems.
 *
 * This class is invoked by the teacher server when a new problem
 * is available and delegates the work to the WorkCoordinator.
 */


public class ClientCommHandler implements ClientCommInterface {

    private final WorkCoordinator workCoordinator;

    public ClientCommHandler(ServerCommInterface server, String teamName) {
        this.workCoordinator = new WorkCoordinator(server, teamName);
    }

    @Override
    public void publishProblem(byte[] hash, int problemsize) throws Exception {
        System.out.println("Problem received. The maximum problem size is: " + problemsize);
        workCoordinator.startCracking(hash, problemsize);
    }
}
