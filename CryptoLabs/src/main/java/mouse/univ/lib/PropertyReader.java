package mouse.univ.lib;

import mouse.univ.lib.FileManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertyReader {
    private final FileManager fileManager;

    public PropertyReader() {
        this.fileManager = new FileManager();
    }

    public Map<String, String> readProperties(String filepath) {
        List<String> lines = fileManager.readLines(filepath);
        Map<String, String> properties = new HashMap<>();

        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                String[] parts = line.split("=", 2);

                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    properties.put(key, value);
                }
            }
        }

        return properties;
    }
}
