import java.io.*;
import java.net.URL;
import java.io.File;

public class FormController extends Controller {

    public String get() throws FileNotFoundException {
        String responseHeader = "HTTP/1.1 200 OK\r\n\r\n";
        String responseBody;
        File file = new File("../resources/main/form.txt");

        if(file.exists()) {
            InputStream fileStream = new FileInputStream(file);
            responseBody = Parser.parseInputStream(fileStream);
        } else {
            responseBody = "";
        }

        return responseHeader + responseBody;
    }

    public String post(String request) throws IOException {
        String responseHeader = "HTTP/1.1 200 OK\r\n\r\n";
        String textToWrite = updateText(request);

        File newFile = new File("../resources/main/form.txt");
        FileWriter writer = new FileWriter(newFile, false);
        writer.write(textToWrite);
        newFile.setWritable(true, false);
        writer.close();

        return responseHeader;
    }

    public String delete() {
        String responseHeader = "HTTP/1.1 200 OK\r\n\r\n";

        URL url = getClass().getResource("form.html");
        File fileToDelete = new File(url.getPath());
        fileToDelete.delete();

        //delete a file if it exists if not don't try and delete it just return a 200ok !

        return responseHeader;
    }

    private String updateText(String request) throws IOException {
        String[] requestWords = request.split(" ");
        return requestWords[requestWords.length - 1];
    }

}
