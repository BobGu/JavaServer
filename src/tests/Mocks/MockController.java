package Mocks;

import Requests.Request;
import Controllers.Controller;

public class MockController implements Controller {

    public String get() {
        return "HTTP/1.1 200 OK\r\n\r\n";
    }

    public String post(Request request) {
        return "HTTP/1.1 200 OK\r\n\r\n";
    }

    public String delete() {
        return "hello";
    }

    public String put(Request request) { return "yo";}

}


