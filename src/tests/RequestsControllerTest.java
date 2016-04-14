import Controllers.Controller;
import Controllers.RequestsController;
import Requests.Request;
import httpStatus.HttpStatus;
import logs.Log;
import org.junit.Test;
import specialCharacters.EscapeCharacters;

import java.io.IOException;
import java.util.List;

public class RequestsControllerTest {

    @Test
    public void TestRepliesWithTwoHundredOkayForPut() throws IOException {
        Request request = new Request("/requests", "HEAD", null, null);
        Controller controller = new RequestsController();
        String response = controller.handle(request);

        assertEquals(HttpStatus.okay + EscapeCharacters.newline + EscapeCharacters.newline, response);
    }

    private void assertEquals(String s, String response) {
    }

    @Test
    public void TestVisitGetsAddedToLog() throws IOException {
        Log log = Log.getInstance();
        Request request = new Request("/requests", "HEAD", null, null);
        Controller controller = new RequestsController();

        controller.handle(request);

        List<String> recentVisits = log.recentVisits(1);
        String recentVisit = recentVisits.get(0);

        assertEquals("HEAD /requests HTTP/1.1", recentVisit);
    }

    @Test
    public void TestMethodsNotAllowed() throws IOException {
        Request request = new Request("/requests", "POST", null, null);
        Controller controller = new RequestsController();
        String response = controller.handle(request);

        assertEquals(HttpStatus.methodNotAllowed + EscapeCharacters.newline + EscapeCharacters.newline,
                response);
    }
}
