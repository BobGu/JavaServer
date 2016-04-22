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
        return new String(fileContent);
    }

}
