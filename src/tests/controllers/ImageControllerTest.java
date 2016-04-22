import controllers.Controller;
import controllers.ImageController;
import httpStatus.HttpStatus;
import org.junit.Test;
import readers.Reader;
import requests.Request;
import specialCharacters.EscapeCharacters;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class ImageControllerTest {
    MockImageReader reader = new MockImageReader();
    Controller controller = new ImageController(reader);
    Request getRequest = new Request("/image.png", "GET", null, null, true, true);

    @Test
    public void HandlesAGetRequest() throws IOException {
        String response = controller.handle(getRequest);

        assertTrue(response.contains(HttpStatus.okay + EscapeCharacters.newline));
    }

    @Test
    public void GetRequestReturnAContentType() throws IOException {
        String response = controller.handle(getRequest);

        assertTrue(response.contains("Content-Type: text/html" + EscapeCharacters.newline + EscapeCharacters.newline));
    }
    @Test
    public void HandlesAnOptionsRequest() throws IOException {
        Request request = new Request("/image.jpg", "OPTIONS", null, null, true, true);
        String response = controller.handle(request);

        assertTrue(response.contains(HttpStatus.okay + EscapeCharacters.newline));
        assertTrue(response.contains("Allow: GET,OPTIONS"));
    }

    @Test
    public void HandlesMethodsThatAreNotAllowed() throws IOException {
        Request request = new Request("/cat.gif", "DELETE", null, null, true, true);
        String response = controller.handle(request);

        assertTrue(response.contains(HttpStatus.methodNotAllowed + EscapeCharacters.newline + EscapeCharacters.newline));
    }

    @Test
    public void ReaderReadsFromImageFile() throws IOException {
        String response = controller.handle(getRequest);
        controller.handle(getRequest);

        assertTrue(reader.getIsRead());
    }

    private class MockImageReader implements Reader {
        private boolean isRead = false;

        public boolean getIsRead() {
            return isRead;
        }

        public String read(String location) {
            isRead = true;
            return "reading the image";
        }
    }
}
