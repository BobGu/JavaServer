package readers;

import parsers.Parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReader implements Reader{

    public byte[] read(String location) throws IOException {
        File file = new File(location);
        String responseBody;

        if(file.exists()) {
            InputStream fileStream = new FileInputStream(file);
            responseBody = Parser.fileToText(fileStream);
        } else {
            responseBody = "";
        }

        return responseBody.getBytes();
    }
}
