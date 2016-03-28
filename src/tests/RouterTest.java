import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RouterTest {

    private Map<String, ArrayList<String>> routes = new HashMap<String, ArrayList<String>>();
    private Router router;

    @Before
    public void TestSetup() {
        ArrayList<String> methods = new ArrayList<String>();
        methods.add("GET");
        routes.put("localhost:5000", methods);
        router = new Router(routes);
    }

    @Test
    public void TestUrlExistsInRoutes() {
        assert(router.requestAllowed("localhost:5000", "GET"));
    }
}
