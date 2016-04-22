
import controllers.Controller;
import controllers.RequestsController;
import org.junit.Before;
import requests.Request;
import httpStatus.HttpStatus;
import logs.Log;
import org.junit.Test;
import specialCharacters.EscapeCharacters;

import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class RequestsControllerTest {
    private Controller controller;

    @Before
    public void setup() {
        controller = new RequestsController();
    }

    @Test
    public void TestRepliesWithTwoHundredOkayForPut() throws IOException {
        Request request = new Request("/requests", "HEAD", null, null, false, false);
        byte[] response = controller.handle(request);
        String responseString = new String(response);

        assertEquals(HttpStatus.okay + EscapeCharacters.newline + EscapeCharacters.newline, responseString);
    }

    @Test
    public void TestVisitGetsAddedToLog() throws IOException {
        Log log = Log.getInstance();
        Request request = new Request("/requests", "HEAD", null, null, false, false);

        controller.handle(request);

        List<String> recentVisits = log.recentVisits(100);
        String recentVisit = recentVisits.get(0);

        assertEquals("HEAD /requests HTTP/1.1", recentVisit);
    }

    @Test
    public void TestMethodsNotAllowed() throws IOException {
        Request request = new Request("/requests", "POST", null, null, false, false);
        Controller controller = new RequestsController();
        byte[] response = controller.handle(request);
        String responseString = new String(response);

        assertEquals(HttpStatus.methodNotAllowed + EscapeCharacters.newline + EscapeCharacters.newline,
                responseString);
    }
}
