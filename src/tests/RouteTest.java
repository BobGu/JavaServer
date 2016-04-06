import Mocks.MockController;
import Routes.Route;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class RouteTest {
    private Route route;

    @Before
    public void CreateARoute() {
        route = new Route("/", "GET", new MockController());
    }

    @Test
    public void TestRouteCanRetrieveItsPath() {
        assertEquals("/", route.getPath());
    }

    @Test
    public void TestRouteCanGetItsHttpVerb() {
        assertEquals("GET", route.getHttpVerb());
    }

    @Test
    public void TestRouteCanGetAController() {
        assertThat(route.getController(), instanceOf(MockController.class));
    }

    @Test
    public void TestCanReturnAFullRoute() {
        assertEquals("GET /", route.toString());
    }

}
