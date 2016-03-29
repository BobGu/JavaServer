import java.io.IOException;
import java.io.InputStream;

public class Handler {
    private String route;
    private Router router;

    public Handler() {
        router = new Router();
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String handleRequest(InputStream socketInputStream) throws IOException {
        String route = parseForRoute(socketInputStream);
        boolean pathExists = router.pathExists(Parser.parseForPathUrl(route));
        return handleResponse(route, pathExists);
    }

    private String handleResponse(String route, boolean pathExists) throws IOException {
        String response = "";

        if(routesHttpVerb(route).equals("GET") && routesPath(route).equals("/")) {
            String responseHeader = "HTTP/1.1 200 OK\r\n";
            InputStream fileStream = Server.class.getResourceAsStream("index.html");
            String responseBody = Parser.parseInputStream(fileStream);
            response = responseHeader + responseBody;
        } else if(routesHttpVerb(route).equals("POST") && routesPath(route).equals("/form")) {
            response = "HTTP/1.1 200 OK\r\n";
        } else if(routesHttpVerb(route).equals("OPTIONS") && routesPath(route).equals("/")) {
            response = "HTTP/1.1 200 OK\r\nAllow: GET,HEAD,POST,OPTIONS,PUT\r\n";
        } else if(pathExists) {
            response = "HTTP/1.1 405 Method now allowed";
        } else {
            response = "HTTP/1.1 404 Not Found\r\n";
        }

        return response;
    }

    private String parseForRoute(InputStream socketInputStream) throws IOException {
        String requestHeader = Parser.parseInputStream(socketInputStream);
        String httpVerb = Parser.parseForHttpVerb(requestHeader);
        String pathUrl = Parser.parseForPathUrl(requestHeader);
        return httpVerb + " " + pathUrl;
    }


}
