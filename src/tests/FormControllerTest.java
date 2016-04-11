import Controllers.FormController;
import Mocks.MockRequest;
import httpStatus.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import specialCharacters.EscapeCharacters;

import java.io.FileWriter;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
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

}
