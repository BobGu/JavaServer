package Controllers;

import Requests.Request;

public class ParameterController implements Controller {

    public String get(Request request) {
        System.out.println(request.getParameters());
        return request.getParameters();
    }

    public void post(Request request) {

    }

    public void put(Request request) {

    }

    public void delete() {

    }
}
