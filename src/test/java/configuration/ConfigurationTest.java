import configuration.Configuration;
import org.junit.Before;
import org.junit.Test;
import requests.Request;
import routes.Route;
import routes.Router;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

public class ConfigurationTest {
    private Configuration config;
    @Before
    public void setUp() {
        config = new Configuration(new MockRouter());
    }

    @Test
    public void setsPortCorrectly() {
        config.setPort(4000);
        assertEquals(4000, config.getPort());
    }

    private class MockRouter implements Router {
        public void setRoutes(ArrayList<Route> routes)  {
        }

        public void setDirectoryLocation(String location) {
        }

        public byte[] direct(Request request) {
            return new byte[4];
        }

        public boolean isRoutes() {
            return true;
        }

    }

}
