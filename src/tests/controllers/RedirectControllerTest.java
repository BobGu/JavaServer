package controllers;

import controllers.Controller;
import controllers.RedirectController;
import requests.Request;
import httpStatus.HttpStatus;
import org.junit.Test;
import specialCharacters.EscapeCharacters;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class RedirectControllerTest {
    private Request getRequest = new Request("GET /redirect HTTP/1.1", "/redirect", "GET", null, null);
    private Controller controller = new RedirectController();

    @Test
    public void TestGetsThreeOhOneRedirect() throws IOException {
        byte[] response = controller.handle(getRequest);
        String responseString = new String(response);

        assertThat(responseString , containsString(HttpStatus.REDIRECT.getResponseCode() + EscapeCharacters.newline));
    }

    @Test
    public void TestResponseIncludesTheLocationOfRedirect() throws IOException {
        byte[] response = controller.handle(getRequest);
        String responseString = new String(response);

        assertThat(responseString, containsString("http://localhost:5000/"));
    }

}
