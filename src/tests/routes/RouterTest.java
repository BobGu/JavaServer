import controllers.Controller;
import requests.Request;
import httpStatus.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import routes.Route;
import routes.Router;
import specialCharacters.EscapeCharacters;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class RouterTest {
    private ArrayList<Route> routes;
    private Router router;
    private MockController indexController;
    private MockController formController;

    public RouterTest() {
    }

    @Before
    public void setup() {
        routes = new ArrayList<Route>();
        indexController = new MockController();
        formController = new MockController();
        Route routeRoot = new Route("/", indexController);
        Route routeForm = new Route("/form", formController);

        routes.add(routeRoot);
        routes.add(routeForm);
        router = new Router();
        router.setRoutes(routes);

    }

    @Test
    public void TestItCanDirectARequestToTheCorrectController() throws IOException {
        Request request = new Request("/", "GET", null, null);
        router.direct(request);
        assertTrue(indexController.isHandleInvoked());
    }

    @Test
    public void TestItCanDirectToTheCorrectController() throws IOException {
        Request request = new Request("/form", "GET", null, null);
        router.direct(request);

        assertTrue(formController.isHandleInvoked());
        assertFalse(indexController.isHandleInvoked());
    }

    @Test
    public void TestIfRouteDoesNotExist() throws IOException {
        Request request = new Request("/foobar", "GET", null, null);
        String response = router.direct(request);

        assertEquals(HttpStatus.notFound + EscapeCharacters.newline + EscapeCharacters.newline, response);
    }

    private class MockController implements Controller {
        private boolean postInvoked = false;
        private boolean handleInvoked = false;

        public String get(Request request) {
            return "Hello World!";
        }

        public String post(Request request) {
            postInvoked = true;
            return "TRUE";
        }

        public void delete() {
        }

        public String put(Request request) { return "PUTTER";}

        public boolean isPostInvoked() {
            return postInvoked;
        }

        public boolean isHandleInvoked() {
            return handleInvoked;
        }

        public void head() {

        }

        public String handle(Request request) {
            handleInvoked = true;
            return "handling the request";
        }

    }

}
