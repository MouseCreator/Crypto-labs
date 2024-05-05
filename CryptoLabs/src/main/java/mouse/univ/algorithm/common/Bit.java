package mouse.univ.algorithm.common;

public class Bit {
    private boolean value;

    public Bit(boolean value) {
        this.value = value;
    }

    public static Bit of(int value) {
        return new Bit(value != 0);
    }

    public static Bit of(boolean isOne) {
        return new Bit(isOne);
    }

    public int asInt() {
        return value ? 1 : 0;
    }
}
