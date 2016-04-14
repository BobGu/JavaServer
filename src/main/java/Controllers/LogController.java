package Controllers;
import Requests.Request;
import httpStatus.HttpStatus;
import logs.Log;
import specialCharacters.EscapeCharacters;

public class LogController implements Controller{

    public String handle(Request request) {
        String response = "";
        if (request.getHttpVerb().equals("GET")) {
            response = get();
        } else {
            response = HttpStatus.methodNotAllowed + EscapeCharacters.newline + EscapeCharacters.newline;
        }

        return response;
    }

    private String get() {
        Log log = Log.getInstance();
        log.addVisit("GET /log HTTP/1.1");
        return HttpStatus.okay + EscapeCharacters.newline + EscapeCharacters.newline;
    }
}
