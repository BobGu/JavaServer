package controllers;

import requests.Request;
import httpStatus.HttpStatus;
import specialCharacters.EscapeCharacters;

public class ParameterController implements Controller {

    public byte[] handle(Request request) {
        String response = "";

        if (request.getHttpVerb().equals("GET")) {
            response = get(request);
        }
        return response.getBytes();
    }

    public String get(Request request) {
        String responseHead = HttpStatus.okay + EscapeCharacters.newline + EscapeCharacters.newline;
        String responseBody = request.getParameters();

        return responseHead + responseBody;
    }

}
