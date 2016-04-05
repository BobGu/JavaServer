import Mocks.MockRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
        Assert.assertThat(response, containsString("HTTP/1.1 200 OK\r\n\r\n"));
    }

    @Test
    public void TestPostAddsDate() throws IOException {
        MockRequest request = new MockRequest("/form", "POST", "data=hello");
        String response = formController.post(request);
        String getResponse = formController.get();

        Assert.assertThat(response, containsString("HTTP/1.1 200 OK\r\n\r\n"));
        Assert.assertThat(getResponse, containsString("data=hello"));
    }

    @Test
    public void TestPostAddsDataIfKeyDoesNotExist() throws IOException {
        MockRequest request = new MockRequest("/form", "POST", "greeting=hello");
        createFile();

        String response = formController.post(request);
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

        String deleteResponse = formController.delete();
        String getResponse = formController.get();

        Assert.assertThat(deleteResponse, containsString("HTTP/1.1 200 OK\r\n\r\n"));
        assertTrue(!getResponse.contains("data=form form test"));
    }

    @Test
    public void TestPutCreatesANewResourceIfOneDoesNotExist() throws IOException {
        MockRequest request = new MockRequest("/form", "PUT", "data=im a cool guy");

        String putResponse = formController.put(request);
        String getResponse = formController.get();

        Assert.assertThat(putResponse, containsString("HTTP/1.1 200 OK"));
        Assert.assertThat(getResponse, containsString("data=im a cool guy"));
    }

}
