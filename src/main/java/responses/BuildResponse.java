package responses;

import Requests.Request;
import Routes.Router;

public class BuildResponse {

    private Router router;

    public BuildResponse(Router router) {
        this.router = router;
    }

    public String build(Request request) {
        String response = "";

        if(!router.pathExists(request.getPath())) {
            response = "HTTP/1.1 404 Not Found\r\n\r\n";
        } else if(request.getHttpVerb().equals("OPTIONS")) {
            response = "HTTP/1.1 200 OK\r\nAllow: "
                    + router.methodsAllowed(request.getPath())
                    + "\r\n\r\n";
        } else {
            response = "HTTP/1.1 200 OK\r\n\r\n";
        }

        return response;
    }

}
