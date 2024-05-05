package mouse.univ.algorithm.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PermutationImplTest {

    private Permutation permutation;
    @BeforeEach
    void setUp() {
        permutation = new PermutationImpl();
    }
    @Test
    void applyPermutation() {
        BitArr bitArr = CBitArr.fromString("111000");
        int[] permutationTable = {5, 4, 3, 2, 1, 0};
        BitArr result = permutation.applyPermutation(bitArr, permutationTable);
        String bitsResult = result.writeBits();
        assertEquals("000111", bitsResult);
    }
}