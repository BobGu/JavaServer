package Routes;

import Controllers.Controller;
import Controllers.FormController;
import Controllers.IndexController;
import Requests.Request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Router {
    private ArrayList<Route> routes = new ArrayList<Route>();

    public Router() {
        this(null);
    }

    public Router(ArrayList<Route> routes) {
        if (routes == null) {
            createRoutes();
        } else {
            this.routes = routes;
        }
    }

    private void createRoutes() {
        routes.add(new Route("/", new String[] {"GET"}, new IndexController()));
        routes.add(new Route("/form", new String[] {"GET", "POST", "PUT", "DELETE", "OPTIONS"}, new FormController()));
    }

    public String handle(Request request) throws IOException {
        Route route = findRoute(request.getPath());
        return callControllerAction(route.getController(), request);
    }

    private Route findRoute(String path) {
       return routes.stream()
                    .filter(route -> route.getPath().equals(path))
                    .findFirst()
                    .get();
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
