package mouse.univ.algorithm.common;

import lombok.Getter;
import mouse.univ.exception.EncryptException;

import java.nio.ByteBuffer;
import java.util.BitSet;
public class BitArr {
    private final BitSet buffer;
    @Getter
    private final int size;

    public BitArr(int size) {
        this.buffer = new BitSet(size);
        this.size = size;
    }

    public BitArr(int size, BitSet buffer) {
        this.buffer = buffer;
        this.size = size;

    }

    public static BitArr fromInt(int intValue, int bufferSize) {
        BitArr bitArr = new BitArr(bufferSize);
        for (int i = 0; i < bufferSize; i++) {
            if ((intValue & (1 << i)) != 0) {
                bitArr.setBit(i, Bit.one());
            }
        }
        return bitArr;
    }

    public static BitArr mergeAll(BitArr[] orderedArr) {
        int expectedSize = 0;
        for (int i = 0; i < orderedArr.length; i++) {
            expectedSize += orderedArr.length;
        }

        BitArr result = new BitArr(expectedSize);
        int k = 0;
        for (BitArr currentArr : orderedArr) {
            for (int j = 0; j < currentArr.length(); j++) {
                result.setBit(k, currentArr.bitAt(k));
                k++;
            }
        }
        return result;
    }

    public String writeBits() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            boolean b = buffer.get(i);
            builder.append(b ? "1" : "0");
        }
        return builder.toString();
    }


    public int intValue() {
        ByteBuffer wrapped = ByteBuffer.wrap(buffer.toByteArray());
        return wrapped.getInt();
    }

    public void clear() {
        buffer.clear();
    }

    public BitArr xor(BitArr other) {
        if (this.length() != other.length()) {
            throw new EncryptException("Cannot apply XOR operator to bit arrays of different size: "
                    + this.length() + " and " + other.length());
        }
        BitSet resultSet = (BitSet) buffer.clone();
        resultSet.xor(other.buffer);
        return new BitArr(size, resultSet);
    }

    public static BitArr fromString(String bits) {
        BitArr bitArr = new BitArr(bits.length());
        int i = -1;
        for (char ch : bits.toCharArray()) {
            i++;
            if (ch == '0') {
                bitArr.setBit(i, Bit.of(0));
            }
            else if (ch == '1') {
                bitArr.setBit(i, Bit.of(1));
            }
        }
        return bitArr;
    }

    public int length() {
        return size;
    }


    public Bit bitAt(int bit) {
        validateSize(bit);
        boolean isOne = buffer.get(bit);
        return Bit.of(isOne);
    }

    private void validateSize(int index) {
        if (index < 0 || index >= size) {
            throw new EncryptException("Invalid index " + index + " for bit array of size " + size);
        }
    }
    public void setBit(int position, Bit currentBit) {
        validateSize(position);
        if (currentBit.asBoolean()) {
            buffer.set(position);
        } else {
            buffer.clear(position);
        }
    }

    @Override
    public String toString() {
        return "BitArr{" + writeBits() + "}";
    }

    public BitArr[] split(int divisions) {
        int notLast = divisions - 1;
        int sizeNotLast = (size + notLast) / divisions;
        int sizeLast = size - notLast * divisions;

        BitArr[] bitSets = new BitArr[divisions];
        int k = 0;
        for (int i = 0; i < divisions; i++) {
            bitSets[i] = new BitArr(sizeNotLast);
            int sizeLimit = (i == divisions - 1) ? sizeNotLast : sizeLast;
            for (int j = 0; j < sizeLimit; j++) {
                k++;
                bitSets[i].setBit(j, bitAt(k));
            }
        }
        return bitSets;
    }

    public BitArr getBits(int... bits) {
        BitArr bitArr = new BitArr(bits.length);
        for (int i = 0; i < bits.length; i++) {
            bitArr.setBit(i, bitAt(bits[i]));
        }
        return bitArr;
    }
    public BitArr getRange(int fromInclusive, int toInclusive) {
        if (toInclusive < fromInclusive) {
            throw new EncryptException("Cannot get subset of bits: [" + fromInclusive + ", " + toInclusive + ")");
        }
        BitSet bitSet = buffer.get(fromInclusive, toInclusive+1);
        return new BitArr(toInclusive + 1 - fromInclusive, bitSet);
    }

    public BitArr cycledShiftLeft(int steps) {
        BitArr result = new BitArr(size);
        int current = steps;
        for (int i = 0; i < size; i++) {
            result.setBit(i, result.bitAt(current));
            current++;
            if (current == size) {
                current = 0;
            }
        }
        return result;
    }

    public BitArr[] splitToSizedBlocks(int blockSize) {
        int divisions = (size + blockSize - 1) / blockSize;
        BitArr[] bitSets = new BitArr[divisions];
        int k = 0;
        int lastIndex = divisions - 1;
        for (int i = 0; i < lastIndex; i++) {
            bitSets[i] = new BitArr(blockSize);
            for (int j = 0; j < blockSize; j++) {
                bitSets[i].setBit(j, bitAt(k));
                k++;
            }
        }
        for (int j = 0; j < blockSize; j++) {
            bitSets[lastIndex].setBit(j, k < size ? bitAt(k) : Bit.zero());
            k++;
        }

        return bitSets;
    }

    public BitArr subs(int fromInclusive, int toExclusive) {
        BitSet bitSet = buffer.get(fromInclusive, toExclusive);
        return new BitArr(toExclusive - fromInclusive, bitSet);
    }
}
