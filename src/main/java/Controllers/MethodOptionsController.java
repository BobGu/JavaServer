package Controllers;

import Requests.Request;

public class MethodOptionsController implements Controller{

    public String get() {
        return "";
    }

    public String post(Request request) {
        return "a response";
    }

    public String put(Request request) {
        return "put";
    }

    public void delete() {}

    public void head() {

    }
}
