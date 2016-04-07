import Controllers.FormController;
import Routes.Route;
import Routes.Router;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class RouterTest {
    private ArrayList<Route> routes;
    private Router router;
    private Route routeGetIndex;

    @Before
    public void setup() {
        routes = new ArrayList<Route>();
        routeGetIndex = new Route("/", "GET", new FormController());
        Route routePostIndex = new Route("/", "POST", new FormController());
        routes.add(routeGetIndex);
        routes.add(routePostIndex);

        router = new Router(routes);
    }

    @Test
    public void TestIfARouteExists() {
        assertTrue(router.routeExists("/", "GET"));
    }

    @Test
    public void TestIfARouteIsNotFound() {
        assertFalse(router.routeExists("/foobar", "GET"));
    }

    @Test
    public void TestIfAPathExists() {
        assertTrue(router.pathExists("/"));
    }

    @Test
    public void TestAPathDoesNotExist() {
        assertFalse(router.pathExists("/foobar"));
    }

    @Test
    public void TestMethodsAllowedOnAPath() {
        String methodsAllowed = "GET,POST,OPTIONS";
        assertEquals(methodsAllowed, router.methodsAllowed("/"));
    }

    @Test
    public void TestCanFindARoute() {
        assertEquals(routeGetIndex, router.findRoute("/", "GET"));
    }

}
