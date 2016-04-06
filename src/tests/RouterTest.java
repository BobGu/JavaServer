import Controllers.FormController;
import Routes.Route;
import Routes.Router;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RouterTest {
    private ArrayList<Route> routes;
    private Router router;

    @Before
    public void setup() {
        routes = new ArrayList<Route>();
        Route route = new Route("/", "GET", new FormController());
        routes.add(route);

        router = new Router(routes);
    }


    @Test
    public void testIfARouteExists() {
        assertTrue(router.routeExists("/", "GET"));
    }

    @Test
    public void testIfARouteIsNotFound() {
        assertFalse(router.routeExists("/foobar", "GET"));
    }


}
