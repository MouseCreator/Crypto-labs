package mouse.univ.algorithm.common.mapper;

import mouse.univ.algorithm.common.BitArr;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

@Service
public class Mapper {
    public BitArr bigIntegerToBitArr(BigInteger bigInteger, int bits) {
        byte[] byteArray = bigInteger.toByteArray();
        return BitArr.fromBytes(byteArray, bits);
    }
    public BigInteger bitArrToBigInteger(BitArr bitArr) {
        return bitArr.bigIntegerValue();
    }

    public BitArr stringToBitArr(String message) {
        byte[] byteArray = message.getBytes(StandardCharsets.UTF_8);
        return BitArr.fromBytes(byteArray, byteArray.length * 8);
    }
    public String bitArrToString(BitArr bitArr) {
        byte[] byteArray = bitArr.getBytes();
        return new String(byteArray);
    }
    public BigInteger stringToBigInteger(String message) {
        byte[] byteArray = message.getBytes();
        return new BigInteger(byteArray);
    }
    public String bigIntegerToString(BigInteger bigInteger) {
        byte[] byteArray = bigInteger.toByteArray();
        return new String(byteArray);
    }
}
