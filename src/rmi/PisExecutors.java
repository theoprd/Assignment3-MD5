package rmi;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Runnable task responsible for communicating with a worker Pi.
 *
 * Each instance sends a specific numeric range and hash to a worker,
 * waits for the result, and forwards any found solution to the
 * WorkCoordinator.
 */


public class PisExecutors implements Runnable {

    private final String piIp;
    private final int port;
    private final byte[] hash;
    private final int start;
    private final int end;
    private final WorkCoordinator workCoordinator;

    public PisExecutors(
            String piIp,
            int port,
            byte[] hash,
            int start,
            int end,
            WorkCoordinator workCoordinator
    ) {
        this.piIp = piIp;
        this.port = port;
        this.hash = hash;
        this.start = start;
        this.end = end;
        this.workCoordinator = workCoordinator;
    }

    @Override
    public void run() {
        try (
             Socket socket = new Socket(piIp, port);
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream())
        ) {
            System.out.println("Connected to Pi " + piIp + " range [" + start + ", " + end + "]");

            output.writeObject(hash);
            output.writeInt(start);
            output.writeInt(end);
            output.flush();

            int solution = input.readInt();
            if (solution != -1) {
                workCoordinator.giveSolution(solution);
            }
        } catch (Exception e) {
            System.err.println("Pi unreachable: " + piIp + " (range " + start + "-" + end + ")");
        }
    }
}
