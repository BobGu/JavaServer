package Controllers;

import Requests.Request;
import httpStatus.HttpStatus;
import specialCharacters.EscapeCharacters;

public class LogsController implements Controller{

    public String handle(Request request) {
        String response = " ";
        if (request.getHttpVerb().equals("GET")) {
            response = get(request);
        }
        return response;
    }

    private String get(Request request) {
        String response = "";
        response += HttpStatus.notAuthorized + EscapeCharacters.newline;
        response += "WWW-Authenticate: Basic realm=\"/ Bob Server Logs\"" + EscapeCharacters.newline + EscapeCharacters.newline;
        return response;
    }
}
