package controllers;

import requests.Request;
import readers.Reader;
import httpStatus.HttpStatus;
import specialCharacters.EscapeCharacters;

import java.io.*;

public class IndexController implements Controller {
    private final String METHODS_ALLOWED = "GET,OPTIONS";
    private Reader reader;
    private String directoryName;

    public IndexController(String directoryName, Reader reader) {
        this.directoryName = directoryName;
        this.reader= reader;
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
        byte[] directoryAndFiles = reader.read(directoryName);
        String files = new String(directoryAndFiles);

        response += "<!DOCTYPE html><html><head><title>bobsjavaserver</title></head><body>";
        response += "<h1>Hello world</h1><ul>";
        response += format(files) + "</ul>";
        response += "</body></html>";

        return response;
    }

    private String methodNotAllowed() {
        return HttpStatus.methodNotAllowed + EscapeCharacters.newline + EscapeCharacters.newline;
    }

    private String format(String directoryAndFiles) {
        String htmlFormat = "";
        String[] dirAndFiles = directoryAndFiles.split(" ");
        int count = 0;

        for(String file: dirAndFiles) {
            if (count == 0) {
                htmlFormat += "<li>" + file + "</li>";
            } else {
                htmlFormat += "<li><a href=/" + file + ">" + file + "</a></li>";
            }
            count += 1;
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
