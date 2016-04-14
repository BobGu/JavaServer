import Controllers.Controller;
import Controllers.LogController;
import Controllers.TheseController;
import Requests.Request;
import httpStatus.HttpStatus;
import logs.Log;
import org.junit.Test;
import specialCharacters.EscapeCharacters;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TheseControllerTest {

    @Test
    public void TestRepliesWithTwoHundredOkayForPut() throws IOException {
        Request request = new Request("/these", "PUT", null, null);
        Controller controller = new TheseController();
        String response = controller.handle(request);

        assertEquals(HttpStatus.okay + EscapeCharacters.newline + EscapeCharacters.newline, response);
    }

    @Test
    public void TestVisitGetsAddedToLog() throws IOException {
        Log log = Log.getInstance();
        Request request = new Request("/these", "PUT", null, null);
        Controller controller = new TheseController();

        controller.handle(request);

        List<String> recentVisits = log.recentVisits(1);
        String recentVisit = recentVisits.get(0);

        assertEquals("PUT /these HTTP/1.1", recentVisit);
    }

    @Test
    public void TestMethodsNotAllowed() throws IOException {
        Request request = new Request("/these", "POST", null, null);
        Controller controller = new TheseController();
        String response = controller.handle(request);

        assertEquals(HttpStatus.methodNotAllowed + EscapeCharacters.newline + EscapeCharacters.newline,
                response);
    }
}
