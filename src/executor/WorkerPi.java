package executor;

import utils.MD5Cracker;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Worker node running on a Raspberry Pi.
 *
 * This class listens for incoming TCP connections from the master node.
 * Upon receiving an MD5 hash and a numeric range, it brute-forces the range
 * to find a matching MD5 hash and returns the result.
 *
 * The worker does not communicate with the teacher server directly.
 */

public class WorkerPi {

    private static final int PORT = 9000;

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(PORT);
        System.out.println("Pi listens on port " +  PORT);

        while (true) {
            Socket socket = server.accept();
            try(
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream())
            ) {
                byte[] hash = (byte[]) input.readObject();
                int start = input.readInt();
                int end = input.readInt();

                System.out.println("Working on range [" + start + ", " + end + "]");

                int solution = MD5Cracker.crack(hash, start, end);
                output.writeInt(solution);
                output.flush();
            }
            socket.close();
        }
    }
}
