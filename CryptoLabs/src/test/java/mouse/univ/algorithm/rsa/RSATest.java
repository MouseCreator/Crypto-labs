package mouse.univ.algorithm.rsa;

import mouse.univ.algorithm.common.mapper.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class RSATest {
    private RSA rsa;
    private Mapper mapper;
    @BeforeEach
    void setUp() {
        rsa = new RSA();
        mapper = new Mapper();
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

    @Test
    void testRandomNumbers() {
        BigInteger[] pq = rsa.generatePQ();
        BigInteger p = pq[0];
        BigInteger q = pq[1];
        BigInteger N = rsa.getN(p, q);
        BigInteger E = rsa.getEncryptionExponent(p, q);
        BigInteger d = rsa.getDecryptionExponent(p, q, E);

        BigInteger m = BigInteger.valueOf(2000);
        BigInteger C = rsa.encrypt(m, N, E);
        BigInteger m2 = rsa.decrypt(C, N, d);

        assertEquals(m, m2);
    }

    @Test
    void testTextMessage() {
        BigInteger[] pq = rsa.generatePQ();
        BigInteger p = pq[0];
        BigInteger q = pq[1];
        BigInteger N = rsa.getN(p, q);
        BigInteger E = rsa.getEncryptionExponent(p, q);
        BigInteger d = rsa.getDecryptionExponent(p, q, E);

        String message = "Hello, world!";
        BigInteger m = mapper.stringToBigInteger(message);
        BigInteger C = rsa.encrypt(m, N, E);
        BigInteger m2 = rsa.decrypt(C, N, d);

        String message2 = mapper.bigIntegerToString(m2);

        assertEquals(message, message2);
    }
}