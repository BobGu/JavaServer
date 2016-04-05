import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;

public class FormControllerTest {
    private FormController formController;

    @Before
    public void TestCreateFormController() {
        String path = "src/tests/TestFiles/fakeform.txt";
        formController = new FormController(path);
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


}
