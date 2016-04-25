import controllers.IndexController;
import httpStatus.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import requests.Request;
import specialCharacters.EscapeCharacters;
import readers.Reader;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class IndexControllerTest {
    private MockReader reader = new MockReader();
    private IndexController controller;
    private Request getRequest = new Request("/", "GET", null, null, false, false);

    @Before
    public void setup() {
        controller = new IndexController("public", reader);
    }

    @Test
    public void ItHasAFileDirectory() throws IOException {
        controller.handle(getRequest);

        assertTrue(reader.getIsDirectoryCreated());
    }

    @Test
    public void TwoHundredOkayForAGetRequest() throws IOException {
        byte[] response = controller.handle(getRequest);
        String responseString = new String(response);

        assertTrue(responseString.contains(HttpStatus.okay + EscapeCharacters.newline));
    }

    @Test
    public void ResponseHasContentType() throws IOException {
        byte[] response = controller.handle(getRequest);
        String responseString = new String(response);

        assertTrue(responseString.contains("Content-Type: text/html;"));
    }

    @Test
    public void MethodNotAllowed() throws IOException {
        Request request = new Request("/", "POST", null, null, false, false);
        byte[] response = controller.handle(request);
        String responseString = new String(response);

        assertTrue(responseString.contains(HttpStatus.methodNotAllowed + EscapeCharacters.newline + EscapeCharacters.newline));
    }

    @Test
    public void HandlingAnOptionsRequest() throws IOException {
        Request request = new Request("/", "OPTIONS", null, null, false, false);
        byte[] response = controller.handle(request);
        String responseString = new String(response);

        assertTrue(responseString.contains("Allow: GET,OPTIONS"));
    }

    private class MockReader implements Reader{
        private boolean isDirectoryCreated;

        public byte[] read(String dirName) {
            createDirectory();
            return "File contents".getBytes();
        }

        public boolean getIsDirectoryCreated() {
            return isDirectoryCreated;
        }

        private void createDirectory() {
            isDirectoryCreated = true;
        }
    }
}
