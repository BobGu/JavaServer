import controllers.FileController;
import controllers.PatchController;
import httpStatus.HttpStatus;
import org.junit.Test;
import requests.Request;
import specialCharacters.EscapeCharacters;
import writers.FileWriter;
import readers.FileReader;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertTrue;

public class PatchControllerTest {
    private MockFileReader reader = new MockFileReader();
    private MockWriter writer = new MockWriter(reader);
    private PatchController controller = new PatchController(reader, writer, "public/file1");
    private FileController fileController = new FileController(reader, "public/file1");
    private String fullRequest = "PATCH /patch-content.txt HTTP/1.1"
                               + EscapeCharacters.newline
                               + "If-Match: 1387ee43ab23c285a1bf455bc72081429106ac2a"
                               + EscapeCharacters.newline
                               + EscapeCharacters.newline
                               + "Brand new material!";

    @Test
    public void HandlesAPatchRequest() throws IOException {
        Request request = new Request(fullRequest, "/file1", "PATCH", null, null);
        byte[] response = controller.handle(request);
        String responseString = new String(response);

        assertTrue(responseString.contains(HttpStatus.NO_CONTENT.getResponseCode() + EscapeCharacters.newline));
    }

    @Test
    public void HandlesAPatchRequestWithMatchingEtag() throws IOException, NoSuchAlgorithmException {
        Request request = new Request(fullRequest, "/file1", "PATCH", "Brand new material!", null);
        controller.handle(request);

        Request getRequest = new Request("GET /file1 HTTP/1.1", "/file1", "GET", null, null);
        byte[] getResponse = fileController.handle(getRequest);
        String getResponseString = new String(getResponse);

        assertTrue(getResponseString.contains("Brand new material!"));
    }

    private class MockFileReader extends FileReader {
        private String text = "default text";

        @Override
        public byte[] read(String location) {
            return text.getBytes();
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
