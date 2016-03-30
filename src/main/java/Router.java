import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Router {

    private Map<String, ArrayList<String>> routes;

    public Router() {
        routes = createRoutes();
    }

    public boolean pathExists(String url) {
        List<String> methods = routes.get(url);
        return methods != null;
    }

    private Map<String, ArrayList<String>> createRoutes() {
        HashMap<String, ArrayList<String>> routesToReturn = new HashMap<String, ArrayList<String>>();
        ArrayList<String> rootRoutes = new ArrayList<String>();
        rootRoutes.add("GET");

        routesToReturn.put("/", rootRoutes);
        return routesToReturn;
    }

}
