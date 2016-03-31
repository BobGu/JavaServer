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

    public String handleRequest(InputStream socketInputStream) throws IOException {
        request = Parser.parseRequest(socketInputStream);
        route = parseForRoute();
        boolean pathExists = router.pathExists(Parser.parseForPathUrl(getRoute()));
        return router.chooseRoute(pathExists, route, request);
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
