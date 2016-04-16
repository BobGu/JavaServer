package controllers;
import requests.Request;
import httpStatus.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import specialCharacters.EscapeCharacters;

import java.io.FileWriter;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FormControllerTest {
    private FormController formController;
    private String path;
    private Request getRequest;

    @Before
    public void setup() {
        String path = "../tests/TestFiles/fakeform.txt";
        formController = new FormController(path);
        getRequest = new Request("/form", "GET", "data=yet", null);
    }

    public void createFile() throws IOException {
        FileWriter writer = new FileWriter("src/tests/TestFiles/fakeform.txt", false);
        writer.write("data=form form test");
        writer.close();
    }

    //@Test
    //public void TestPostAddsData() throws IOException {
    //    Request request = new Request("/form", "POST", "data=hello", null);
    //    formController.post(request);
    //    String getResponse = formController.get(getRequest);

    //    Assert.assertThat(getResponse, containsString("data=hello"));
    //}

    //@Test
    //public void TestPostAddsDataIfKeyDoesNotExist() throws IOException {
    //    Request request = new Request("/form", "POST", "greeting=hello", null);
    //    createFile();

    //    formController.post(request);
    //    String getResponse = formController.get(getRequest);

    //    Assert.assertThat(formController.get(getRequest), containsString("data=form form test"));
    //    Assert.assertThat(getResponse, containsString("greeting=hello"));
    //}


    //@Test
    //public void TestPutCreatesANewResourceIfOneDoesNotExist() throws IOException {
    //    Request request = new Request("/form", "PUT", "data=im a cool guy", null);

    //    formController.put(request);
    //    String getResponse = formController.get(request);

    //    Assert.assertThat(getResponse, containsString("data=im a cool guy"));
    //}

    //@Test
    //public void TestPutReturnsAOkayResponse() throws IOException {
    //    Request request = new Request("/form", "PUT", "data=im a cool guy", null);
    //    String response = formController.put(request);

    //    Assert.assertThat(response, containsString(HttpStatus.okay + EscapeCharacters.newline + EscapeCharacters.newline));
    //}

    //@Test
    //public void TestMethodIsNotAllowed() throws IOException {
    //    Request request = new Request("/form", "FAKEHTTPACTION", "data=hello", null);
    //    String response = formController.handle(request);

    //    Assert.assertThat(response, containsString(HttpStatus.methodNotAllowed));
    //}

    @Test
    public void TestHandleAGetRequest() throws IOException {
        String response = formController.handle(getRequest);
        Assert.assertThat(response,
                          containsString(HttpStatus.okay + EscapeCharacters.newline + EscapeCharacters.newline));
    }

    @Test
    public void TestHandleAPostRequest() throws IOException {
        Request request = new Request("/form", "POST", "data=shouldbeposted", null);
        String responseToPost = formController.handle(request);
        String responseToGet = formController.handle(getRequest);

        Assert.assertThat(responseToPost,
                containsString(HttpStatus.okay + EscapeCharacters.newline + EscapeCharacters.newline));
        Assert.assertThat(responseToGet, containsString("data=shouldbeposted"));

    }

    @Test
    public void TestHandleAPutRequest() throws IOException {
        Request request = new Request("/form", "PUT", "data=acoolname", null);
        String responseToPut = formController.handle(request);
        String responseToGet = formController.handle(getRequest);

        Assert.assertThat(responseToPut,
                containsString(HttpStatus.okay + EscapeCharacters.newline + EscapeCharacters.newline));
        Assert.assertThat(responseToGet, containsString("data=acoolname"));
    }

    @Test
    public void TestHandleOptionsRequest() throws IOException {
        Request request = new Request("/form", "OPTIONS", null, null);
        String response = formController.handle(request);

        Assert.assertThat(response,
                containsString(HttpStatus.okay + EscapeCharacters.newline));

        Assert.assertThat(response, containsString("Allow: GET,POST,PUT,DELETE,OPTIONS"));
    }

}
