package mouse.univ.algorithm.common;

import lombok.Getter;
import mouse.univ.exception.EncryptException;

import java.util.BitSet;
public class BitArr {
    private final BitSet buffer;
    @Getter
    private final int size;

    public BitArr(int size) {
        this.buffer = new BitSet(size);
        this.size = size;
    }

    public static BitArr fromHex(String s) {
        s = s.replaceAll("\\s", "");
        if (s.startsWith("0x")) {
            s = s.substring(2);
        }
        char[] chars = s.toCharArray();
        BitArr[] bitArrs = new BitArr[chars.length];
        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            bitArrs[i] = convertHex(ch);
        }
        return BitArr.mergeAll(bitArrs);
    }

    private static BitArr convertHex(char ch) {
        return switch (ch) {
            case '0' -> BitArr.fromBinary("0000");
            case '1' -> BitArr.fromBinary("0001");
            case '2' -> BitArr.fromBinary("0010");
            case '3' -> BitArr.fromBinary("0011");
            case '4' -> BitArr.fromBinary("0100");
            case '5' -> BitArr.fromBinary("0101");
            case '6' -> BitArr.fromBinary("0110");
            case '7' -> BitArr.fromBinary("0111");
            case '8' -> BitArr.fromBinary("1000");
            case '9' -> BitArr.fromBinary("1001");
            case 'A' -> BitArr.fromBinary("1010");
            case 'B' -> BitArr.fromBinary("1011");
            case 'C' -> BitArr.fromBinary("1100");
            case 'D' -> BitArr.fromBinary("1101");
            case 'E' -> BitArr.fromBinary("1110");
            case 'F' -> BitArr.fromBinary("1111");
            default -> throw new IllegalArgumentException("Hex contains illegal character: " + ch);
        };
    }

    public static BitArr zeros(int k) {
        return new BitArr(k);
    }

    public byte[] getBytes() {
        return buffer.toByteArray();
    }

    public BitArr(int size, BitSet buffer) {
        this.buffer = buffer;
        this.size = size;

    }

    public static BitArr fromInt(int intValue, int bufferSize) {
        BitArr bitArr = new BitArr(bufferSize);
        int s = bufferSize - 1;
        int maxShift = 32;
        for (int i = 0; i < maxShift; i++) {
            if ((intValue & (1 << i)) != 0) {
                bitArr.setBit(s - i, Bit.one());
            }
        }
        return bitArr;
    }
    public static BitArr mergeAll(BitArr[] orderedArr) {
        int expectedSize = 0;
        for (BitArr bitArr : orderedArr) {
            expectedSize += bitArr.length();
        }

        BitArr result = new BitArr(expectedSize);
        int k = 0;
        for (BitArr currentArr : orderedArr) {
            for (int j = 0; j < currentArr.length(); j++) {
                result.setBit(k, currentArr.bitAt(j));
                k++;
            }
        }
        return result;
    }

    public static BitArr fromBytes(byte[] byteArray, int bitsCount) {
        BitArr bitArr = new BitArr(bitsCount);
        readBytes(byteArray, bitArr, bitsCount);
        return bitArr;
    }

    private static void readBytes(byte[] byteArray, BitArr bitArr, int bitsCount) {
        int k = 0;
        for (byte currentByte : byteArray) {
            for (int j = 0; j < 8; j++) {
                if ((currentByte & (1 << j)) != 0) {
                    bitArr.setBit(k, Bit.one());
                }
                k++;
                if (k >= bitsCount) {
                    return;
                }
            }
        }
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
        int v = 0;
        int current = 1;
        for (int i = size - 1; i >= 0; i--) {
           if (bitAt(i).asBoolean()) {
                v += current;
           }
           current = current << 1;
        }

        return v;
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

    public static BitArr fromBinary(String bits) {
        String replaced = bits.replaceAll("[^01]", "");
        BitArr bitArr = new BitArr(replaced.length());
        int i = -1;
        for (char ch : replaced.toCharArray()) {
            i++;
            if (ch == '0') {
                bitArr.setBit(i, Bit.of(0));
            }
            else if (ch == '1') {
                bitArr.setBit(i, Bit.of(1));
            }
            else {
                throw new EncryptException("Unexpected char: " + ch);
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
        int sizeLast = size - notLast * sizeNotLast;

        BitArr[] bitSets = new BitArr[divisions];
        int k = 0;
        for (int i = 0; i < divisions; i++) {
            bitSets[i] = new BitArr(sizeNotLast);
            int sizeLimit = (i == divisions - 1) ? sizeNotLast : sizeLast;
            for (int j = 0; j < sizeLimit; j++) {
                bitSets[i].setBit(j, bitAt(k));
                k++;
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
            result.setBit(i, this.bitAt(current));
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
        bitSets[lastIndex] = new BitArr(blockSize);
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
