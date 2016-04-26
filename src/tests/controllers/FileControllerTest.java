import controllers.FileController;
import httpStatus.HttpStatus;
import org.junit.Test;
import readers.FileReader;
import requests.Request;
import specialCharacters.EscapeCharacters;

import java.io.IOException;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class FileControllerTest {
    private MockFileReader reader = new MockFileReader();
    private FileController controller = new FileController(reader, "public");
    private String requestForPartialContent = "GET /partial_content.txt HTTP/1.1"
                                            + EscapeCharacters.newline
                                            + "Range: bytes=0-6"
                                            + EscapeCharacters.newline;
    private Request partialContentRequest = new Request(requestForPartialContent, "/partial-content.txt", "GET", null, null);

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

    @Test
    public void ReceivesPartialResponseHeader() throws IOException {
        byte[] response = controller.handle(partialContentRequest);
        String responseString = new String(response);

        assertTrue(responseString.contains(HttpStatus.partialContent+ EscapeCharacters.newline));
    }

    @Test
    public void ReceivesContentRange() throws IOException {
        byte[] response = controller.handle(partialContentRequest);
        String responseString = new String(response);

        assertTrue(responseString.contains("Content-Range: bytes 0-6/17"));
    }

    @Test
    public void ReceivesContentLength() throws IOException {
        byte[] response = controller.handle(partialContentRequest);
        String responseString = new String(response);

        assertTrue(responseString.contains("Content-Length: 7"));
    }

    @Test
    public void PartialResponseHasContentType() throws IOException {
        byte[] response = controller.handle(partialContentRequest);
        String responseString = new String(response);

        assertTrue(responseString.contains("Content-Type: text/html"));
    }

    @Test
    public void HandlesRequestForPartialContent() throws IOException {
        byte[] response = controller.handle(partialContentRequest);
        String responseBody = bodyOfResponse(response);

        byte[] fullContent = "reading from file".getBytes();
        byte[] partialContent = Arrays.copyOfRange(fullContent, 0, 7);
        String expectedContent = new String(partialContent);

        assertEquals(expectedContent, responseBody);
    }

    @Test
    public void HandlesRequestForPartialContentNoStartingRange() throws IOException {
        String requestNoStartRange = "GET /partial_content.txt HTTP/1.1"
                + EscapeCharacters.newline
                + "Range: bytes=-8"
                + EscapeCharacters.newline;
        Request request = new Request(requestNoStartRange, "/partial_content.txt", "GET", null, null);
        byte[] response = controller.handle(request);
        String responseBody = bodyOfResponse(response);

        byte[] fullContent = "reading from file".getBytes();
        byte[] partialContent = Arrays.copyOfRange(fullContent, 9, 17);
        String expectedContent = new String(partialContent);

        assertEquals(expectedContent, responseBody);
    }

    @Test
    public void HandlesRequestForPartialRequestNoEndRange() throws IOException {
        String requestNoEndRange = "GET /partial_content.txt HTTP/1.1"
                + EscapeCharacters.newline
                + "Range: bytes=14-"
                + EscapeCharacters.newline;
        Request request = new Request(requestNoEndRange, "/partial_content.txt", "GET", null, null);
        byte[] response = controller.handle(request);
        String responseBody = bodyOfResponse(response);

        byte[] fullContent = "reading from file".getBytes();
        byte[] partialContent = Arrays.copyOfRange(fullContent, 14, 17);
        String expectedContent = new String(partialContent);

        assertEquals(expectedContent, responseBody);
    }


    private String bodyOfResponse(byte[] response) {
        String responseString = new String(response);
        String[] responseLines = responseString.split(EscapeCharacters.newline);
        return responseLines[responseLines.length -1];
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
