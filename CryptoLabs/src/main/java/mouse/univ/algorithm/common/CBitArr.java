package mouse.univ.algorithm.common;

import java.math.BigInteger;

public class CBitArr implements BitArr {
    private BigInteger bigInteger;

    public CBitArr(BigInteger bigInteger) {
        this.bigInteger = bigInteger;
    }
    public CBitArr() {
        this.bigInteger = BigInteger.valueOf(0);
    }

    protected static CBitArr fromString(String bits) {
        CBitArr bitArr = new CBitArr();
        for (char ch : bits.toCharArray()) {
            if (ch == '0') {
                bitArr.pushBit(Bit.of(0));
            }
            else if (ch == '1') {
                bitArr.pushBit(Bit.of(1));
            }
        }
        return bitArr;
    }
    @Override
    public int length() {
        return bigInteger.bitCount();
    }

    @Override
    public Bit bitAt(int bit) {
        boolean isOne = bigInteger.testBit(bit);
        return Bit.of(isOne);
    }

    @Override
    public void pushBit(Bit currentBit) {
        BigInteger pushed = BigInteger.valueOf(currentBit.asInt());
        bigInteger = bigInteger.shiftLeft(1)
                .add(pushed);
    }
}
