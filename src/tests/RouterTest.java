import Mocks.MockRequest;
import Requests.Request;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class RouterTest {

    @Test
    public void TestHandleReturnsAppropriateResponse() throws IOException {
        Map<String, Controller> routes = new HashMap<String, Controller>();
        routes.put("/", new MockIndexController());

        Router router = new Router(routes);
        MockRequest request = new MockRequest("/", "GET", null);
        String response = router.handle(request);

        assertEquals("HTTP/1.1 200 OK\r\n\r\n", response);
    }

    @Test
    public void TestHandleReturnsCorrectResponse() throws IOException {
        Map<String, Controller> routes = new HashMap<String, Controller>();
        routes.put("/", new MockIndexController());

        Router router = new Router(routes);
        MockRequest request = new MockRequest("/", "POST", "data=thisNewData");
        String response = router.handle(request);

        assertEquals("HTTP/1.1 200 OK\r\n\r\n", response);
    }


    private class MockIndexController implements Controller {

        public String get() {
            return "HTTP/1.1 200 OK\r\n\r\n";
        }

        public String post(Request request) {
            return "HTTP/1.1 200 OK\r\n\r\n";
        }

        public String delete() {
            return "hello";
        }

        public String put(Request request) { return "yo";}

    }


}
