package mouse.univ.core;

import java.math.BigInteger;

public interface KeyGenerator {
    BigInteger generateKeyBits(int bits);
    BigInteger generateKeyBytes(int bytes);
}
