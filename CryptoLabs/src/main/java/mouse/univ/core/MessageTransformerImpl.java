package mouse.univ.core;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
@Service
public class MessageTransformerImpl implements MessageTransformer {
    private final SecureRandom random;

    public MessageTransformerImpl() {
        this.random = new SecureRandom();
    }

    @Override
    public BigInteger generateMessageBits(int bits) {
        return new BigInteger(bits, random);
    }

    @Override
    public BigInteger generateMessageBytes(int bytes) {
        return new BigInteger(bytes * 8, random);
    }

    @Override
    public BigInteger encodeMessage(String message) {
        return new BigInteger(message.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String decodeMessage(BigInteger encoded) {
        return new String(encoded.toByteArray(), StandardCharsets.UTF_8);
    }
}
