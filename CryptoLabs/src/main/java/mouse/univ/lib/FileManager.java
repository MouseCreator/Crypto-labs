package mouse.univ.lib;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileManager {


    public void writeFile(String path, String data)  {
        Path filePath = Path.of(path);
        try {
            Files.writeString(filePath, data, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void appendToFile(String path, String data)  {
        Path filePath = Path.of(path);
        try {
            Files.writeString(filePath, data, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String readFile(String path) {
        Path filePath = Path.of(path);
        try {
            return Files.readString(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> readLines(String path){
        Path filePath = Path.of(path);
        try {
            return Files.readAllLines(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
