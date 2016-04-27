import controllers.FormController;
import readers.Reader;
import requests.Request;
import httpStatus.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import specialCharacters.EscapeCharacters;
import writers.Writer;

import java.io.FileWriter;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.containsString;

public class FormControllerTest {
    private FormController formController;
    private String path;
    private Request getRequest;
    private MockWriter writer = new MockWriter();
    private MockReader reader = new MockReader();

    @Before
    public void setup() {
        path = "../tests/TestFiles/fakeform.txt";
        formController = new FormController(path, writer, reader);
        getRequest = new Request("GET /form HTTP/1.1", "/form", "GET", "data=yet", null);
    }

    @Test
    public void TestHandleAGetRequest() throws IOException {
        byte[] response= formController.handle(getRequest);
        String responseString = new String(response);

        Assert.assertThat(responseString,
                          containsString(HttpStatus.OKAY.getResponseCode() + EscapeCharacters.newline + EscapeCharacters.newline));
        Assert.assertThat(responseString, containsString("I'm reading from this " + path));
    }

    @Test
    public void TestHandleAPostRequest() throws IOException {
        Request request = new Request("POST /form HTTP/1.1", "/form", "POST", "data=shouldbeposted", null);
        byte[] responseToPost= formController.handle(request);
        String responseToPostString = new String(responseToPost);

        Assert.assertThat(responseToPostString,
                containsString(HttpStatus.OKAY.getResponseCode() + EscapeCharacters.newline + EscapeCharacters.newline));
        Assert.assertThat(writer.getText(), containsString("data=shouldbeposted"));

    }

    @Test
    public void TestHandleAPutRequest() throws IOException {
        Request request = new Request("PUT /form HTTP/1.1", "/form", "PUT", "data=acoolname", null);
        byte[] responseToPut = formController.handle(request);
        String responseToPutString = new String(responseToPut);

        Assert.assertThat(responseToPutString,
                containsString(HttpStatus.OKAY.getResponseCode() + EscapeCharacters.newline + EscapeCharacters.newline));
        Assert.assertThat(writer.getText(), containsString("data=acoolname"));
    }

    @Test
    public void TestHandleOptionsRequest() throws IOException {
        Request request = new Request("OPTIONS /form HTTP/1.1", "/form", "OPTIONS", null, null);
        byte[] response = formController.handle(request);
        String responseString = new String(response);

        Assert.assertThat(responseString,
                containsString(HttpStatus.OKAY.getResponseCode() + EscapeCharacters.newline));

        Assert.assertThat(responseString, containsString("Allow: GET,POST,PUT,DELETE,OPTIONS"));
    }

    @Test
    public void TestHandleDeleteRequest() throws IOException {
        Request request = new Request("GET /DELETE HTTP/1.1", "/form", "DELETE", null, null);
        byte[] response = formController.handle(request);
        String responseString = new String(response);

        Assert.assertThat(responseString,
                containsString(HttpStatus.OKAY.getResponseCode() + EscapeCharacters.newline));
        assertEquals(writer.getText(), "");
    }

    private class MockWriter implements Writer {
        private String text= "";

        public void write(String location, String textToWrite) {
            text += textToWrite;
        }

        public void update(String location, String textToWrite) {
            text += textToWrite;
        }

        public void delete(String location) {
            text = "";
        }

        public String getText() {
            return text;
        }
    }

    private class MockReader implements Reader {

        public byte[] read(String location) {
            return ("I'm reading from this " + location).getBytes();
        }
    }
}
