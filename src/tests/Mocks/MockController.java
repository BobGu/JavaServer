package Mocks;

import Requests.Request;
import Controllers.Controller;

public class MockController implements Controller {
    private boolean postInvoked = false;
    private boolean handleInvoked = false;

    public String get(Request request) {
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

    public boolean isHandleInvoked() {
        return handleInvoked;
    }

    public void head() {

    }

    public String handle(Request request) {
        handleInvoked = true;
        return "handling the request";
    }

}


