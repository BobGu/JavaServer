package writers;

import java.io.File;
import java.io.IOException;

public class FileWriter implements Writer {

    public void write(String location, String textToWrite) throws IOException {
        File file = new File(location);

        java.io.FileWriter writer = new java.io.FileWriter(location, true);
        writer.write(textToWrite);
        writer.close();
    }

    public void delete(String location) {
        File file = new File(location);

        if (file.exists()) {
            file.delete();
        }
    }
}
