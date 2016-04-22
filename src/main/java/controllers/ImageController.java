package controllers;

import httpStatus.HttpStatus;
import readers.Reader;
import requests.Request;
import specialCharacters.EscapeCharacters;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Encoder;

public class ImageController implements Controller{
    private final String METHODS_ALLOWED = "GET,OPTIONS";
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
        response += "Content-Type: image/jpeg" + EscapeCharacters.newline;

        File file = new File(location);
        byte[] fileContent = Files.readAllBytes(file.toPath());
        String image = new String(fileContent);

        response += "Content-Length: " + fileContent.length + EscapeCharacters.newline + EscapeCharacters.newline;
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
