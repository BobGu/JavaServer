package controllers;

import requests.Request;

import java.io.IOException;

public interface Controller {

    public abstract String handle(Request request) throws IOException;

}
