package Mocks;

import Requests.Request;
import Controllers.Controller;

public class MockController implements Controller {
    private boolean postInvoked = false;

    public String get() {
        return "Hello World!";
    }

    public void post(Request request) {
        postInvoked = true;
    }

    public void delete() {
    }

    public void put(Request request) { }

    public boolean isPostInvoked() {
        return postInvoked;
    }

}


