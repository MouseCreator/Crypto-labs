package mouse.univ.algorithm.des;

import mouse.univ.algorithm.common.BitArr;
import mouse.univ.algorithm.common.PermutationImpl;
import mouse.univ.algorithm.common.mapper.Mapper;
import mouse.univ.core.SeededKeyGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class DESTest {
    private DES des;
    private Mapper mapper;
    private SeededKeyGenerator keyGen;
    @BeforeEach
    void setUp() {
        des = new DES(new PermutationImpl());
        mapper = new Mapper();
        keyGen = new SeededKeyGenerator(1L);
    }

    @Test
    void encryptECB() {
        BigInteger bigInteger = BigInteger.valueOf(10L);
        BitArr bitArr = mapper.fromBigInteger(bigInteger, 4);
        BigInteger keyInt = keyGen.generateKeyBits(56);
        BitArr key = mapper.fromBigInteger(keyInt, 56);
        BitArr encryptedMessage = des.encryptECB(bitArr, key);

        BitArr decrypted = des.decryptECB(encryptedMessage, key);
        BigInteger newBigInteger = mapper.toBigInteger(decrypted);
        assertEquals(bigInteger, newBigInteger);
    }

    @Test
    void encryptEBBMessage() {
        String message = "Hello, world!";
        BitArr bitArr = mapper.fromMessage(message);
        BigInteger keyInt = keyGen.generateKeyBits(56);
        BitArr key = mapper.fromBigInteger(keyInt, 56);
        BitArr encryptedMessage = des.encryptECB(bitArr, key);

        BitArr decrypted = des.decryptECB(encryptedMessage, key);
        String newMessage = mapper.toMessage(decrypted);
        assertEquals(message, newMessage);
    }

    @Test
    void encryptSimple() {
        BitArr M = BitArr.fromString("0000 0001 0010 0011 0100 0101 0110 0111 1000 1001 1010 1011 1100 1101 1110 1111");
        BitArr K = BitArr.fromString("00010011 00110100 01010111 01111001 10011011 10111100 11011111 11110001");
        BitArr encryptedMessage = des.encryptECB(M, K);
        assertNotNull(encryptedMessage);
    }
}