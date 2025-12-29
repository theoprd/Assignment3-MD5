package rmi;

import server.ServerCommInterface;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Coordinates the distributed cracking process.
 *
 * This class splits the search space among available Raspberry Pis,
 * launches worker threads, collects results, and submits the solution
 * to the teacher server.
 *
 * An AtomicBoolean is used to ensure that only one correct solution
 * is submitted.
 */


public class WorkCoordinator {

    private final ServerCommInterface server;
    private final String teamName;

//    private final String[] piIp = {
//            "",
//            ""
//    };
//    private final int PORT = 9000;  // to uncomment for final project

    private final String[] piIp = {"localhost", "localhost"};
    // ports array temporary for testing
    private final int[] ports = {9000, 9001};
    private final AtomicBoolean hashCracked = new AtomicBoolean(false);

    public WorkCoordinator(ServerCommInterface server, String teamName) {
        this.server = server;
        this.teamName = teamName;
    }

    public void startCracking(byte[] hash, int problemsize) {

        System.out.println("Starting distributed cracking...");
        System.out.println("Problem size: " + problemsize);

        int pisNumber = piIp.length;
        int segmentSize = (problemsize + 1) / pisNumber;

        for (int i = 0; i < pisNumber; i++) {
            int start = i * segmentSize;
            int end = (i == pisNumber - 1) ? problemsize : (start + segmentSize - 1);

            System.out.println("Assigning Pi " + piIp[i] + " range [" + start + ", " + end + "]");

            new Thread(new PisExecutors(
                    piIp[i],
                    ports[i],
                    //PORT,
                    hash,
                    start,
                    end,
                    this
            )).start();
        }
    }

    public synchronized void giveSolution(int solution) throws Exception {
        if (hashCracked.get()) return;

        hashCracked.set(true);
        System.out.println("The solution is: " + solution);

        if (server != null) {
            server.submitSolution(teamName, String.valueOf(solution));
        }

        //server.submitSolution(teamName, String.valueOf(solution));
    }

}
