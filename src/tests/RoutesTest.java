import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class RoutesTest {

    @Test
    public void TestTheRouteExists() {
        Routes routes = new Routes();
        String httpVerb = "GET";
        String url = "localhost:5000";
        assertTrue(routes.exist(httpVerb, url));
    }
}
