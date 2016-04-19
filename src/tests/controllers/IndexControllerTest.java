import controllers.IndexController;
import org.junit.Before;
import org.junit.Test;
import requests.Request;
import resourceCRUD.ResourceCRUD;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class IndexControllerTest {
    private MockDirectoryCRUD resourceCRUD = new MockDirectoryCRUD();
    private IndexController controller;

    @Before
    public void setup() {
        controller = new IndexController("public", resourceCRUD);
    }

    @Test
    public void ItHasAFileDirectory() throws IOException {
        Request request = new Request("/", "GET", null, null);
        controller.handle(request);

        assertTrue(resourceCRUD.getIsDirectoryCreated());
    }

    //@Test
    //public void TwoHundredOkayForAGetRequest() {
    //    Request request = new Request("/", "OPTIONS", null, null);
    //    controller.handle(request);
    //}

    private class MockDirectoryCRUD implements ResourceCRUD {
        private boolean isDirectoryCreated;

        public void create(String dirName, String text) {
        }

        public String read(String dirName) {
            createDirectory();
            return "File contents";
        }

        public void update(String dirName, String text) {

        }

        public void delete(String dirName){}

        public boolean getIsDirectoryCreated() {
            return isDirectoryCreated;
        }

        private void createDirectory() {
            isDirectoryCreated = true;
        }
    }
}
