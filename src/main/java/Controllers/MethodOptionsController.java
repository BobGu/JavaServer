package controllers;

import httpStatus.HttpStatus;
import requests.Request;
import specialCharacters.EscapeCharacters;

public class MethodOptionsController implements Controller{

    String METHODS_ALLOWED = "GET,HEAD,POST,OPTIONS,PUT";

    public byte[] handle(Request request) {
        String response = "";

        if (request.getHttpVerb().equals("OPTIONS")) {
            response += options();
        } else if (METHODS_ALLOWED.contains(request.getHttpVerb())) {
            response = HttpStatus.OKAY.getResponseCode() + EscapeCharacters.newline + EscapeCharacters.newline;
        } else {
            response = HttpStatus.METHOD_NOT_ALLOWED.getResponseCode() + EscapeCharacters.newline + EscapeCharacters.newline;
        }
        return response.getBytes();
    }

    private String options() {
        return HttpStatus.OKAY.getResponseCode()
               + EscapeCharacters.newline
               + "Allow: "
               + METHODS_ALLOWED
               + EscapeCharacters.newline
               + EscapeCharacters.newline;
    }

}
