package controllers;

import requests.Request;
import httpStatus.HttpStatus;
import logs.Log;
import specialCharacters.EscapeCharacters;

public class RequestsController implements Controller {

    public byte[] handle(Request request) {
        String response = "";

        if(request.getHttpVerb().equals("HEAD")) {
            response = head();
        } else {
            response = HttpStatus.methodNotAllowed + EscapeCharacters.newline + EscapeCharacters.newline;
        }
        return response.getBytes();
    }

    public String head() {
        Log log = Log.getInstance();
        log.addVisit("HEAD /requests HTTP/1.1");
        return HttpStatus.okay + EscapeCharacters.newline + EscapeCharacters.newline;
    }

}
