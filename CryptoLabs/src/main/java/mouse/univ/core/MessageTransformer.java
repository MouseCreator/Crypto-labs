package mouse.univ.core;

import java.math.BigInteger;

public interface MessageTransformer {
    BigInteger generateMessageBits(int bits);
    BigInteger generateMessageBytes(int bytes);
    BigInteger encodeMessage(String message);
    String decodeMessage(BigInteger encoded);
}
