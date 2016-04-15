package Controllers;

import Requests.Request;
import httpStatus.HttpStatus;
import specialCharacters.EscapeCharacters;


public class RedirectController implements Controller{

    public String handle(Request request) {
        String response = "";

        if (request.getHttpVerb().equals("GET")) {
            response = get();
        }
        return response;
    }

    private String get() {
        return HttpStatus.redirect
               + EscapeCharacters.newline
               + "Location: http://localhost:5000/"
               + EscapeCharacters.newline
               + EscapeCharacters.newline;
    }
}
