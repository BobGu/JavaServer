package Controllers;

import Requests.Request;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface Controller {

    public abstract String get() throws IOException;
    public abstract void post(Request request) throws IOException;
    public abstract void delete();
    public abstract void put(Request request) throws IOException;

}
