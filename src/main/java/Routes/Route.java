package Routes;

import Controllers.Controller;

public class Route {
    private String path;
    private String[] methodsAllowed;
    private Controller controller;

    public Route(String path, String[] methodsAllowed, Controller controller) {
        this.path = path;
        this.methodsAllowed = methodsAllowed;
        this.controller = controller;
    }

    public String getPath() {
        return path;
    }

    public String[] getMethodsAllowed() {
        return methodsAllowed;
    }

    public Controller getController() {
        return controller;
    }
}
