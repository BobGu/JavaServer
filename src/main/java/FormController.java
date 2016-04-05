import java.io.*;
import java.net.URL;
import java.io.File;

public class FormController implements Controller {
    private File resource;

    public FormController(File resourcePath) {
        this.resource= resourcePath;
    }

    public String get() throws IOException {
        String responseHeader = "HTTP/1.1 200 OK\r\n\r\n";
        String responseBody;

        if(resource.exists()) {
            InputStream fileStream = new FileInputStream(resource);
            responseBody = Parser.fileToText(fileStream);
        } else {
            responseBody = "";
        }
        return responseHeader + responseBody;
    }

    public String post(String request) throws IOException {
        String responseHeader = "HTTP/1.1 200 OK\r\n\r\n";
        String textToWrite = updateText(request);

        FileWriter writer = new FileWriter(resource, false);
        writer.write(textToWrite);
        writer.close();

        return responseHeader;
    }

    public String delete() {
        String responseHeader = "HTTP/1.1 200 OK\r\n\r\n";

        if(resource.exists()) {
            resource.delete();
        }

        return responseHeader;
    }

    private String updateText(String request) throws IOException {
        String[] requestWords = request.split(" ");
        return requestWords[requestWords.length - 1];
    }

}
