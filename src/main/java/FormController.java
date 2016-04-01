import java.io.*;
import java.net.URL;
import java.io.File;

public class FormController extends Controller {

    public String get() throws FileNotFoundException {
        String responseHeader = "HTTP/1.1 200 OK\r\n\r\n";
        String responseBody;

        InputStream fileStream = new FileInputStream("../resources/main/form.txt");
        responseBody = Parser.parseInputStream(fileStream);

        return responseHeader + responseBody;
    }

    public String post(String request) throws IOException {
        String responseHeader = "HTTP/1.1 200 OK\r\n\r\n";
        String textToWrite = updateText(request);
        URL url = getClass().getResource("form.txt");

        if (url != null) {
            File fileToOverWrite = new File(url.getPath());
            System.out.println(fileToOverWrite.canWrite());
            FileWriter writer = new FileWriter(fileToOverWrite, false);
            writer.write(textToWrite);
            writer.close();
        } else {
            File newFile = new File("../resources/main/form.txt");
            FileWriter writer = new FileWriter(newFile, false);
            writer.write(textToWrite);
            newFile.setWritable(true, false);
            writer.close();
        }

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
