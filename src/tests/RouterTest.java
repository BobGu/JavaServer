import Controllers.Controller;
import Mocks.MockController;
import Mocks.MockRequest;
import Routes.Router;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class RouterTest {

    @Test
    public void TestHandleReturnsAppropriateResponse() throws IOException {
        Map<String, Controller> routes = new HashMap<String, Controller>();
        routes.put("/", new MockController());

        Router router = new Router(routes);
        MockRequest request = new MockRequest("/", "GET", null);
        String response = router.handle(request);

        assertEquals("HTTP/1.1 200 OK\r\n\r\n", response);
    }

    @Test
    public void TestHandleReturnsCorrectResponse() throws IOException {
        Map<String, Controller> routes = new HashMap<String, Controller>();
        routes.put("/", new MockController());

        Router router = new Router(routes);
        MockRequest request = new MockRequest("/", "POST", "data=thisNewData");
        String response = router.handle(request);

        assertEquals("HTTP/1.1 200 OK\r\n\r\n", response);
    }


}
