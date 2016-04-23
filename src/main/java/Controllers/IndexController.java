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
    private final String METHODS_ALLOWED = "GET,OPTIONS";
    private ResourceCRUD resourceCRUD;
    private String directoryName;

    public IndexController(String directoryName, ResourceCRUD resourceCRUD) {
        this.directoryName = directoryName;
        this.resourceCRUD = resourceCRUD;
    }

    public byte[] handle(Request request) throws IOException {
        String response = "";

        if (request.getHttpVerb().equals("GET")) {
            response = get();
        } else if(request.getHttpVerb().equals("OPTIONS")) {
            response = options();
        } else {
            response = methodNotAllowed();
        }
        return response.getBytes();
    }

    private String get() throws IOException {
        String response = "";
        response += HttpStatus.okay + EscapeCharacters.newline;
        response += "Content-Type: text/html;" + EscapeCharacters.newline + EscapeCharacters.newline;
        String directoryAndFiles = resourceCRUD.read(directoryName);

        response += "<!DOCTYPE html><html><head><title>bobsjavaserver</title></head><body>";
        response += "<h1>Hello world</h1><ul>";
        response += format(directoryAndFiles) + "</ul>";
        response += "</body></html>";

        return response;
    }

    private String methodNotAllowed() {
        return HttpStatus.methodNotAllowed + EscapeCharacters.newline + EscapeCharacters.newline;
    }

    private String format(String directoryAndFiles) {
        String htmlFormat = "";
        String[] dirAndFiles = directoryAndFiles.split(" ");

        for(String file: dirAndFiles) {
            if (file.equals(directoryName)) {
                htmlFormat += "<li>" + directoryName + "</li>";
            } else {
                htmlFormat += "<li><a href=/" + file + ">" + file + "</a></li>";
            }
        }
        return htmlFormat;
    }

    private String options() {
        return  HttpStatus.okay
                + EscapeCharacters.newline
                + "Allow: "
                + METHODS_ALLOWED
                + EscapeCharacters.newline
                + EscapeCharacters.newline;
    }

}
