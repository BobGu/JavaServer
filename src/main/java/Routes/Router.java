package Routes;

import Controllers.FormController;
import Controllers.IndexController;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public String methodsAllowed(String path) {
        Stream<Route> routes = findRoutes(path);
        return routes.map(route -> route.getHttpVerb())
                     .collect(Collectors.joining(","))
                     .concat(",OPTIONS");
    }

    public boolean pathExists(String path) {
        return routes.stream().anyMatch(route -> route.getPath().equals(path));
    }

    public Route findRoute(String path, String httpVerb) {
        return routes.stream()
                     .filter(routeFound(path, httpVerb))
                     .findFirst()
                     .get();
    }

    private void createRoutes() {
        routes.add(new Route("/", "GET", new IndexController()));
        routes.add(new Route("/form", "GET", new FormController()));
    }

    private Stream<Route> findRoutes(String path) {
       return routes.stream()
                    .filter(route -> route.getPath().equals(path));

    }

    private Predicate<Route> routeFound(String path, String httpVerb) {
        return route -> route.getPath().equals(path) && route.getHttpVerb().equals(httpVerb);
    }

}
