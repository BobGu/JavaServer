package readers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileReader implements Reader{

    public byte[] read(String location) throws IOException {
        File file = new File(location);

        if (file.isDirectory()) {
            String files = collectFileNames(file.list());
            files = file.getName() + " " + files;
            System.out.println(files);
            return files.getBytes();
        } else {
            return Files.readAllBytes(file.toPath());
        }
    }

    private String collectFileNames(String[] files) {
        String fileNames = "";

        for(String f : files) {
            fileNames += f + " ";
        }
        return fileNames;
    }
}
