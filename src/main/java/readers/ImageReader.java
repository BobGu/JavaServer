package readers;

import java.util.Base64;
import java.util.Base64.Encoder;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ImageReader implements Reader {

    public byte[] read(String location) throws IOException {
        File file = new File(location);
        return Files.readAllBytes(file.toPath());
    }

}
