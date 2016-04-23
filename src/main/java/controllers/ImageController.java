package controllers;

import httpStatus.HttpStatus;
import readers.Reader;
import requests.Request;
import specialCharacters.EscapeCharacters;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ImageController implements Controller{
    private final String METHODS_ALLOWED = "GET,OPTIONS";
    private String directoryBaseUrl = "../resources/main/public";
    private Reader reader;

    public ImageController(Reader reader) {
        this.reader = reader;
    }

    public byte[] handle(Request request) throws IOException {
        byte[] response;

        if (request.getHttpVerb().equals("GET")) {
            String location = directoryBaseUrl + request.getPath();
            response = get(location, request.getPath());
        } else if(request.getHttpVerb().equals("OPTIONS")) {
            response = options().getBytes();
        } else {
            response = methodNotAllowed().getBytes();
        }
        return response;
    }

    private byte[] get(String location, String path) throws IOException {
        String responseHeadersString = "";

        responseHeadersString += HttpStatus.okay + EscapeCharacters.newline;
        responseHeadersString += "Content-Type: image/jpeg" + EscapeCharacters.newline;

        byte[] responseBody = reader.read(location);
        responseHeadersString += "Content-Length: " + responseBody.length + EscapeCharacters.newline + EscapeCharacters.newline;
        byte[] responseHeader = responseHeadersString.getBytes();

        byte[] response = new byte[responseHeader.length + responseBody.length];
        System.arraycopy(responseHeader, 0, response, 0, responseHeader.length);
        System.arraycopy(responseBody, 0, response, responseHeader.length, responseBody.length);

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

}
