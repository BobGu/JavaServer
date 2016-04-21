package controllers;

import controllers.Controller;
import controllers.LogController;
import httpStatus.HttpStatus;
import logs.Log;
import org.junit.Test;
import requests.Request;
import specialCharacters.EscapeCharacters;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class LogControllerTest {

    @Test
    public void TestRepliesWithTwoHundredOkayForGet() throws IOException {
        Request request = new Request("/log", "GET", null, null, false);
        Controller controller = new LogController();
        assertThat(controller.handle(request) , containsString(HttpStatus.okay));
    }

    @Test
    public void TestVisitGetsAddedToLog() throws IOException {
        Log log = Log.getInstance();
        Request request = new Request("/log", "GET", null, null, false);
        Controller controller = new LogController();

        controller.handle(request);

        List<String> recentVisits = log.recentVisits(1);
        String recentVisit = recentVisits.get(0);

        assertEquals("GET /log HTTP/1.1", recentVisit);
    }

    @Test
    public void TestMethodsNotAllowed() throws IOException {
        Request request = new Request("/log", "POST", null, null, false);
        Controller controller = new LogController();
        String response = controller.handle(request);

        assertEquals(HttpStatus.methodNotAllowed + EscapeCharacters.newline + EscapeCharacters.newline,
                     response);
    }


}
