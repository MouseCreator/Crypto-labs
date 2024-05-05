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
        boolean isOne = buffer.get(bit);
        return Bit.of(isOne);
    }


    public void setBit(int position, Bit currentBit) {
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
}
