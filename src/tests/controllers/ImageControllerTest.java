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
    Request getRequest = new Request("GET /image.png HTTP/1.1", "/image.png", "GET", null, null);

    @Test
    public void HandlesAGetRequest() throws IOException {
        byte[] response = controller.handle(getRequest);
        String responseString = new String(response);

        assertTrue(responseString.contains(HttpStatus.OKAY.getResponseCode() + EscapeCharacters.newline));
    }

    @Test
    public void GetRequestReturnAContentType() throws IOException {
        byte[] response = controller.handle(getRequest);
        String responseString = new String(response);

        assertTrue(responseString.contains("Content-Type: image/jpeg" +  EscapeCharacters.newline));
    }
    @Test
    public void HandlesAnOptionsRequest() throws IOException {
        Request request = new Request("GET /image.jpg HTTP/1.1", "/image.jpg", "OPTIONS", null, null);
        byte[] response = controller.handle(request);
        String responseString = new String(response);

        assertTrue(responseString.contains(HttpStatus.OKAY.getResponseCode() + EscapeCharacters.newline));
        assertTrue(responseString.contains("Allow: GET,OPTIONS"));
    }

    @Test
    public void HandlesMethodsThatAreNotAllowed() throws IOException {
        Request request = new Request("GET /cat.gif HTTP/1.1", "/cat.gif", "DELETE", null, null);
        byte[] response = controller.handle(request);
        String responseString = new String(response);

        assertTrue(responseString.contains(HttpStatus.METHOD_NOT_ALLOWED.getResponseCode() + EscapeCharacters.newline + EscapeCharacters.newline));
    }

    @Test
    public void ReaderReadsFromImageFile() throws IOException {
        byte[] response = controller.handle(getRequest);
        controller.handle(getRequest);
        String responseString = new String(response);

        assertTrue(reader.getIsRead());
    }

    private class MockImageReader implements Reader {
        private boolean isRead = false;

        public boolean getIsRead() {
            return isRead;
        }

        public byte[] read(String location) {
            isRead = true;
            return "reading the image".getBytes();
        }
    }
}
