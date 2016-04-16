package controllers;

import requests.Request;
import httpStatus.HttpStatus;
import logs.Log;
import specialCharacters.EscapeCharacters;

import javax.xml.bind.DatatypeConverter;
import java.util.List;

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
        String authorizationCode = "";

        try {
            authorizationCode = decodeBase64(request.getAuthorization());
        } catch (NullPointerException e) {
            e.getMessage();
        }

        if (request.getAuthorization() != null && isAdmin(authorizationCode)) {
            response += HttpStatus.okay + EscapeCharacters.newline + EscapeCharacters.newline;
            response += getLogVisits();
        } else {
            response += HttpStatus.notAuthorized + EscapeCharacters.newline;
            response += "WWW-Authenticate: Basic realm=\"/ Bob Server Logs\"" + EscapeCharacters.newline + EscapeCharacters.newline;
        }
        return response;
    }

    private String getLogVisits() {
        String visits = "";
        Log log = Log.getInstance();
        List<String> recentVisits = log.recentVisits(3);

        for(String recentVisit:recentVisits) {
            visits += recentVisit + EscapeCharacters.newline;
        }

        return visits;
    }

    private String decodeBase64(String encodedString) {
        byte[] decodedBytes = DatatypeConverter.parseBase64Binary(encodedString);
        return new String(decodedBytes);
    }

    private String getAdminCredentials() {
        return "admin:hunter2";
    }

    private boolean isAdmin(String authorizationCode) {
        return authorizationCode.equals(getAdminCredentials());
    }
}
