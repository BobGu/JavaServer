import parsers.Parser;
import requests.Request;
import org.junit.Test;
import org.junit.Assert;
import specialCharacters.EscapeCharacters;
import java.io.ByteArrayInputStream;
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.containsString;
import java.io.IOException;
import java.io.InputStream;

public class ParserTest {
    private Parser parser = new Parser();

    @Test
    public void CanParseRequest() throws IOException {
        String request= "GET /logs HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n\r\n";
        InputStream inputStream = new ByteArrayInputStream(request.getBytes());

        String parsedRequest = parser.parseInputStream(inputStream);

        Assert.assertThat(parsedRequest, containsString("Host: localhost:5000"));
    }

    @Test
    public void CanParseRequestWithABody() throws IOException {
        String request = "POST /foobar HTTP/1.1 Host: localhost:5000/form Connection: Keep-Alive Content-Length: 10\r\n\r\nname=Johns";
        InputStream inputStream = new ByteArrayInputStream(request.getBytes());

        String parsedRequest = parser.parseInputStream(inputStream);

        Assert.assertThat(parsedRequest, containsString("name=Johns"));
    }

    @Test
    public void CanParseForHTTPVerb() {
        String firstLineOfRequestHeader = "GET /logs HTTP/1.1";

        assertEquals("GET",  parser.parseForHttpVerb(firstLineOfRequestHeader));
    }

    @Test
    public void CanParseForPathOfUrl() {
        String  firstLineOfRequestHeader = "GET /foobar HTTP/1.1";

        assertEquals("/foobar", parser.parseForPathUrl(firstLineOfRequestHeader));
    }

    @Test
    public void CanParseForMoreComplexPath() {
        String firstLineOfRequestHeader = "GET /foobar/items/1";

        assertEquals("/foobar/items/1", parser.parseForPathUrl(firstLineOfRequestHeader));
    }

    @Test
    public void CanParsePathWithUnderscore() {
        String firstLineOfRequestHeader = "GEt /method_options";

        assertEquals("/method_options", parser.parseForPathUrl(firstLineOfRequestHeader));
    }

    @Test
    public void CanCreateARequestObject() throws IOException {
        String requestString = "GET /logs HTTP/1.1" + EscapeCharacters.newline
                               + "Authorization: Basic YWRtaW46aHVudGVyMg==" + EscapeCharacters.newline + EscapeCharacters.newline;
        InputStream inputStream = new ByteArrayInputStream(requestString.getBytes());

        Request request = parser.parseAndCreateRequest(inputStream, "public");

        assertEquals("/logs", request.getPath());
        assertEquals( "GET", request.getHttpVerb());
        assertEquals("YWRtaW46aHVudGVyMg==", request.getAuthorization());
        assertEquals(null, request.getParameters());
    }

    @Test
    public void CanParseForPath() {
        String request= "GET /parameters?name=boboblaw";

        assertEquals("/parameters", parser.parseForPathUrl(request));
    }

    @Test
    public void CanParseForParameter() throws IOException {
        String requestHeader = "GET /parameters?name=myname" + EscapeCharacters.newline + EscapeCharacters.newline;
        InputStream stream = new ByteArrayInputStream(requestHeader.getBytes());
        Request request = parser.parseAndCreateRequest(stream, "public");

        assertEquals("name = myname", request.getParameters());
    }

    @Test
    public void CanParseForMultipleParameters() throws IOException {
        String requestHeader = "GET /parameters?name=myname&city=losangeles" + EscapeCharacters.newline + EscapeCharacters.newline;
        InputStream stream = new ByteArrayInputStream(requestHeader.getBytes());
        Request request = parser.parseAndCreateRequest(stream, "public");

        assertEquals("name = myname" + EscapeCharacters.newline + "city = losangeles", request.getParameters());
    }

    @Test
    public void CanParseForParametersThatArePercentEncoded() throws IOException {
        String requestHeader = "GET /parameters?variable_1=Operators%20%3C%2C&variable_2=stuff" + EscapeCharacters.newline + EscapeCharacters.newline;
        InputStream stream = new ByteArrayInputStream((requestHeader.getBytes()));
        Request request = parser.parseAndCreateRequest(stream, "public");

        assertEquals("variable_1 = Operators <," + EscapeCharacters.newline + "variable_2 = stuff",
                      request.getParameters());

    }

}
