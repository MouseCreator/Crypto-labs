package mouse.univ.algorithm.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BitArrTest {

    @Test
    void xor() {
        BitArr b1 = BitArr.fromBinary("1001011");
        BitArr b2 = BitArr.fromBinary("0101101");
        BitArr xor = b1.xor(b2);
        BitArr xor2 = b1.xor(b2);
        assertEquals("1100110", xor.writeBits());
        assertEquals(xor, xor2);
    }

    @Test
    void or() {
        BitArr b1 = BitArr.fromBinary("100100");
        BitArr b2 = BitArr.fromBinary("010101");
        BitArr or = b1.or(b2);
        BitArr or2 = b2.or(b1);
        assertEquals("110101", or.writeBits());
        assertEquals(or, or2);
    }

    @Test
    void and() {
        BitArr b1 = BitArr.fromBinary("10101");
        BitArr b2 = BitArr.fromBinary("11001");
        BitArr and = b1.and(b2);
        BitArr and2 = b2.and(b1);
        assertEquals("10001", and.writeBits());
        assertEquals(and, and2);
    }
    @Test
    void shift() {
        String s = "1001011001001";
        BitArr b1 = BitArr.fromBinary(s);
        String prev = s;
        for (int i = 0; i < 10; i++) {
            String right = i > 0 ? s.substring(i) : s;
            String left = i > 0 ? s.substring(0, i) : "";
            s = right + left;
            b1 = b1.cycledShiftLeft(i);
            String bits = b1.writeBits();
            assertEquals(s, bits, "Incorrect shift by " + i + " positions. Prev: " + prev + ". Curr: " + bits);
            prev = s;
        }
    }
    @Test
    void intValueTest() {
        BitArr bitArr = BitArr.fromInt(13, 4);
        int i = bitArr.intValue();
        assertEquals(13, i);
    }

    @Test
    void cloneTest() {
        String str = "100101";
        BitArr bitArr = BitArr.fromBinary(str);
        BitArr cloned = bitArr.clone();
        assertEquals(str, cloned.writeBits());
    }
    @Test
    void addTest() {
        String str1 = "101111";
        String str2 = "111000";
        BitArr num1 = BitArr.fromBinary(str1);
        BitArr num2 = BitArr.fromBinary(str2);
        assertEquals(47, num1.intValue());
        assertEquals(56, num2.intValue());
        BitArr added = num1.add(num2);
        assertEquals("100111", added.writeBits());
    }

    @Test
    void addTestHex() {
        BitArr bitArr1 = BitArr.fromHex("0x1A3");
        BitArr bitArr2 = BitArr.fromHex("0x2C5");
        BitArr result = bitArr1.add(bitArr2);
        BitArr expected = BitArr.fromHex("0x468");
        assertEquals(expected, result);
    }
    @Test
    void testHex() {
        String text = "123a";
        BitArr fromHex = BitArr.fromHex(text);
        String s = fromHex.writeHex();
        assertEquals(text, s);
    }
}