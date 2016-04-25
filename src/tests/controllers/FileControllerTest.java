import controllers.FileController;
import httpStatus.HttpStatus;
import org.junit.Test;
import readers.FileReader;
import requests.Request;
import specialCharacters.EscapeCharacters;

import java.io.IOException;
import static org.junit.Assert.assertTrue;

public class FileControllerTest {
    private MockFileReader reader = new MockFileReader();
    private FileController controller = new FileController(reader, "public");

    @Test
    public void HandlesAGetRequest() throws IOException {
        Request request = new Request("GET /file1 HTTP/1.1", "/file1", "GET", null, null);
        byte[] response = controller.handle(request);
        String responseString = new String(response);

        assertTrue(responseString.contains(HttpStatus.okay + EscapeCharacters.newline));
    }

    @Test
    public void HandlesAnOptionsRequest() throws IOException {
        Request request = new Request("OPTONS /file1 HTTP/1.1", "/file1", "OPTIONS", null, null);
        byte[] response = controller.handle(request);
        String responseString = new String(response);

        assertTrue(responseString.contains(HttpStatus.okay + EscapeCharacters.newline));
        assertTrue(responseString.contains("Allow: GET,OPTIONS"));
    }

    @Test
    public void MethodNotAllowed() throws IOException {
        Request request = new Request("PUT /file1 HTTP/1.1", "/file1", "PUT", null, null);
        byte[] response = controller.handle(request);
        String responseString = new String(response);

        assertTrue(responseString.contains(HttpStatus.methodNotAllowed + EscapeCharacters.newline + EscapeCharacters.newline));
    }

    @Test
    public void ReaderReadsFromTheFile() throws IOException {
        Request request = new Request("GET /file1 HTTP/1.1", "/file1", "GET", null, null);
        byte[] response = controller.handle(request);

        assertTrue(reader.getIsRead());
    }

    private class MockFileReader extends FileReader {
        private boolean isRead = false;

        @Override
        public byte[] read(String location) {
            isRead = true;
            return "reading from file".getBytes();
        }

        public boolean getIsRead() {
            return isRead;
        }
    }
}
