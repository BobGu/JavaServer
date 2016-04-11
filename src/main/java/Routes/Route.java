package Routes;

import Controllers.Controller;

public class Route {
    private String path;
    private Controller controller;

    public Route(String path, Controller controller) {
        this.path = path;
        this.controller = controller;
    }

    public String getPath() {
        return path;
    }

    public Controller getController() {
        return controller;
    }

}
