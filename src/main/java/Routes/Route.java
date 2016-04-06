package Routes;

import Controllers.Controller;

public class Route {
    private String path;
    private String httpVerb;
    private Controller controller;

    public Route(String path, String httpVerb, Controller controller) {
        this.path = path;
        this.httpVerb = httpVerb;
        this.controller = controller;
    }

    public String getPath() {
        return path;
    }

    public String getHttpVerb() {
        return httpVerb;
    }

    public Controller getController() {
        return controller;
    }

    @Override
    public String toString() {
        return httpVerb + " " + path;
    }


}
