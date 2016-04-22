package readers;

import java.util.Base64;
import java.util.Base64.Encoder;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ImageReader implements Reader {

    public String read(String location) throws IOException {
        File file = new File(location);
        byte[] fileContent = Files.readAllBytes(file.toPath());
        Encoder encoder = Base64.getEncoder();
        byte[] encodedFileContent = encoder.encode(fileContent);
        return new String(encodedFileContent);
    }

}
