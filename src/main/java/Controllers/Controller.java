package Controllers;

import Requests.Request;

import java.io.IOException;

public interface Controller {

    public abstract String get() throws IOException;
    public abstract String post(Request request) throws IOException;
    public abstract void delete();
    public abstract String put(Request request) throws IOException;
    public abstract void head();

}
