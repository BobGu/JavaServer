package Controllers;

import Parsers.Parser;
import Requests.Request;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class IndexController implements Controller {

    public String get() throws IOException {
        String responseBody;

        File file = new File("../resources/main/index.html");
        InputStream fileStream = new FileInputStream(file);
        responseBody = Parser.fileToText(fileStream);

        return responseBody;
    }

    public String post(Request request) {
        return "a response";
    }

    public void delete() {
    }

    public String put(Request request) {
        return "hello";
    }

    public void head() {}
}
