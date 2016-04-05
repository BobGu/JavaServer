import java.io.*;
import java.net.URL;
import java.io.File;

public class FormController implements Controller {
    private String resourcePath;

    public FormController(){
       this(null);
    }

    public FormController(String resourcePath) {
        if (resourcePath == null) {
            this.resourcePath = "../resources/main/form.txt";
        } else {
            this.resourcePath = resourcePath;
        }
    }

    public String get() throws IOException {
        String responseHeader = "HTTP/1.1 200 OK\r\n\r\n";
        String responseBody;
        File file = new File(resourcePath);

        if(file.exists()) {
            InputStream fileStream = new FileInputStream(file);
            responseBody = Parser.fileToText(fileStream);
        } else {
            responseBody = "";
        }
        return responseHeader + responseBody;
    }

    public String post(String request) throws IOException {
        String responseHeader = "HTTP/1.1 200 OK\r\n\r\n";
        String textToWrite = updateText(request);
        File file = new File(resourcePath);

        FileWriter writer = new FileWriter(resourcePath, false);
        writer.write(textToWrite);
        writer.close();

        return responseHeader;
    }

    public String delete() {
        String responseHeader = "HTTP/1.1 200 OK\r\n\r\n";
        File file = new File(resourcePath);

        if(file.exists()) {
            file.delete();
        }

        return responseHeader;
    }

    private String updateText(String request) throws IOException {
        String[] requestWords = request.split(" ");
        return requestWords[requestWords.length - 1];
    }

}
