package mouse.univ.algorithm.rsa;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
@Service
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
        pq[0] = getPrime();
        pq[1] = getPrime();
        return pq;
    }

    private BigInteger getPrime() {
        BigInteger number;
        do {
            number = BigInteger.probablePrime(2048, random);
        } while (!isPrime(number));
        return number;
    }
    private boolean isPrime(BigInteger number) {
        return number.isProbablePrime(10);
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
