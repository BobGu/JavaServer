package writers;
import parsers.Parser;
import texts.KeyValueUpdater;
import texts.TextUpdater;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileWriter implements Writer {
    private TextUpdater textUpdater;

    public FileWriter() {
        this(null);
    }

    public FileWriter(TextUpdater updater) {
        if(updater == null) {
            this.textUpdater = new KeyValueUpdater();
        } else {
            this.textUpdater = updater;
        }
    }

    public void write(String location, String textToWrite) throws IOException {
        File file = new File(location);

        java.io.FileWriter writer = new java.io.FileWriter(location, true);
        writer.write(textToWrite);
        writer.close();
    }

    public void update(String location, String textToWrite) throws IOException {
        String updatedText = "";
        File file = new File(location);

        if (file.exists()) {
            InputStream fileStream = new FileInputStream(file);
            String fileText = fileToText(fileStream);
            updatedText = textUpdater.update(fileText, textToWrite);
        }

        java.io.FileWriter writer = new java.io.FileWriter(location, false);
        writer.write(updatedText);
        writer.close();
    }

    public void delete(String location) {
        File file = new File(location);

        if (file.exists()) {
            file.delete();
        }
    }

    private String fileToText(InputStream inputStream) throws IOException {
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
