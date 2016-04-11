import Controllers.FormController;
import Mocks.MockController;
import Mocks.MockRequest;
import Requests.Request;
import httpStatus.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import specialCharacters.EscapeCharacters;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FormControllerTest {
    private FormController formController;
    private String path;

    @Before
    public void TestCreateFormController() {
        String path = "src/tests/TestFiles/fakeform.txt";
        formController = new FormController(path);
    }

    public void createFile() throws IOException {
        FileWriter writer = new FileWriter("src/tests/TestFiles/fakeform.txt", false);
        writer.write("data=form form test");
        writer.close();
    }

    @Test
    public void TestCorrectResponseForGet() throws IOException {
        String response = formController.get();
        Assert.assertThat(response,
                containsString(HttpStatus.okay + EscapeCharacters.newline + EscapeCharacters.newline));
    }

    @Test
    public void TestPostReturnsTwoHundredOkay() throws IOException {
        MockRequest request = new MockRequest("/form", "POST", "data=hello");
        String response = formController.post(request);

        Assert.assertThat(response, containsString(HttpStatus.okay + EscapeCharacters.newline + EscapeCharacters.newline));
    }

    @Test
    public void TestPostAddsData() throws IOException {
        MockRequest request = new MockRequest("/form", "POST", "data=hello");
        formController.post(request);
        String getResponse = formController.get();

        Assert.assertThat(getResponse, containsString("data=hello"));
    }

    @Test
    public void TestPostAddsDataIfKeyDoesNotExist() throws IOException {
        MockRequest request = new MockRequest("/form", "POST", "greeting=hello");
        createFile();

        formController.post(request);
        String getResponse = formController.get();

        Assert.assertThat(formController.get(), containsString("data=form form test"));
        Assert.assertThat(getResponse, containsString("greeting=hello"));
    }

    @Test
    public void TestPostReplacesDataWithSameKey() throws IOException {
        MockRequest request = new MockRequest("/form", "POST", "data=newdata");
        createFile();

        formController.post(request);
        String getResponse = formController.get();

        Assert.assertThat(getResponse, containsString("data=newdata"));
        assertTrue(!getResponse.contains("data=form form test"));
    }

    @Test
    public void TestDeleteMethodDeletesAFile() throws IOException {
        createFile();
        Assert.assertThat(formController.get(), containsString("data=form form test"));

        formController.delete();
        String getResponse = formController.get();

        assertTrue(!getResponse.contains("data=form form test"));
    }

    @Test
    public void TestPutCreatesANewResourceIfOneDoesNotExist() throws IOException {
        MockRequest request = new MockRequest("/form", "PUT", "data=im a cool guy");

        formController.put(request);
        String getResponse = formController.get();

        Assert.assertThat(getResponse, containsString("data=im a cool guy"));
    }

    @Test
    public void TestPutReturnsAOkayResponse() throws IOException {
        MockRequest request = new MockRequest("/form", "PUT", "data=im a cool guy");
        String response = formController.put(request);

        Assert.assertThat(response, containsString(HttpStatus.okay + EscapeCharacters.newline + EscapeCharacters.newline));
    }

    @Test
    public void TestMethodIsNotAllowed() throws IOException {
        MockRequest request = new MockRequest("/form", "FAKEHTTPACTION", "data=hello");
        String response = formController.handle(request);

        Assert.assertThat(response, containsString(HttpStatus.methodNotAllowed));
    }

    @Test
    public void TestHandleChoosesCorrectAction() throws IOException {
        MockRequest request = new MockRequest("/form", "GET", null);
        MockFormController controller = new MockFormController();
        controller.handle(request);

        assertTrue(controller.isGetInvoked());
    }

    @Test
    public void TestHandlePicksTheCorrectAction() throws IOException {
        MockRequest request = new MockRequest("/form", "POST", null);
        MockFormController controller = new MockFormController();
        controller.handle(request);

        assertTrue(controller.isPostInvoked());
    }

    @Test
    public void TestHandleCallsTheCorrectAction() throws IOException {
        MockRequest request = new MockRequest("/form", "PUT", null);
        MockFormController controller = new MockFormController();
        controller.handle(request);

        assertTrue(controller.isPostInvoked());
    }

    @Test
    public void TestHandleCallsTheAppropriateAction() throws IOException {
        MockRequest request = new MockRequest("/form", "OPTIONS", null);
        MockFormController controller = new MockFormController();
        controller.handle(request);

        assertTrue(controller.isOptionsInvoked());
    }

    @Test
    public void TestHandleChoosesTheCorrectAction() throws IOException {
        MockRequest request = new MockRequest("/form", "DELETE", null);
        MockFormController controller = new MockFormController();
        controller.handle(request);

        assertTrue(controller.isDeleteInvoked());
    }

    private class MockFormController extends FormController {
        boolean getInvoked = false;
        boolean postInvoked = false;
        boolean deleteInvoked = false;
        boolean optionsInvoked = false;

        public boolean isGetInvoked() {
            return getInvoked;
        }

        public boolean isPostInvoked() {
            return postInvoked;
        }

        public boolean isDeleteInvoked() {
            return deleteInvoked;
        }

        public boolean isOptionsInvoked() {
            return optionsInvoked;
        }

        @Override
        public String get() {
            getInvoked = true;
            return "get request called";
       }

        @Override
        public String post(Request request) {
            postInvoked = true;
            return "post method called";
        }

        @Override
        public String delete() {
            deleteInvoked = true;
            return "delete method called";
        }

        @Override
        public String options() {
            optionsInvoked = true;
            return "options invoked";
        }
    }

}
