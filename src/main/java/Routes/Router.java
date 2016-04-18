package routes;

import controllers.*;
import readers.FileReader;
import requests.Request;
import httpStatus.HttpStatus;
import specialCharacters.EscapeCharacters;
import writers.FileWriter;

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
        System.out.println(request.getPath());
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
        routes.add(new Route("/form", new FormController(new FileWriter(), new FileReader())));
        routes.add(new Route("/method_options",  new MethodOptionsController()));
        routes.add(new Route("/parameters", new ParameterController()));
        routes.add(new Route("/logs", new LogsController()));
        routes.add(new Route("/log", new LogController()));
        routes.add(new Route("/these", new TheseController()));
        routes.add(new Route("/requests", new RequestsController()));
        routes.add(new Route("/redirect", new RedirectController()));
    }

    private Optional<Route> findRoute(String path) {
        return routes.stream()
                     .filter(route -> route.getPath().equals(path))
                     .findFirst();
    }
}
