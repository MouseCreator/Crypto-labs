package mouse.univ.algorithm.common;

import mouse.univ.exception.EncryptException;
import org.springframework.stereotype.Service;

@Service
public class PermutationImpl implements Permutation {
    @Override
    public BitArr applyPermutation(BitArr initial, int[] table) {
        if (initial.length() != table.length) {
            throw new EncryptException(
                    "Cannot apply permutation to block of size " +
                    initial.length() +
                    " and table of size " +
                    table.length);
        }
        return applyExtendedPermutation(initial, table);
    }

    @Override
    public BitArr applyExtendedPermutation(BitArr initial, int[] table) {
        BitArr result = new BitArr(table.length);
        for (int i = 0; i < table.length; i++) {
            int bit = table[i];
            Bit currentBit = initial.bitAt(bit-1);
            result.setBit(i, currentBit);
        }

        return result;
    }
}
