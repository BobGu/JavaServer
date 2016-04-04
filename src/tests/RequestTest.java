import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestTest {
    private Request request;

    @Before
    public void RequestObjectCreation() {
        request = new Request();
    }

    @Test
    public void TestCanSetAPath() {
        request.setPath("/");
        assertEquals("/", request.getPath());
    }

    @Test
    public void TestCanSetAHttpVerb() {
        request.setHttpVerb("GET");
        assertEquals("GET", request.getHttpVerb());
    }

    @Test
    public void TestCanSetABody() {
        request.setBody("data=fatcat");
        assertEquals("data=fatcat", request.getBody());
    }

}


