package Routes;

import Controllers.Controller;
import Controllers.FormController;
import Controllers.IndexController;
import Requests.Request;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Router {

    private Map<String, Controller> routes;

    public Router() {
        this(null);
    }

    public Router(Map<String,Controller> routes) {
        if (routes == null) {
            this.routes = createRoutes();
        } else {
            this.routes = routes;
        }
    }

    private Map<String, Controller> createRoutes() {
        Map<String, Controller> routes = new HashMap<String, Controller>();

        routes.put("/", new IndexController());
        routes.put("/form", new FormController());
        return routes;
    }

    public String handle(Request request) throws IOException {
        Controller controller = routes.get(request.getPath());
        return callControllerAction(controller, request);
    }

    private String callControllerAction(Controller controller, Request request) throws IOException {
        String response =  "";

        if(request.getHttpVerb().equals("GET")) {
            response = controller.get();
        } else if(request.getHttpVerb().equals("POST")) {
            response = controller.post(request);
        } else if(request.getHttpVerb().equals("DELETE")) {
            response = controller.delete();
        } else if(request.getHttpVerb().equals("PUT")) {
            response = controller.put(request);
        }

        return response;
    }

}
