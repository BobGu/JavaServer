import java.io.*;
import java.net.URL;

public class Handler {
    private String route;
    private Router router;
    private String request;

    public Handler() {
        router = new Router();
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String handleRequestAndResponse(InputStream socketInputStream) throws IOException {
        handleRequest(socketInputStream);
        return response();
    }

    private void handleRequest(InputStream socketInputStream) throws IOException {
        setRequest(Parser.parseRequest(socketInputStream));
        setRoute(parseForRoute());
    }

    private String response() throws IOException {
        return router.getResponse(getRoute(), getRequest());
    }

    private String parseForRoute() throws IOException {
        String httpVerb = Parser.parseForHttpVerb(request);
        String pathUrl = Parser.parseForPathUrl(request);
        return httpVerb + " " + pathUrl;
    }

    private String httpVerb(String route) {
        return Parser.parseForHttpVerb(getRoute());
    }

    private String path(String route) {
        return Parser.parseForPathUrl(route);
    }

}
