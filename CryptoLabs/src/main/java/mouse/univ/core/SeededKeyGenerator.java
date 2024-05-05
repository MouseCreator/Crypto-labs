package mouse.univ.core;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.SecureRandom;

public class SeededKeyGenerator implements KeyGenerator {
    private final SecureRandom random;

    public SeededKeyGenerator(long seed) {
        byte[] array = extractBytes(seed);
        this.random = new SecureRandom(array);
    }

    private static byte[] extractBytes(long seed) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(seed);
        return buffer.array();
    }

    @Override
    public BigInteger generateKey(int bits) {
        return new BigInteger(bits, random);
    }
}
