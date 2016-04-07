import Controllers.Controller;
import Mocks.MockController;
import Requests.Request;
import Routes.Route;
import Routes.Router;
import org.junit.Test;
import responses.BuildResponse;

import java.io.IOException;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class BuildResponseTest {

    private BuildResponse buildResponseWhenRouteExists() {
        MockRouter router = new MockRouter(true, true);
        return new BuildResponse(router);
    }

    @Test
    public void TestReturnsAFourOhFour() throws IOException {
        MockRouter router = new MockRouter(false, false);
        BuildResponse buildResponse = new BuildResponse(router);
        Request request = new Request("/foobar", "GET", null);

        assertEquals("HTTP/1.1 404 Not Found\r\n\r\n", buildResponse.build(request));
    }

    @Test
    public void TestReturnsATwoHundredOk() throws IOException {
        Request request = new Request("/", "GET", null);
        BuildResponse buildResponse = buildResponseWhenRouteExists();
        assertEquals("HTTP/1.1 200 OK\r\n\r\nHello World!", buildResponse.build(request));
    }

    @Test
    public void TestReturnsATwoHundredAndMethodsAllowed() throws IOException {
        MockRouter router = new MockRouter(false, true);
        BuildResponse buildResponse = new BuildResponse(router);
        Request request = new Request("/", "OPTIONS", null);
        String expectedResponse = "HTTP/1.1 200 OK\r\nAllow: GET,POST,OPTIONS\r\n\r\n";

        assertEquals(expectedResponse, buildResponse.build(request));
    }

    @Test
    public void TestReturnsAFourOhFourIfPathDoesntExist() throws IOException {
        MockRouter router = new MockRouter(false, false);
        BuildResponse buildResponse = new BuildResponse(router);
        Request request = new Request("/foobar", "OPTIONS", null);
        String expectedResponse = "HTTP/1.1 404 Not Found\r\n\r\n";

        assertEquals(expectedResponse, buildResponse.build(request));
    }

    @Test
    public void TestReturnsAResponseBody() throws IOException {
        BuildResponse buildResponse = buildResponseWhenRouteExists();
        Request request = new Request("/", "GET", null);

        assertThat(buildResponse.build(request), containsString("Hello World!"));
    }

    @Test
    public void TestReturnsCorrectResponseBody() throws IOException {
        MockController controller = new MockController();
        MockRouter router = new MockRouter(true, true, controller);
        BuildResponse buildResponse = new BuildResponse(router);
        Request postRequest = new Request("/", "POST", "New Body");

        buildResponse.build(postRequest);

        assertTrue(controller.isPostInvoked());
    }


    private class MockRouter extends Router {
        private boolean routeExist;
        private boolean pathExist;
        private Controller controller;

        MockRouter(boolean routeExist, boolean pathExist) {
            this(routeExist, pathExist, new MockController());
        }

        MockRouter(boolean routeExist, boolean pathExist, Controller controller) {
            this.routeExist = routeExist;
            this.pathExist = pathExist;
            this.controller = controller;
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

        @Override
        public Route findRoute(String path, String httpVerb) {
            return new Route(path, httpVerb, controller);
        }
    }

}
