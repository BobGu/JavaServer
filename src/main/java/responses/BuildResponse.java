package responses;

import Requests.Request;
import Routes.Router;

public class BuildResponse {

    private Router router;

    public BuildResponse(Router router) {
        this.router = router;
    }

    public String build(Request request) {
        if (!router.routeExists(request.getPath(), request.getHttpVerb())) {
            return "HTTP/1.1 404 Not Found\r\n\r\n";
        }

        return "hello";
    }

}
