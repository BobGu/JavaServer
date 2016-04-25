package controllers;

import httpStatus.HttpStatus;
import readers.Reader;
import requests.Request;
import specialCharacters.EscapeCharacters;
import java.io.IOException;

public class FileController implements Controller {
    private final String METHODS_ALLOWED = "GET,OPTIONS";
    private String fileLocation;
    private Reader reader;

    public FileController(Reader reader, String fileLocation) {
        this.reader = reader;
        this.fileLocation = fileLocation;
    }

    public byte[] handle(Request request) throws IOException {
       if (request.getHttpVerb().equals("GET")) {
           return get(fileLocation);
       } else if (request.getHttpVerb().equals("OPTIONS")) {
           return options().getBytes();
       } else {
           return methodNotAllowed().getBytes();
       }
    }

    private byte[] get(String resourcePath) throws IOException {
        String responseHeadersString = "";
        responseHeadersString += HttpStatus.okay + EscapeCharacters.newline;
        String contentType = determineContentType(resourcePath);

        responseHeadersString += "Content-Type: " + contentType + EscapeCharacters.newline;

        byte[] responseBody = reader.read(resourcePath);
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

    private String determineContentType(String resourcePath) {
        String contentType = "";

        if (resourcePath.contains(".gif")) {
            contentType = "image/gif";
        } else if (resourcePath.contains(".jpeg") || resourcePath.contains(".jpg")) {
            contentType = "image/jpeg";
        } else if (resourcePath.contains(".png")) {
            contentType = "image/png";
        } else {
            contentType = "text/html";
        }
        return contentType;
    }

}
