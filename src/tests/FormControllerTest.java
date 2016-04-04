import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;

public class FormControllerTest {
    private FormController formController;

    @Before
    public void TestCreateFormController() {
        formController = new FormController();
    }

    @Test
    public void TestCorrectResponseForGet() throws IOException {
        String response = formController.get();
        Assert.assertThat(response, containsString("HTTP/1.1 200 OK\r\n\r\n"));
    }
}
