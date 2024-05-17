package mouse.univ.algorithm.common;

import mouse.univ.config.Ioc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PermutationImplTest {

    private Permutation permutation;
    @BeforeEach
    void setUp() {
        permutation = Ioc.get().getBean(Permutation.class);
    }
    @Test
    void applyPermutation() {
        BitArr bitArr = BitArr.fromBinary("111000");
        bitArr.writeBits();
        int[] permutationTable = {6, 5, 4, 3, 2, 1};
        BitArr result = permutation.applyPermutation(bitArr, permutationTable);
        String bitsResult = result.writeBits();
        assertEquals("000111", bitsResult);
    }
}