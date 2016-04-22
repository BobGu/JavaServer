package controllers;

import httpStatus.HttpStatus;
import readers.Reader;
import requests.Request;
import specialCharacters.EscapeCharacters;

import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Encoder;

public class ImageController implements Controller{
    private String METHODS_ALLOWED = "GET,OPTIONS";
    private String directoryBaseUrl = "../resources/main/public";
    private Reader reader;

    public ImageController(Reader reader) {
        this.reader = reader;
    }

    public String handle(Request request) throws IOException {
        String response = "";

        if (request.getHttpVerb().equals("GET")) {
            String location = directoryBaseUrl + request.getPath();
            response = get(location, request.getPath());
        } else if(request.getHttpVerb().equals("OPTIONS")) {
            response = options();
        } else {
            response = methodNotAllowed();
        }
        return response;
    }

    private String get(String location, String path) throws IOException {
        String response = "";
        response += HttpStatus.okay + EscapeCharacters.newline;
        response += "Content-Type: text/html" + EscapeCharacters.newline + EscapeCharacters.newline;

        String encodedFile = reader.read(location);
        response += formatIntoDataUrl(encodedFile, path);
        return response;
    }

    private String options() {
        return HttpStatus.okay
                + EscapeCharacters.newline
                + "Allow: "
                + METHODS_ALLOWED
                + EscapeCharacters.newline
                + EscapeCharacters.newline;
    }

    private String methodNotAllowed() {
        return HttpStatus.methodNotAllowed + EscapeCharacters.newline + EscapeCharacters.newline;
    }

    private String formatIntoDataUrl(String encodedFile, String path) {
        return "<img alt='/public" + path + "' src='data:image/png;base64, " + encodedFile + "'/>";
    }

}
