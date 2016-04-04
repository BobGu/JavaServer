import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class IndexController extends Controller {

    public String get() throws IOException {
        String responseHeader = "HTTP/1.1 200 OK\r\n\r\n";
        String responseBody;

        File file = new File("../resources/main/index.html");
        InputStream fileStream = new FileInputStream(file);
        responseBody = Parser.fileToText(fileStream);

        return responseHeader + responseBody;
    }

    public String post(String request) {
        return "";
    }

    public String delete() {
       return "";
    }
}
