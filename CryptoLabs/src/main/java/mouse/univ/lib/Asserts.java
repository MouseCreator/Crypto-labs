package mouse.univ.lib;

public class Asserts {
    public static void assertThat(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
    public static void assertThat(boolean condition) {
        assert condition;
    }
}
