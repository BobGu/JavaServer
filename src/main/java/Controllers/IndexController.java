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

    public void post(Request request) {
    }

    public void delete() {
    }

    public void put(Request request) {
    }
}
