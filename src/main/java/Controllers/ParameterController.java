package Controllers;

import Requests.Request;

public class ParameterController implements Controller {

    public String get(Request request) {
        return request.getParameters();
    }

    public void post(Request request) {

    }

    public void put(Request request) {

    }

    public void delete() {

    }
}
