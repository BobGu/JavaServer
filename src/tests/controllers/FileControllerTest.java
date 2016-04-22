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
    private FileController controller = new FileController(reader);

    @Test
    public void HandlesAGetRequest() throws IOException {
        Request request = new Request("/file1", "GET", null, null, true, false);
        String response = controller.handle(request);

        assertTrue(response.contains(HttpStatus.okay + EscapeCharacters.newline + EscapeCharacters.newline));
    }

    @Test
    public void HandlesAnOptionsRequest() throws IOException {
        Request request = new Request("/file1", "OPTIONS", null, null, true, false);
        String response = controller.handle(request);

        assertTrue(response.contains(HttpStatus.okay + EscapeCharacters.newline));
        assertTrue(response.contains("Allow: GET,OPTIONS"));
    }

    @Test
    public void MethodNotAllowed() throws IOException {
        Request request = new Request("/file1", "PUT", null, null, true, false);
        String response = controller.handle(request);

        assertTrue(response.contains(HttpStatus.methodNotAllowed + EscapeCharacters.newline + EscapeCharacters.newline));
    }

    @Test
    public void ReaderReadsFromTheFile() throws IOException {
        Request request = new Request("/file1", "GET", null, null, true, false);
        String response = controller.handle(request);

        assertTrue(reader.getIsRead());
    }

    private class MockFileReader extends FileReader {
        private boolean isRead = false;

        @Override
        public String read(String location) {
            isRead = true;
            return "reading from file";
        }

        public boolean getIsRead() {
            return isRead;
        }
    }
}
