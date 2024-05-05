package mouse.univ.algorithm.des;

import mouse.univ.lib.PropertyReader;
import org.junit.jupiter.api.Test;

import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
public class HelperTest {
    @Test
    void testKeys() {
        PropertyReader propertyReader = new PropertyReader();
        Map<String, String> expected = propertyReader.readProperties("src/test/resources/Expected.txt");
        Map<String, String> actual = propertyReader.readProperties("src/test/resources/Keys.txt");

        for (String k : expected.keySet()) {
            String s = actual.get(k);
            assertNotNull(s);
            String st = s.replaceAll("\\s", "");
            String kt = expected.get(k).replaceAll("\\s", "");
            assertEquals(st, kt);
        }
    }
}
