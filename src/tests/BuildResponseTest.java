import Requests.Request;
import Routes.Router;
import org.junit.Test;
import responses.BuildResponse;

import static org.junit.Assert.assertEquals;

public class BuildResponseTest {

    @Test
    public void TestReturnsAFourOhFour() {
        MockRouter router = new MockRouter(false, false);
        BuildResponse buildResponse = new BuildResponse(router);
        Request request = new Request("/foobar", "GET", null);

        assertEquals("HTTP/1.1 404 Not Found\r\n\r\n", buildResponse.build(request));
    }

    @Test
    public void TestReturnsATwoHundredOk() {
        MockRouter router = new MockRouter(true, true);
        BuildResponse buildResponse = new BuildResponse(router);
        Request request = new Request("/", "GET", null);

        assertEquals("HTTP/1.1 200 OK\r\n\r\n", buildResponse.build(request));
    }

    @Test
    public void TestReturnsATwoHundredAndMethodsAllowed() {
        MockRouter router = new MockRouter(false, true);
        BuildResponse buildResponse = new BuildResponse(router);
        Request request = new Request("/", "OPTIONS", null);
        String expectedResponse = "HTTP/1.1 200 OK\r\nAllow: GET,POST,OPTIONS\r\n\r\n";

        assertEquals(expectedResponse, buildResponse.build(request));
    }

    @Test
    public void TestReturnsAFourOhFourIfPathDoesntExist() {
        MockRouter router = new MockRouter(false, false);
        BuildResponse buildResponse = new BuildResponse(router);
        Request request = new Request("/foobar", "OPTIONS", null);
        String expectedResponse= "HTTP/1.1 404 Not Found\r\n\r\n";

        assertEquals(expectedResponse, buildResponse.build(request));
    }

    private class MockRouter extends Router {
        private boolean routeExist;
        private boolean pathExist;

        MockRouter(boolean routeExist, boolean pathExist) {
            this.routeExist = routeExist;
            this.pathExist = pathExist;
        }

        @Override
        public boolean routeExists(String path, String httpVerb) {
            return routeExist;
        }

        @Override
        public String methodsAllowed(String path) {
            return "GET,POST,OPTIONS";
        }

        @Override
        public boolean pathExists(String path) {
            return pathExist;
        }
    }

}
