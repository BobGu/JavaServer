package routes;

import controllers.*;
import readers.FileReader;
import requests.Request;
import httpStatus.HttpStatus;
import specialCharacters.EscapeCharacters;
import writers.FileWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class Router {

    private ArrayList<Route> routes = new ArrayList<Route>();
    private String directoryName;

    public void setDirectory(String directoryName) {
        this.directoryName = directoryName;
    }

    public void setRoutes() {
        setRoutes(null);
    }

    public void setRoutes(ArrayList<Route> routes) {
        if (routes == null) {
            createRoutes();
        } else {
            this.routes = routes;
        }
    }

    public byte[] direct(Request request) throws IOException {
        byte[] response;
        Optional<Route> route = findRoute(request.getPath());
        String fileLocation = directoryName + request.getPath();
        File file = new File(fileLocation);
        boolean fileRequested = file.exists();

        if (route.isPresent()) {
            response = route.get().getController().handle(request);
        } else if (fileRequested) {
            Controller controller = new FileController(new FileReader(), fileLocation);
            response = controller.handle(request);
        } else {
            String responseString = HttpStatus.notFound + EscapeCharacters.newline + EscapeCharacters.newline;
            response = responseString.getBytes();
        }
        return response;
    }

    private void createRoutes() {
        routes.add(new Route("/", new IndexController(directoryName, new FileReader())));
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
