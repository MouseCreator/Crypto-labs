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
    private final static BitArr k1 = BitArr.fromHex("0x5A827999");
    private final static BitArr k2 = BitArr.fromHex("0x6ED9EBA1");
    private final static BitArr k3 = BitArr.fromHex("0x8F1BBCDC");
    private final static BitArr k4 = BitArr.fromHex("0xCA62C1D6");
    public BitArr hash(BitArr message) {
        BitArr bitArr = preProcessMessage(message);
        BitArr[] chunks = bitArr.splitToSizedBlocks(512);
        return processChunks(chunks);
    }

    private BitArr processChunks(BitArr[] chunks) {
        BitArr[] resultSet = new BitArr[]{ h0.clone(), h1.clone(), h2.clone(), h3.clone(), h4.clone() };
        for (BitArr chunk : chunks) {
            processChunk(chunk, resultSet);
        }
        return BitArr.mergeAll(resultSet);
    }

    private void processChunk(BitArr chunk, BitArr[] resultSet) {
        BitArr[] split = chunk.split(16);
        BitArr[] w = new BitArr[80];
        System.arraycopy(split, 0, w, 0, split.length);
        for (int i = 16; i < 80; i++) {
            w[i] = (w[i-3].xor(w[i-8]).xor(w[i-14]).xor(w[i-16])).cycledShiftLeft(1);
        }
        BitArr a = h0, b = h1, c = h2, d = h3, e = h4;
        BitArr f, k;
        for (int i = 0; i < 80; i++) {
            if (i == 18) {
                System.out.println(19);
            }
            if (i < 20) {
                f = (b.and(c)).or(b.not().and(d));
                k = k1;
            } else if (i < 40) {
                f = b.xor(c).xor(d);
                k = k2;
            } else if (i < 60) {
                f = (b.and(c)).or(b.and(d)).or(c.and(d));
                k = k3;
            } else {
                f = b.xor(c).xor(d);
                k = k4;
            }
            BitArr ac = a.cycledShiftLeft(5);
            BitArr t = ac.add(f).add(e).add(k).add(w[i]);
            e = d;
            d = c;
            c = b.cycledShiftLeft(30);
            b = a;
            a = t;

        }
        resultSet[0].add(a);
        resultSet[1].add(b);
        resultSet[2].add(c);
        resultSet[3].add(d);
        resultSet[4].add(e);
    }

    private static BitArr preProcessMessage(BitArr message) {
        BitArr newMessage = BitArr.mergeAll(new BitArr[]{message, BitArr.fromBinary("10000000")});
        int length = message.length();
        int s = length >> 9;
        int modulo = length - (s << 9);
        int k = 512 - 64 - modulo;
        BitArr sizeArr = BitArr.fromInt(length, 64);
        BitArr fullMessage = BitArr.mergeAll(new BitArr[]{newMessage, BitArr.zeros(k), sizeArr});
        assert fullMessage.length() % 512 == 0;
        return fullMessage;
    }
}
