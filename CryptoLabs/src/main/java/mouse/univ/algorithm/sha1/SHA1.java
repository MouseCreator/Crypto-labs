package mouse.univ.algorithm.sha1;

import mouse.univ.algorithm.common.BitArr;
import org.springframework.stereotype.Service;

@Service
public class SHA1 {

    private final static BitArr h0 = BitArr.fromHex("0x67452301");
    private final static BitArr h1 = BitArr.fromHex("0xEFCDAB89");
    private final static BitArr h2 = BitArr.fromHex("0x98BADCFE");
    private final static BitArr h3 = BitArr.fromHex("0x10325476");
    private final static BitArr h4 = BitArr.fromHex("0xC3D2E1F0");
    public BitArr hash(BitArr message) {
        preProcessMessage(message);
        return null;
    }

    private static void preProcessMessage(BitArr message) {
        BitArr newMessage = BitArr.mergeAll(new BitArr[]{message, BitArr.fromBinary("10000000")});
        int length = newMessage.length();
        int s = length >> 9;
        int modulo = length - (s << 9);
        int k = 512 - 64 - modulo;
        BitArr sizeArr = BitArr.fromInt(length, 64);
        BitArr fullMessage = BitArr.mergeAll(new BitArr[]{newMessage, BitArr.zeros(k), sizeArr});
        assert fullMessage.length() % 512 == 0;
    }
}
