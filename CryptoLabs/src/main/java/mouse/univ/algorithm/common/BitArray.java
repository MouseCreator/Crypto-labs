package mouse.univ.algorithm.common;

import java.math.BigInteger;

public interface BitArray extends Cloneable {
    byte[] getBytes();
    String writeBits();
    String writeHex();
    int intValue();
    BigInteger bigIntegerValue();
    String stringValue();
    BitArray xor(BitArray other);
    int length();
    Bit bitAt(int bit);
    void setBit(int position, Bit currentBit);
    BitArray reverse();
    BitArray[] split(int divisions);
    BitArray getBits(int... bits);
    BitArray getRange(int fromInclusive, int toInclusive);
    BitArray cycledShiftLeft(int steps);
    BitArray[] splitToSizedBlocks(int blockSize);
    BitArray subs(int fromInclusive, int toExclusive);
    BitArray and(BitArray other);
    BitArray or(BitArray other);
    BitArray not() ;
    BitArray add(BitArray other);



}
