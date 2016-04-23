package controllers;

import requests.Request;
import httpStatus.HttpStatus;
import logs.Log;
import specialCharacters.EscapeCharacters;

public class TheseController implements Controller{

    public byte[] handle(Request request) {
        String response = "";

        if(request.getHttpVerb().equals("PUT")) {
            response = put();
        } else {
            response = HttpStatus.methodNotAllowed + EscapeCharacters.newline + EscapeCharacters.newline;
        }
        return response.getBytes();
    }

    public String put() {
        Log log = Log.getInstance();
        log.addVisit("PUT /these HTTP/1.1");
        return HttpStatus.okay + EscapeCharacters.newline + EscapeCharacters.newline;
    }
}
