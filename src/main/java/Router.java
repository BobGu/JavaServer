import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Router {

    private Map<String, ArrayList<String>> routes;

    public Router() {
        routes = createRoutes();
    }

    private Map<String, ArrayList<String>> createRoutes() {
        HashMap<String, ArrayList<String>> routesToReturn = new HashMap<String, ArrayList<String>>();
        ArrayList<String> rootRoutes = new ArrayList<String>();
        rootRoutes.add("GET");

        routesToReturn.put("/", rootRoutes);
        return routesToReturn;
    }

    public String getResponse(String route, String request) throws IOException {
        String response = "";
        String path = Parser.parseForPathUrl(route);
        String httpVerb = Parser.parseForHttpVerb(route);

        if(path.equals("/")) {
            IndexController indexController = new IndexController();
            response = chooseAndCallControllerAction(indexController, httpVerb, request);
        } else if(path.equals("/form")) {
            FormController formController = new FormController();
            response = chooseAndCallControllerAction(formController, httpVerb, request);
        } else if(httpVerb.equals("OPTIONS") && path.equals("/")) {
            response = "HTTP/1.1 200 OK\r\nAllow: GET,HEAD,POST,OPTIONS,PUT\r\n";
        } else {
            response = "HTTP/1.1 404 Not Found\r\n";
        }

        return response;
    }

    private String chooseAndCallControllerAction(Controller controller, String httpVerb, String request) throws IOException {
        String response =  "";

        if(httpVerb.equals("GET")) {
            response = controller.get();
        } else if(httpVerb.equals("POST")) {
            response = controller.post(request);
        } else if(httpVerb.equals("DELETE")) {
            response = controller.delete();
        }

        return response;
    }

}
