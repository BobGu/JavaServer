package resourceCRUD;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class DirectoryCRUD implements ResourceCRUD{

    public void create(String location, String text) {
    }

    public String read(String directoryName) throws IOException {
        if (!directoryName.equals("public")) {
            File file =  new File("../resources/" + directoryName);
            String directory = file.getName();
            return directory;
        } else {
            File file = new File("../resources/public");
            return file.getName();
        }
    }

    public void update(String location, String text) {

    }

    public void delete(String location) {

    }

    private String collectFileNames(String[] files) {
        String fileNames = "";

        for(String f : files) {
            fileNames += f;
        }
        return fileNames;
    }

}
