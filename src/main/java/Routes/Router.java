package Routes;

import Controllers.FormController;
import Controllers.IndexController;
import Controllers.MethodOptionsController;
import Controllers.ParameterController;
import Requests.Request;
import httpStatus.HttpStatus;
import specialCharacters.EscapeCharacters;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

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

    public String direct(Request request) throws IOException {
        String response;
        Optional<Route> route = findRoute(request.getPath());

        if (route.isPresent()) {
            response = route.get().getController().handle(request);
        } else {
            response = HttpStatus.notFound + EscapeCharacters.newline + EscapeCharacters.newline;
        }
        return response;
    }

    private void createRoutes() {
        routes.add(new Route("/", new IndexController()));
        routes.add(new Route("/form", new FormController()));
        routes.add(new Route("/method_options",  new MethodOptionsController()));
        routes.add(new Route("/parameters", new ParameterController()));
    }

    private Optional<Route> findRoute(String path) {
        return routes.stream()
                     .filter(route -> route.getPath().equals(path))
                     .findFirst();
    }
}
