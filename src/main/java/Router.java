import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Router {

    private Map<String, List<String>> routes;

    public Router(Map routes) {
        this.routes = routes;
    }

    public boolean requestAllowed(String url, String httpVerb) {
        List<String> methods = routes.get(url);
        if(methods.size() == 0) {
            return methods.contains(httpVerb);
        } else {
            return false;
        }
    }
}
