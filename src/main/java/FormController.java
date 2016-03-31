import java.io.*;
import java.net.URL;
import java.io.File;

public class FormController extends Controller {

    public String get() {
        String responseHeader = "HTTP/1.1 200 OK\r\n\r\n";

        InputStream fileStream = Server.class.getResourceAsStream("form.html");
        String responseBody = Parser.parseInputStream(fileStream);

        return responseHeader + responseBody;
    }

    public String post(String request) throws IOException {
        String responseHeader = "HTTP/1.1 200 OK\r\n\r\n";

        URL url = getClass().getResource("form.html");
        File fileToUpdate = new File(url.getPath());
        InputStream htmlInputStream = new FileInputStream(fileToUpdate);
        String updatedHtml = updateHtml(htmlInputStream, request);

        byte[] buffer = updatedHtml.getBytes();
        InputStream fileStream = new ByteArrayInputStream(updatedHtml.getBytes());
        FileOutputStream fileOutputStream = new FileOutputStream(new File(url.getPath()));

        fileOutputStream.write(buffer);
        fileOutputStream.close();

        return responseHeader;
    }

    private String updateHtml(InputStream inputStream, String request) throws IOException {
        String htmlText = Parser.parseInputStream(inputStream);

        String[] requestWords = request.split(" ");
        String requestBody = requestWords[requestWords.length - 1];
        String replacingText = "<p>" + requestBody;
        String updatedHtmlText = htmlText.replace("<p>", replacingText);

        return updatedHtmlText;
    }

}
