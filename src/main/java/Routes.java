import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Routes {
    private ArrayList<Route> routes;

    public Routes() {
        routes = new ArrayList<Route>();
        routes.add(new Route("GET", "localhost:5000/"));
    }

    public boolean exist(String httpVerb, String url) {
        Predicate<Route> routeFound = route -> route.getHttpVerb().equals(httpVerb) && route.getUrl().equals(url);
        return routes.stream().anyMatch(routeFound);
    }

}
