package Mocks;

import Requests.Request;
import Controllers.Controller;

public class MockController implements Controller {
    private boolean postInvoked = false;

    public String get() {
        return "Hello World!";
    }

    public String post(Request request) {
        postInvoked = true;
        return "TRUE";
    }

    public void delete() {
    }

    public String put(Request request) { return "PUTTER";}

    public boolean isPostInvoked() {
        return postInvoked;
    }

    public void head() {

    }

}


