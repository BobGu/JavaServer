import controllers.Controller;
import controllers.TheseController;
import requests.Request;
import httpStatus.HttpStatus;
import logs.Log;
import org.junit.Test;
import specialCharacters.EscapeCharacters;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TheseControllerTest {
    private Controller controller = new TheseController();

    @Test
    public void TestRepliesWithTwoHundredOkayForPut() throws IOException {
        Request request = new Request("/these", "PUT", null, null);
        byte[] response = controller.handle(request);
        String responseString = new String(response);

        assertEquals(HttpStatus.okay + EscapeCharacters.newline + EscapeCharacters.newline, responseString);
    }

    @Test
    public void TestVisitGetsAddedToLog() throws IOException {
        Log log = Log.getInstance();
        Request request = new Request("/these", "PUT", null, null);
        controller.handle(request);
        List<String> recentVisits = log.recentVisits(1);
        String recentVisit = recentVisits.get(0);

        assertEquals("PUT /these HTTP/1.1", recentVisit);
    }

    @Test
    public void TestMethodsNotAllowed() throws IOException {
        Request request = new Request("/these", "POST", null, null);
        byte[] response = controller.handle(request);
        String responseString = new String(response);

        assertEquals(HttpStatus.methodNotAllowed + EscapeCharacters.newline + EscapeCharacters.newline,
                responseString);
    }
}
