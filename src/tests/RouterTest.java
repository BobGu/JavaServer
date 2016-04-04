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

    private class MockRequest extends Request {
        private String path;
        private String httpVerb;
        private String body;

        public MockRequest(String path, String httpVerb, String body) {
            super(path, httpVerb, body);
        }
    }

    private abstract class MockController extends Controller{
        public abstract String get();
        public abstract String post(String request);
        public abstract String delete();
    }

    private class MockIndexController extends MockController{

        public String get() {
            return "HTTP/1.1 200 OK\r\n\r\n";
        }

        public String post(String request) {
            return "HTTP/1.1 200 OK\r\n\r\n";
        }

        public String delete() {
            return "hello";
        }

    }


}
