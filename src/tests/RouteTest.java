import org.junit.Test;
import static junit.framework.TestCase.assertEquals;

public class RouteTest {

    @Test
    public void TestRouteHasHTTPVerbAndUrl() {
        Route route = new Route("GET", "localhost:5000");
        assertEquals("GET", route.getHttpVerb());
        assertEquals("localhost:5000", route.getUrl());
    }

}
