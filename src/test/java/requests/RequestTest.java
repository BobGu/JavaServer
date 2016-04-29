import requests.Request;
import org.junit.Before;
import org.junit.Test;
import specialCharacters.EscapeCharacters;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class RequestTest {
    private Request request;

    @Before
    public void RequestObjectCreation() {
        String requestString = "GET / HTTP/1.1"
                + EscapeCharacters.newline
                + "Host: localhost:5000";
        request = new Request(requestString, "/", "GET", "data=fatcat", "QWxhZGRpbjpPcGVuU2VzYW1l");
    }

    @Test
    public void CanSetAPath() {
        assertEquals("/", request.getPath());
    }

    @Test
    public void CanSetAHttpVerb() {
        assertEquals("GET", request.getHttpVerb());
    }

    @Test
    public void CanSetParameters() {
        assertEquals("data=fatcat", request.getParameters());
    }

    @Test
    public void CanGetAuthenticateBase64() {
        assertEquals("QWxhZGRpbjpPcGVuU2VzYW1l", request.getAuthorization());
    }

    @Test
    public void CanGetFullRequest() {
        String requestString = "GET / HTTP/1.1"
                       + EscapeCharacters.newline
                       + "Host: localhost:5000";
        assertTrue((request.getFullRequest()).contains(requestString));
    }

}


