package mouse.univ.algorithm.common;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class CBitArrTest {


    @Test
    void fromString() {
        String bits = "100101";
        CBitArr bitArr = CBitArr.fromString(bits);
        String result = bitArr.writeBits();
        int intValue = bitArr.intValue();
        assertEquals(37, intValue);
        assertEquals(bits, result);
    }

    @Test
    void length() {
        String bits = "1001001";
        int len = bits.length();
        CBitArr bitArr = CBitArr.fromString(bits);
        assertEquals(len, bitArr.length());
    }

    @Test
    void bitAt() {
        String bits = "10011001";
        CBitArr bitArr = CBitArr.fromString(bits);
        for (int i = 0; i < bitArr.length(); i++) {
            char ch = bits.charAt(i);
            Bit bit = bitArr.bitAt(i);
            boolean expectedValue = toBoolean(ch);
            assertEquals(expectedValue, bit.asBoolean());
        }
    }

    private boolean toBoolean(char ch) {
        if (ch == '0') {
            return false;
        }
        if (ch == '1') {
            return true;
        }
        throw new IllegalArgumentException("Cannot be turned to boolean: " + ch);
    }

    @Test
    void pushBit() {
        String bitStr = "10";
        CBitArr bitArr = CBitArr.fromString(bitStr);

        bitArr.pushBit(Bit.zero());
        bitStr += "0";
        assertEquals(bitStr, bitArr.writeBits());

        bitArr.pushBit(Bit.one());
        bitStr += "1";
        assertEquals(bitStr, bitArr.writeBits());
    }
}