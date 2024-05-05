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
        BitArr bitArr = mapper.fromMessage("Hello");
        sha1.hash(bitArr);
    }
}