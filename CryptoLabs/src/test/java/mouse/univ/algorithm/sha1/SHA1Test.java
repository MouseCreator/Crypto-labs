package mouse.univ.algorithm.sha1;

import mouse.univ.algorithm.common.BitArr;
import mouse.univ.algorithm.common.mapper.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SHA1Test {
    private SHA1 sha1;
    private Mapper mapper;
    @BeforeEach
    void setUp() {
        sha1 = new SHA1();
        mapper = new Mapper();
    }

    @Test
    void hash() {
        BitArr bitArr = mapper.stringToBitArr("Hello");
        BitArr hash = sha1.hash(bitArr);
        String hashStr = hash.writeHex();
        assertEquals("f7ff9e8b7bb2e09b70935a5d785e0cc5d9d0abf0", hashStr);
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