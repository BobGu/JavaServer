import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RouterTest {

    private Map<String, ArrayList<String>> routes = new HashMap<String, ArrayList<String>>();
    private Router router;

    @Before
    public void TestSetup() {
        router = new Router();
    }

    @Test
    public void TestRootPathExists() {
        assertTrue(router.pathExists("/"));
    }

}
