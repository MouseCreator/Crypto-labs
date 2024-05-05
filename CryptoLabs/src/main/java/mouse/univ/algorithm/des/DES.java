package mouse.univ.algorithm.des;

import mouse.univ.algorithm.common.BitArr;

public class DES {
    public static final int blockSizeBits = 64;
    public static final int keySizeBits = 56;
    public static final int numRounds = 16;

    private BitArr encryptBlock(BitArr block) {
        assert block.length() == 64;

        block = initialPermutation(block);
        assert block.length() == 64;

        BitArr[] leftRight = toLeftRight(block);
        assert leftRight.length == 2;

        leftRight = toRounds(leftRight);
        assert leftRight.length == 2;

        BitArr result = merge(leftRight);
        result = finalPermutation(result);

        return result;
    }

    private BitArr finalPermutation(BitArr result) {
        return null;
    }

    private BitArr merge(BitArr[] leftRight) {
        return null;
    }

    private BitArr[] toRounds(BitArr[] leftRight) {
        return null;
    }

    private BitArr[] toLeftRight(BitArr block) {
        return null;
    }

    private BitArr initialPermutation(BitArr block) {
        return null;
    }
}
