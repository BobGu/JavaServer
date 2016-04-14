import Controllers.Controller;
import Controllers.LogsController;
import Requests.Request;
import httpStatus.HttpStatus;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class LogsControllerTest {

    @Test
    public void TestItGivesAFourOhFourOhOneResponseIfNoAuthorizationHeader() throws IOException {
        Request request = new Request("/logs", "GET", null, null);
        Controller controller = new LogsController();
        assertThat(controller.handle(request), containsString(HttpStatus.notAuthorized));
    }

    @Test
    public void TestResponseContainsAResponseHeaderWithAWWWAuthenticateField() throws IOException {
        Request request = new Request("/logs", "GET", null, null);
        Controller controller = new LogsController();
        assertThat(controller.handle(request), containsString("WWW-Authenticate: Basic realm=\"/ Bob Server Logs\""));
    }

    @Test
    public void TestResponseIsTwoHundredIfAuthenticated() throws IOException {
        Request request = new Request("/logs", "GET", null, "YWRtaW46aHVudGVyMg==");
        Controller controller = new LogsController();
        assertThat(controller.handle(request), containsString(HttpStatus.okay));
    }

}
