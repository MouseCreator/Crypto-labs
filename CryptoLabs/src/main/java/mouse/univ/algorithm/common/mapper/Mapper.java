package mouse.univ.algorithm.common.mapper;

import mouse.univ.algorithm.common.BitArr;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

@Service
public class Mapper {
    public BitArr fromBigInteger(BigInteger bigInteger, int bits) {
        byte[] byteArray = bigInteger.toByteArray();
        return BitArr.fromBytes(byteArray, bits);
    }
    public BigInteger toBigInteger(BitArr bitArr) {
        byte[] byteArray = bitArr.getBytes();
        return new BigInteger(byteArray);
    }

    public BitArr fromMessage(String message) {
        byte[] byteArray = message.getBytes(StandardCharsets.UTF_8);
        return BitArr.fromBytes(byteArray, byteArray.length * 8);
    }
    public String toMessage(BitArr bitArr) {
        byte[] byteArray = bitArr.getBytes();
        return new String(byteArray);
    }
}
