import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RoutesTest {

    @Test
    public void TestTheRouteExists() {
        Routes routes = new Routes();
        String httpVerb = "GET";
        String url = "localhost:5000";
        assertTrue(routes.exist(httpVerb, url));
    }

    @Test
    public void TestReturnsFalseIfRouteDoesNotExist() {
        Routes routes = new Routes();
        String httpVerb = "Fun Method";
        String url = "localhost:5000";
        assertFalse(routes.exist(httpVerb, url));
    }
}
