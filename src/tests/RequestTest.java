import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestTest {
    private Request request;

    @Before
    public void RequestObjectCreation() {
        request = new Request("/", "GET", "data=fatcat");
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
    public void TestCanSetABody() {
        assertEquals("data=fatcat", request.getBody());
    }

}


