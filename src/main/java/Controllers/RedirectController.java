package controllers;

import requests.Request;
import httpStatus.HttpStatus;
import specialCharacters.EscapeCharacters;


public class RedirectController implements Controller{

    public byte[] handle(Request request) {
        String response = "";

        if (request.getHttpVerb().equals("GET")) {
            response = get();
        }
        return response.getBytes();
    }

    private String get() {
        return HttpStatus.redirect
               + EscapeCharacters.newline
               + "Location: http://localhost:5000/"
               + EscapeCharacters.newline
               + EscapeCharacters.newline;
    }
}
