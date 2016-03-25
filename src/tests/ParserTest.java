import org.junit.Test;
import org.junit.Assert;
import java.io.ByteArrayInputStream;
import static junit.framework.TestCase.assertEquals;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ParserTest {

    @Test
    public void TestCanParseInput() throws IOException {
        String requestString = "GET /logs HTTP/1.1\nHost: localhost:5000\nConnection: Keep-Alive\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\nAccept-Encoding: gzip,deflate";
        InputStream inputStream = new ByteArrayInputStream(requestString.getBytes());

        List<String> requestHeaderLines;
        requestHeaderLines = Parser.parseInputStream(inputStream);

        assertEquals("GET /logs HTTP/1.1", requestHeaderLines.get(0));
    }

    @Test
    public void TestCanParseForHTTPVerb() {
        String firstLineOfRequestHeader = "GET /logs HTTP/1.1";
        assertEquals("GET",  Parser.parseForHttpVerb(firstLineOfRequestHeader));
    }

    @Test
    public void TestCanParseForRootUrl() {
        String secondLineOfRequestHeader = "Host: localhost:5000";
        assertEquals("localhost:5000", Parser.parseForRootUrl(secondLineOfRequestHeader));
    }

    @Test
    public void TestCanParseForPathOfUrl() {
        String  firstLineOfRequestHeader = "GET /foobar HTTP/1.1";
        assertEquals("/foobar", Parser.parseForPathUrl(firstLineOfRequestHeader));
    }

    @Test
    public void TestCanParseForMoreComplexPath() {
        String firstLineOfRequestHeader = "GET /foobar/items/1";
        assertEquals("/foobar/items/1", Parser.parseForPathUrl(firstLineOfRequestHeader));
    }

}
