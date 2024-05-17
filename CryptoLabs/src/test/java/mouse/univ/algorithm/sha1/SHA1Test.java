package mouse.univ.algorithm.sha1;

import mouse.univ.algorithm.common.BitArr;
import mouse.univ.algorithm.common.mapper.Mapper;
import mouse.univ.config.Ioc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.*;

class SHA1Test {
    private SHA1 sha1;
    private Mapper mapper;
    @BeforeEach
    void setUp() {
        sha1 = Ioc.get().getBean(SHA1.class);
        mapper = Ioc.get().getBean(Mapper.class);
    }

    @Test
    void hash() {
        BitArr bitArr = mapper.stringToBitArr("Hello");
        BitArr hash = sha1.hash(bitArr);
        String hashStr = hash.writeHex();
        assertEquals("f7ff9e8b7bb2e09b70935a5d785e0cc5d9d0abf0", hashStr);
    }

    @Test
    void hashRandom() {
        SecureRandom secureRandom = new SecureRandom();
        SHA1Lib lib = new SHA1Lib();
        for (int i = 0; i < 10; i++) {
            byte[] bytes = new byte[10];
            secureRandom.nextBytes(bytes);
            BigInteger bigInteger = new BigInteger(bytes);
            BitArr bitArr = mapper.bigIntegerToBitArr(bigInteger, 80);
            BitArr hash = sha1.hash(bitArr);
            String result = hash.writeHex();
            String libResult = lib.hash(bigInteger);
            assertEquals(result, libResult, "Hashes not equal for input: " + bigInteger);
        }
    }

    @Test
    void hashAndCompareWithLib() {
        SHA1Lib lib = new SHA1Lib();

        String text = "Hello";
        BitArr bitArr = mapper.stringToBitArr(text);
        BitArr hash = sha1.hash(bitArr);
        String hashStr = hash.writeHex();

        String hash2 = lib.hash(text);

        assertEquals(hash2, hashStr);
    }
}