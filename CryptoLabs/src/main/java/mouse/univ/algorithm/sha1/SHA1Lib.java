package mouse.univ.algorithm.sha1;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1Lib {
    public String hash(String textToHash) {
        try {
            return sha1Hash(textToHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-1 Algorithm not found");
        }
    }

    public static String sha1Hash(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] bytes = input.getBytes();
        byte[] result = md.digest(bytes);
        StringBuilder sb = new StringBuilder();
        for (byte b : result) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
