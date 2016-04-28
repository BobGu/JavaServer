package routes;

import requests.Request;

import java.io.IOException;

public interface Router {

    public byte[] direct(Request request) throws IOException;

}
