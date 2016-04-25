package controllers;

import controllers.Controller;
import controllers.LogController;
import httpStatus.HttpStatus;
import logs.Log;
import org.junit.Before;
import org.junit.Test;
import requests.Request;
import specialCharacters.EscapeCharacters;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class LogControllerTest {
    private Controller controller;

    @Before
    public void setup() {
        controller = new LogController();
    }

    @Test
    public void TestRepliesWithTwoHundredOkayForGet() throws IOException {
        Request request = new Request("GET /log HTTP/1.1", "/log", "GET", null, null);
        byte[] response = controller.handle(request);
        String responseString = new String(response);

        assertThat(responseString, containsString(HttpStatus.okay));
    }

    @Test
    public void TestVisitGetsAddedToLog() throws IOException {
        Log log = Log.getInstance();
        Request request = new Request("GET /log HTTP/1.1", "/log", "GET", null, null);
        byte[] response = controller.handle(request);
        String responseString = new String(response);

        controller.handle(request);

        List<String> recentVisits = log.recentVisits(1);
        String recentVisit = recentVisits.get(0);

        assertEquals("GET /log HTTP/1.1", recentVisit);
    }

    @Test
    public void TestMethodsNotAllowed() throws IOException {
        Request request = new Request("GET /log HTTP/1.1", "/log", "POST", null, null);
        byte[] response = controller.handle(request);
        String responseString = new String(response);

        assertEquals(HttpStatus.methodNotAllowed + EscapeCharacters.newline + EscapeCharacters.newline,
                     responseString);
    }

    @Test
    public void CanAddAnyVisitToTheLog() throws IOException {
        Log log = Log.getInstance();
        String fullRequest = "GET /random HTTP/1.1" + EscapeCharacters.newline + "Host: localhost:5000" + EscapeCharacters.newline + EscapeCharacters.newline;
        Request request = new Request(fullRequest, "/random", "GET", null ,null);
        byte[] response = controller.handle(request);
        String responseString = new String(response);

        List<String> recentVisits = log.recentVisits(1);
        String recentVisit = recentVisits.get(0);

        assertEquals("GET /random HTTP/1.1", recentVisit);
    }


}
