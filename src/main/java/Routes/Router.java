package routes;

import requests.Request;

import java.io.IOException;
import java.util.ArrayList;

public interface Router {

    public byte[] direct(Request request) throws IOException;
    public void setRoutes(ArrayList<Route> routes);
    public void setDirectoryLocation(String location);
    public boolean isRoutes();

}
