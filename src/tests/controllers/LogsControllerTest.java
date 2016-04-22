
import controllers.Controller;
import controllers.LogsController;
import controllers.TheseController;
import requests.Request;
import httpStatus.HttpStatus;
import logs.Log;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class LogsControllerTest {
    private Controller controller = new LogsController();

    @Test
    public void TestItGivesAFourOhFourOhOneResponseIfNoAuthorizationHeader() throws IOException {
        Request request = new Request("/logs", "GET", null, null, false, false);
        byte[] response = controller.handle(request);
        String responseString = new String(response);

        assertThat(responseString, containsString(HttpStatus.notAuthorized));
    }

    @Test
    public void TestResponseContainsAResponseHeaderWithAWWWAuthenticateField() throws IOException {
        Request request = new Request("/logs", "GET", null, null, false, false);
        byte[] response = controller.handle(request);
        String responseString = new String(response);

        assertThat(responseString, containsString("WWW-Authenticate: Basic realm=\"/ Bob Server Logs\""));
    }

    @Test
    public void TestResponseIsTwoHundredIfAuthenticated() throws IOException {
        Request request = new Request("/logs", "GET", null, "YWRtaW46aHVudGVyMg==", false, false);
        byte[] response = controller.handle(request);
        String responseString = new String(response);

        assertThat(responseString, containsString(HttpStatus.okay));
    }

    @Test
    public void TestResponseContainsLogsIfAuthenticated() throws IOException {
        Controller mockController = new MockTheseController();
        Request logThisRequest = new Request("/these", "GET", null, null, false, false);
        mockController.handle(logThisRequest);

        Request request = new Request("/logs", "GET" , null, "YWRtaW46aHVudGVyMg==", false, false);
        byte[] response = controller.handle(request);
        String responseString = new String(response);

        assertThat(responseString, containsString("GET /these HTTP/1.1"));

    }

    private class MockTheseController extends TheseController {
        @Override
        public byte[] handle(Request request) {
            Log log = Log.getInstance();
            log.addVisit("GET /these HTTP/1.1");
            return "Logged a get request to /these".getBytes();
        }
    }

}
