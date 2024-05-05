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
        BitArr result = new CBitArr();
        for (int bit : table) {
            Bit currentBit = initial.bitAt(bit);
            result.pushBit(currentBit);
        }
        return result;
    }
}
