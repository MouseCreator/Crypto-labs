package mouse.univ.algorithm.common;

public interface BitArr {
    int length();
    Bit bitAt(int bit);
    void pushBit(Bit currentBit);
    String writeBits();
    int intValue();
}
