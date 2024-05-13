package mouse.univ.algorithm.elgamal;

import java.math.BigInteger;
import java.security.SecureRandom;

public class ElGamal {
    private final SecureRandom secureRandom;

    public ElGamal(SecureRandom secureRandom) {
        this.secureRandom = secureRandom;
    }
    public BigInteger generateP() {
        return BigInteger.probablePrime(2048, secureRandom);
    }
    public BigInteger getGenerator(BigInteger p) {
        return null;
    }

    public BigInteger getX(BigInteger p) {
        return null;
    }
    public BigInteger getPublicKey(BigInteger g, BigInteger p, BigInteger x) {
        return g.modPow(x, p);
    }
}
