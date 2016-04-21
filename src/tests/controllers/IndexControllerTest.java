import controllers.IndexController;
import httpStatus.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import requests.Request;
import resourceCRUD.ResourceCRUD;
import specialCharacters.EscapeCharacters;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class IndexControllerTest {
    private MockDirectoryCRUD resourceCRUD = new MockDirectoryCRUD();
    private IndexController controller;
    private Request getRequest = new Request("/", "GET", null, null, false);

    @Before
    public void setup() {
        controller = new IndexController("public", resourceCRUD);
    }

    @Test
    public void ItHasAFileDirectory() throws IOException {
        controller.handle(getRequest);

        assertTrue(resourceCRUD.getIsDirectoryCreated());
    }

    @Test
    public void TwoHundredOkayForAGetRequest() throws IOException {
        String response = controller.handle(getRequest);

        assertTrue(response.contains(HttpStatus.okay + EscapeCharacters.newline));
    }

    @Test
    public void ResponseHasContentType() throws IOException {
        String response = controller.handle(getRequest);

        assertTrue(response.contains("Content-Type: text/html;"));
    }

    @Test
    public void MethodNotAllowed() throws IOException {
        Request request = new Request("/", "POST", null, null, false);
        String response = controller.handle(request);

        assertTrue(response.contains(HttpStatus.methodNotAllowed + EscapeCharacters.newline + EscapeCharacters.newline));
    }

    @Test
    public void HandlingAnOptionsRequest() throws IOException {
        Request request = new Request("/", "OPTIONS", null, null, false);
        String response = controller.handle(request);

        assertTrue(response.contains("Allow: GET,OPTIONS"));
    }

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
