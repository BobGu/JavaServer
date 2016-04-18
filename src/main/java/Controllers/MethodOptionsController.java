package controllers;

import httpStatus.HttpStatus;
import requests.Request;
import specialCharacters.EscapeCharacters;

public class MethodOptionsController implements Controller{

    String METHODS_ALLOWED = "GET,HEAD,POST,OPTIONS,PUT";

    public String handle(Request request) {
        String response = "";

        if (request.getHttpVerb().equals("OPTIONS")) {
            response += options();
        } else if (METHODS_ALLOWED.contains(request.getHttpVerb())) {
            response = HttpStatus.okay + EscapeCharacters.newline + EscapeCharacters.newline;
        } else {
            response = HttpStatus.methodNotAllowed + EscapeCharacters.newline + EscapeCharacters.newline;
        }
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

}
