package Routes;

import Controllers.Controller;
import Controllers.FormController;
import Controllers.IndexController;
import Requests.Request;

import java.io.IOException;
import java.util.ArrayList;

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

    public boolean routeExists(String path, String httpVerb) {
        return routes.stream()
                     .anyMatch(route -> route.toString().equals(httpVerb+ " " + path));
    }

    private void createRoutes() {
        routes.add(new Route("/", "GET", new IndexController()));
        routes.add(new Route("/form", "GET", new FormController()));
    }

    private Route findRoute(String path) {
       return routes.stream()
                    .filter(route -> route.getPath().equals(path))
                    .findFirst()
                    .get();
    }

}
