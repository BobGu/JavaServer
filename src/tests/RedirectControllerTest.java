import Controllers.Controller;
import Controllers.RedirectController;
import Requests.Request;
import httpStatus.HttpStatus;
import org.junit.Test;
import specialCharacters.EscapeCharacters;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class RedirectControllerTest {
    private Request getRequest = new Request("/redirect", "GET", null, null);
    private Controller controller = new RedirectController();

    @Test
    public void TestGetsThreeOhOneRedirect() throws IOException {
        assertThat(controller.handle(getRequest), containsString(HttpStatus.redirect + EscapeCharacters.newline));
    }

    @Test
    public void TestResponseIncludesTheLocationOfRedirect() throws IOException {
        assertThat(controller.handle(getRequest), containsString("http://localhost:5000/"));
    }

}
