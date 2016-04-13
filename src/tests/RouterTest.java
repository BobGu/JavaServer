import Controllers.Controller;
import Controllers.FormController;
import Controllers.IndexController;
import Mocks.MockController;
import Requests.Request;
import Routes.Route;
import Routes.Router;
import httpStatus.HttpStatus;
import org.junit.Before;
import org.junit.Test;
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

        router = new Router(routes);
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
}
