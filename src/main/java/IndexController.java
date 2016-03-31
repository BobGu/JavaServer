import java.io.InputStream;

public class IndexController extends Controller {

    public String get() {
        String responseHeader = "HTTP/1.1 200 OK\r\n\r\n";
        InputStream fileStream = Server.class.getResourceAsStream("index.html");
        String responseBody = Parser.parseInputStream(fileStream);
        return responseHeader + responseBody;
    }

    public String post(String request) {
        return "";
    }

    public String delete() {
       return "";
    }
}
