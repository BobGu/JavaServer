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
        setRoute(parseForRoute(socketInputStream));
        boolean pathExists = router.pathExists(Parser.parseForPathUrl(getRoute()));
        return handleResponse(pathExists);
    }

    private String handleResponse(boolean pathExists) throws IOException {
        String response = "";

        if(httpVerb(getRoute()).equals("GET") && path(getRoute()).equals("/")) {
            String responseHeader = "HTTP/1.1 200 OK\r\n\r\n";
            InputStream fileStream = Server.class.getResourceAsStream("index.html");
            String responseBody = Parser.parseInputStream(fileStream);
            response = responseHeader + responseBody;
        } else if(httpVerb(getRoute()).equals("POST") && path(getRoute()).equals("/form")) {
            response = "HTTP/1.1 200 OK\r\n";
        } else if(httpVerb(getRoute()).equals("OPTIONS") && path(getRoute()).equals("/")) {
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

    private String httpVerb(String route) {
        return Parser.parseForHttpVerb(getRoute());
    }

    private String path(String route) {
        return Parser.parseForPathUrl(route);
    }

}
