package configuration;

import controllers.Controller;
import routes.Route;
import routes.Router;
import servers.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Configuration {

    private int port = 5000;
    private ArrayList<Route> routes = new ArrayList<Route>();
    private Router router;


    public void setPort(int port) {
                                this.port = port;
                                                 }

    public void addRoute(String path, Controller controller) {
        Route route = new Route(path, controller);
        routes.add(route);
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public void setRoutes() {
        router.setRoutes(routes);
    }

    public void startServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        Server server = new Server(router);
        server.setServerSocket(serverSocket);
        server.startServer();
    }

}
