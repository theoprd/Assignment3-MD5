package utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * Utility class for MD5 brute-force computation.
 *
 * This class attempts to find a numeric string whose MD5 hash
 * matches the target hash within a given range.
 */


public class MD5Cracker {
    public static int crack(byte[] hash, int start, int end) throws Exception {

        MessageDigest md = MessageDigest.getInstance("MD5");
        for (int i = start; i <= end; i++) {
            String target = String.valueOf(i);
            byte[] targetBytes = target.getBytes(StandardCharsets.UTF_8);
            byte[] targetHash = md.digest(targetBytes);

            if (Arrays.equals(targetHash, hash)) {
                return i;
            }
        }
        return  -1;
    }
}
