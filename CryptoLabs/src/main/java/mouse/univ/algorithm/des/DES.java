package mouse.univ.algorithm.des;

import mouse.univ.algorithm.common.Bit;
import mouse.univ.algorithm.common.BitArr;
import mouse.univ.algorithm.common.Permutation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DES {
    public static final int BLOCK_SIZE_BITS = 64;
    public static final int KEY_SIZE_BITS = 56;
    public static final int NUM_ROUNDS = 16;

    private final Permutation permutation;
    @Autowired
    public DES(Permutation permutation) {
        this.permutation = permutation;
    }
    public BitArr encryptECB(BitArr message, BitArr key) {
        BitArr extendedMessage = BitArr.mergeAll(new BitArr[]{message, BitArr.fromString("10000000")});
        BitArr[] result = DESBlocks(key, extendedMessage);
        return BitArr.mergeAll(result);
    }

    private BitArr[] DESBlocks(BitArr key, BitArr extendedMessage) {
        BitArr[] blocks = extendedMessage.splitToSizedBlocks(64);
        BitArr[] result = new BitArr[blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            BitArr block = blocks[i];
            BitArr encrypted = encryptBlock(block, key);
            result[i] = encrypted;
        }
        return result;
    }

    public BitArr decryptECB(BitArr cipher, BitArr key) {
        BitArr[] result = DESBlocks(key, cipher);
        BitArr merged = BitArr.mergeAll(result);
        int currentBit = merged.length() - 1;
        while (!merged.bitAt(currentBit).asBoolean()) {
            currentBit--;
        }
        return merged.subs(0, currentBit);
    }
    private BitArr encryptBlock(BitArr block, BitArr key) {
        assert block.length() == BLOCK_SIZE_BITS;
        assert key.length() == KEY_SIZE_BITS;

        block = initialPermutation(block);
        assert block.length() == 64;

        BitArr result = doRounds(block, key);
        result = finalPermutation(result);

        return result;
    }

    private BitArr doRounds(BitArr block, BitArr key) {
        BitArr[] initialLeftRight = generateInitialKey(key);
        for (int i = 0; i < NUM_ROUNDS; i++) {
            BitArr currentKey = shiftKey(initialLeftRight, i);
            block = roundFunction(block, currentKey);
        }
        return block;
    }

    private BitArr initialPermutation(BitArr block) {
        return permutation.applyPermutation(block, DESTables.INITIAL_PERMUTATION);
    }
    private BitArr finalPermutation(BitArr result) {
        return permutation.applyExtendedPermutation(result, DESTables.FINAL_PERMUTATION);
    }

    private BitArr roundFunction(BitArr bitArr, BitArr key) {
        BitArr[] leftRight = bitArr.split(2);
        assert leftRight.length == 2;

        BitArr left = leftRight[0];
        BitArr right = leftRight[1];
        assert left.length() == 32;
        assert right.length() == 32;

        BitArr rightExtended = permutation.applyExtendedPermutation(right, DESTables.EXPANSION_PERMUTATION);
        assert rightExtended.length() == 48;
        assert key.length() == 48;

        BitArr rightXOR = rightExtended.xor(key);
        BitArr[] rightSplit = rightXOR.split(8);
        BitArr[] substituted = new BitArr[8];
        for (int i = 0; i < rightSplit.length; i++) {
            substituted[i] = substitute(rightSplit[i], i);
        }
        BitArr merged = BitArr.mergeAll(substituted);
        assert merged.length() == 32;

        return permutation.applyPermutation(merged, DESTables.FP_PERMUTATION);
    }

    private BitArr substitute(BitArr rightSplit, int index) {
        BitArr outBits = rightSplit.getBits(0, 5);
        BitArr innerBits = rightSplit.getRange(1, 4);
        int row = outBits.intValue();
        int column = innerBits.intValue();
        int newValue = DESTables.S_BOXES[index][row][column];
        return BitArr.fromInt(newValue, 4);
    }
    private BitArr[] generateInitialKey(BitArr initialKey) {
        BitArr key64 = addParityBits(initialKey);
        BitArr pcKey = permutedChoiceKey(key64);
        BitArr[] leftRightKey = pcKey.split(2);
        assert leftRightKey[0].length() == 28;
        assert leftRightKey[1].length() == 28;
        return leftRightKey;
    }
    private BitArr shiftKey(BitArr[] leftRightKey, int iteration) {
        int[] p = {1, 1, 2,	2,	2,	2,	2,	2,	1,	2,	2,	2,	2,	2,	2,	1 };
        BitArr left = leftRightKey[0];
        BitArr right = leftRightKey[1];
        left = left.cycledShiftLeft(p[iteration]);
        right = right.cycledShiftLeft(p[iteration]);
        BitArr[] leftRightNew = {left, right};
        return getCurrentKey(leftRightKey);
    }

    private BitArr getCurrentKey(BitArr[] leftRightKey)  {
        BitArr merged = BitArr.mergeAll(leftRightKey);
        return permutation.applyPermutation(merged, DESTables.PC2_KEY);
    }

    private BitArr addParityBits(BitArr initialKey) {
        int k = 0;
        int m = 0;
        BitArr extendedKey = new BitArr(64);
        for (int i = 0; i < 8; i++) {
            boolean toBeZero = false;
            for (int j = 0; j < 7; j++) {
                Bit bit = initialKey.bitAt(k);
                if (bit.asBoolean()) {
                    toBeZero = !toBeZero;
                }
                extendedKey.setBit(m, bit);
                k++;
                m++;
            }
            extendedKey.setBit(m, Bit.of(toBeZero));
            m++;
        }
        return extendedKey;
    }

    private BitArr permutedChoiceKey(BitArr initialKey) {
        assert initialKey.length() == 64;
        BitArr newKey = permutation.applyExtendedPermutation(initialKey, DESTables.PC_KEY);
        assert newKey.length() == 56;
        return newKey;
    }

}
