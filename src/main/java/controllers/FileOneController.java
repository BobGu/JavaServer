package controllers;

import httpStatus.HttpStatus;
import readers.Reader;
import requests.Request;
import specialCharacters.EscapeCharacters;
import java.io.IOException;

public class FileOneController implements Controller {
    private final String METHODS_ALLOWED = "GET,OPTIONS";
    private Reader reader;
    private String resourcePath;

    public FileOneController(Reader reader) {
        this(null, reader);
    }

    public FileOneController(String resourcePath, Reader reader) {
        if (resourcePath == null) {
            this.resourcePath = "../resources/main/public/file1";
        } else {
            this.resourcePath = resourcePath;
        }
        this.reader = reader;
    }

    public String handle(Request request) throws IOException {
       String response = "";

       if (request.getHttpVerb().equals("GET")) {
          response = get(resourcePath);
       } else if (request.getHttpVerb().equals("OPTIONS")) {
          response = options();
       } else {
           response = methodNotAllowed();
       }
       return response;
    }

    private String get(String resourcePath) throws IOException {
        String response = "";
        response += HttpStatus.okay + EscapeCharacters.newline + EscapeCharacters.newline;
        response += reader.read(resourcePath);
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
