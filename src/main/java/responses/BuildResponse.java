package responses;

import Requests.Request;
import Routes.Route;
import Routes.Router;

import java.io.IOException;

public class BuildResponse {

    private Router router;
    private int portNumber;

    public BuildResponse(Router router, int portNumber) {
        this.router = router;
        this.portNumber = portNumber;
    }

    public String build(Request request) throws IOException {
        String response = "";

        if(router.routeExists(request.getPath(), request.getHttpVerb())) {
            response += "HTTP/1.1 200 OK\r\n\r\n";
            Route route = router.findRoute(request.getPath(), request.getHttpVerb());

            if (request.getHttpVerb().equals("GET")) {
                response += route.getController().get(request);
            } else if(request.getHttpVerb().equals("POST")) {
                route.getController().post(request);
            } else if (request.getHttpVerb().equals("PUT")) {
                route.getController().post(request);
            } else if (request.getHttpVerb().equals("DELETE")) {
                route.getController().delete();
            }
        } else if (request.getHttpVerb().equals("OPTIONS") && router.pathExists(request.getPath())) {
            response = "HTTP/1.1 200 OK\r\nAllow: "
            + router.methodsAllowed(request.getPath())
            + "\r\n\r\n";
        } else if(request.getPath().equals("/redirect")) {
            response += "HTTP/1.1 302 Found\r\n"
                      + "Location: http://localhost:"
                      + String.valueOf(portNumber)
                      + "/\r\n\r\n";
        } else {
            response = "HTTP/1.1 404 Not Found\r\n\r\n";
        }

        return response;
    }

}
