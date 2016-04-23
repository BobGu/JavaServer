package controllers;

import requests.Request;

import java.io.IOException;

public interface Controller {

    public abstract byte[] handle(Request request) throws IOException;

}
