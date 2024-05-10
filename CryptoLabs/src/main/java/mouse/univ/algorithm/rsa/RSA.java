package mouse.univ.algorithm.rsa;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA {

    private final SecureRandom random;

    public RSA() {
        this.random = new SecureRandom();
    }

    public BigInteger encrypt(BigInteger message, BigInteger N, BigInteger E) {
        return message.modPow(E, N);
    }
    public BigInteger decrypt(BigInteger cipher, BigInteger N, BigInteger d) {
        return cipher.modPow(d, N);
    }
    public BigInteger[] generatePQ() {
        BigInteger[] pq = new BigInteger[2];
        pq[0] = getProbablePrime();
        pq[1] = getProbablePrime();
        return pq;
    }

    private BigInteger getProbablePrime() {
        return BigInteger.probablePrime(2048, random);
    }
    public BigInteger getEncryptionExponent(BigInteger p, BigInteger q) {
        return BigInteger.valueOf(65537L);
    }
    public BigInteger getDecryptionExponent(BigInteger p, BigInteger q, BigInteger E) {
        BigInteger modulo = minusOne(p).multiply(minusOne(q));
        return E.modInverse(modulo);
    }

    private static BigInteger minusOne(BigInteger p) {
        return p.subtract(BigInteger.ONE);
    }

    public BigInteger getN(BigInteger p, BigInteger q) {
        return p.multiply(q);
    }
}
