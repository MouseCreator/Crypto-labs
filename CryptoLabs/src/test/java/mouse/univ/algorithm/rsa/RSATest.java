package mouse.univ.algorithm.rsa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class RSATest {
    private RSA rsa;
    @BeforeEach
    void setUp() {
        rsa = new RSA();
    }
    @Test
    void testSmallNumbers() {
        BigInteger p = BigInteger.valueOf(7);
        BigInteger q = BigInteger.valueOf(11);
        BigInteger N = rsa.getN(p, q);
        BigInteger E = BigInteger.valueOf(37);
        BigInteger d = rsa.getDecryptionExponent(p, q, E);

        assertEquals(13, d.intValue());

        BigInteger m = BigInteger.valueOf(2);

        BigInteger C = rsa.encrypt(m, N, E);
        assertEquals(51, C.intValue());

        BigInteger m2 = rsa.decrypt(C, N, d);

        assertEquals(m, m2);
    }
}