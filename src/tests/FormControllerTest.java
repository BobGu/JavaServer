import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
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
    public void TestPostUpdatesData() throws IOException {
        String request = "data=hello";
        String response = formController.post(request);
        String getResponse = formController.get();

        Assert.assertThat(response, containsString("HTTP/1.1 200 OK\r\n\r\n"));
        Assert.assertThat(getResponse, containsString("data=hello"));
    }

    @Test
    public void TestPostAddsDataIfKeyDoesNotExist() throws IOException {
        createFile();

        String request = "greeting=hello";
        String response = formController.post(request);
        String getResponse = formController.get();

        Assert.assertThat(formController.get(), containsString("data=form form test"));
        Assert.assertThat(getResponse, containsString("greeting=hello"));
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
}
