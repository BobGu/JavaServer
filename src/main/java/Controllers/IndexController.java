package controllers;

import parsers.Parser;
import requests.Request;
import httpStatus.HttpStatus;
import resourceCRUD.DirectoryCRUD;
import resourceCRUD.ResourceCRUD;
import specialCharacters.EscapeCharacters;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class IndexController implements Controller {
    private ResourceCRUD resourceCRUD;
    private String directoryName;

    public IndexController(String directoryName, ResourceCRUD resourceCRUD) {
        this.directoryName = directoryName;
        this.resourceCRUD = resourceCRUD;
    }

    public String handle(Request request) throws IOException {
        return get();
    }

    public String get() throws IOException {
        String response = "";
        response += HttpStatus.okay + EscapeCharacters.newline + EscapeCharacters.newline;

        response += "<h1>Hello world<h1>";
        response += "<a href=" + resourceCRUD.read(directoryName) + ">" + directoryName + "</a>";

        return response;
    }

}
