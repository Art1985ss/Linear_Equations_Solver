package solver;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileService {

    public static String getText(String filename) {
        String text = "";
        try {
            text = Files.readString(Path.of(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    public static void setText(String filename, String text) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
