import rmi.WorkCoordinator;

import java.security.MessageDigest;

public class Test {

    public static void main(String[] args) throws Exception {

        WorkCoordinator wc = new WorkCoordinator(null, "TEST");

        // Example numeric password
        String password = "1234";

        // Compute MD5 hash
        byte[] hash = MessageDigest.getInstance("MD5")
                .digest(password.getBytes("UTF-8"));

        // Start cracking with problem size
        wc.startCracking(hash, 5000);
    }
}
