import Requests.Request;
import Routes.Router;
import org.junit.Test;
import responses.BuildResponse;

import static org.junit.Assert.assertEquals;

public class BuildResponseTest {

    @Test
    public void TestReturnsAFourOhFour() {
        MockRouter router = new MockRouter(false);
        BuildResponse buildResponse = new BuildResponse(router);
        Request request = new Request("/", "GET", null);
        assertEquals("HTTP/1.1 404 Not Found\r\n\r\n", buildResponse.build(request));
    }

    private class MockRouter extends Router {
        private boolean routeExist;

        MockRouter(boolean routeExist) {
            this.routeExist = routeExist;
        }

        @Override
        public boolean routeExists(String path, String httpVerb) {
            return routeExist;
        }
    }
}
