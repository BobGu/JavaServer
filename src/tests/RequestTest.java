import Requests.Request;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestTest {
    private Request request;

    @Before
    public void RequestObjectCreation() {
        request = new Request("/", "GET", "data=fatcat", "QWxhZGRpbjpPcGVuU2VzYW1l");
    }

    @Test
    public void TestCanSetAPath() {
        assertEquals("/", request.getPath());
    }

    @Test
    public void TestCanSetAHttpVerb() {
        assertEquals("GET", request.getHttpVerb());
    }

    @Test
    public void TestCanSetParameters() {
        assertEquals("data=fatcat", request.getParameters());
    }

    @Test
    public void TestCanGetAuthenticateBase64() {
        assertEquals("QWxhZGRpbjpPcGVuU2VzYW1l", request.getAuthorization());
    }

}


