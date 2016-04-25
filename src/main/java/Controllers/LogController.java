package controllers;
import requests.Request;
import httpStatus.HttpStatus;
import logs.Log;
import specialCharacters.EscapeCharacters;

public class LogController implements Controller{
    private Log log = Log.getInstance();

    public byte[] handle(Request request) {
        String response = "";
        if (request.getHttpVerb().equals("GET") && request.getPath().equals("/log")) {
            response = logVisit(request.getFullRequest());
        } else if (request.getHttpVerb().equals("PUT") && request.getPath().equals("/these")) {
            response = logVisit(request.getFullRequest());
        } else if (request.getHttpVerb().equals("HEAD") && request.getPath().equals("/requests")) {
            response = logVisit(request.getFullRequest());
        } else {
            response = HttpStatus.methodNotAllowed + EscapeCharacters.newline + EscapeCharacters.newline;
        }


        return response.getBytes();
    }

    private String logVisit(String fullRequest) {
        String[] linesOfRequest = fullRequest.split(EscapeCharacters.newline);
        String visit = linesOfRequest[0];
        log.addVisit(visit);
        return HttpStatus.okay + EscapeCharacters.newline + EscapeCharacters.newline;
    }
}
