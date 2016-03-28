import org.junit.Test;
import java.io.File;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class HtmlToStringConverterTest {

    @Test
    public void TestCanConvertHtmlToString() throws IOException {
        File targetHtmlFile = new File("src/tests/fixtures/testindex.html");
        String htmlAsString = "<!DOCTYPE html>"
        + "<html lang=\"en\">"
        + "<head>"
        + "    <meta charset=\"UTF-8\">"
        + "    <title>This is a test</title>"
        + "</head>"
        + "<body>"
        + "    <h1>My incredible homepage</h1>"
        + "</body>"
        + "</html>";
        assertEquals(htmlAsString, HtmlToStringConverter.convert(targetHtmlFile));
    }

}
