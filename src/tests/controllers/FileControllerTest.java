import controllers.FileController;
import httpStatus.HttpStatus;
import org.junit.Test;
import readers.FileReader;
import requests.Request;
import specialCharacters.EscapeCharacters;
import writers.FileWriter;
import writers.Writer;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileControllerTest {
    private MockFileReader reader = new MockFileReader();
    private MockWriter writer = new MockWriter(reader);
    private FileController controller = new FileController(reader, writer, "public");

    @Test
    public void HandlesAGetRequest() throws IOException {
        Request request = new Request("GET /file1 HTTP/1.1", "/file1", "GET", null, null);
        byte[] response = controller.handle(request);
        String responseString = new String(response);

        assertTrue(responseString.contains(HttpStatus.OKAY.getResponseCode() + EscapeCharacters.newline));
    }

    @Test
    public void HandlesAnOptionsRequest() throws IOException {
        Request request = new Request("OPTONS /file1 HTTP/1.1", "/file1", "OPTIONS", null, null);
        byte[] response = controller.handle(request);
        String responseString = new String(response);

        assertTrue(responseString.contains(HttpStatus.OKAY.getResponseCode() + EscapeCharacters.newline));
        assertTrue(responseString.contains("Allow: GET,OPTIONS"));
    }

    @Test
    public void MethodNotAllowed() throws IOException {
        Request request = new Request("PUT /file1 HTTP/1.1", "/file1", "PUT", null, null);
        byte[] response = controller.handle(request);
        String responseString = new String(response);

        assertTrue(responseString.contains(HttpStatus.METHOD_NOT_ALLOWED.getResponseCode() + EscapeCharacters.newline + EscapeCharacters.newline));
    }

    @Test
    public void ReaderReadsFromTheFile() throws IOException {
        Request request = new Request("GET /file1 HTTP/1.1", "/file1", "GET", null, null);
        byte[] response = controller.handle(request);

        assertTrue(reader.getIsRead());
    }

    @Test
    public void HandlesAPatchRequest() throws IOException {
        Request request = new Request("PATCH /file1 HTTP/1.1", "/file1", "PATCH", null, null);
        byte[] response = controller.handle(request);
        String responseString = new String(response);

        assertTrue(responseString.contains(HttpStatus.NO_CONTENT.getResponseCode() + EscapeCharacters.newline));
    }

    @Test
    public void CanGetContentsOfEtag() {
        String fullRequest = "PATCH /patch-content.txt HTTP/1.1" + EscapeCharacters.newline
                           + "If-Match: 5c36acad75b78b82be6d9cbbd6143ab7e0cc04b0" + EscapeCharacters.newline;

        assertEquals("5c36acad75b78b82be6d9cbbd6143ab7e0cc04b0", controller.getEtag(fullRequest));
    }



    @Test
    public void HandlesAPatchRequestWithMatchingEtag() throws IOException, NoSuchAlgorithmException {
        String fullRequest = "PATCH /patch-content.txt HTTP/1.1"
                + EscapeCharacters.newline
                + "If-Match: 1387ee43ab23c285a1bf455bc72081429106ac2a"
                + EscapeCharacters.newline
                + EscapeCharacters.newline
                + "Brand new material!";
        Request request = new Request(fullRequest, "/file1", "PATCH", "Brand new material!", null);
        controller.handle(request);

        Request getRequest = new Request("GET /file1 HTTP/1.1", "/file1", "GET", null, null);
        byte[] getResponse = controller.handle(getRequest);
        String getResponseString = new String(getResponse);

        assertTrue(getResponseString.contains("Brand new material!"));
    }

    private class MockFileReader extends FileReader {
        private boolean isRead = false;
        private String text = "default text";

        @Override
        public byte[] read(String location) {
            isRead = true;
            return text.getBytes();
        }

        public boolean getIsRead() {
            return isRead;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    private class MockWriter extends FileWriter {
        private MockFileReader reader;

        MockWriter(MockFileReader reader) {
            this.reader = reader;
        }

        @Override
        public void write(String location, String textToWrite) {
            reader.setText(textToWrite);
        }

    }
}

