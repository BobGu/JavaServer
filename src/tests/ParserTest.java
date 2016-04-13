import Parsers.Parser;
import Requests.Request;
import org.junit.Test;
import org.junit.Assert;
import specialCharacters.EscapeCharacters;

import javax.lang.model.util.ElementScanner6;
import java.io.ByteArrayInputStream;
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.containsString;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ParserTest {

    @Test
    public void TestCanParseRequest() throws IOException {
        String request= "GET /logs HTTP/1.1\r\nHost: localhost:5000\r\nConnection: Keep-Alive\r\nUser-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\nAccept-Encoding: gzip,deflate\r\n\r\n";
        InputStream inputStream = new ByteArrayInputStream(request.getBytes());

        String parsedRequest = Parser.parseInputStream(inputStream);

        Assert.assertThat(parsedRequest, containsString("Host: localhost:5000"));
    }

    @Test
    public void TestCanParseRequestWithABody() throws IOException {
        String request = "POST /foobar HTTP/1.1 Host: localhost:5000/form Connection: Keep-Alive Content-Length: 10\r\n\r\nname=Johns";
        InputStream inputStream = new ByteArrayInputStream(request.getBytes());

        String parsedRequest = Parser.parseInputStream(inputStream);

        Assert.assertThat(parsedRequest, containsString("name=Johns"));
    }

    @Test
    public void TestCanParseForHTTPVerb() {
        String firstLineOfRequestHeader = "GET /logs HTTP/1.1";
        assertEquals("GET",  Parser.parseForHttpVerb(firstLineOfRequestHeader));
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

    @Test
    public void TestCanCreateARequestObject() throws IOException {
        String requestString = "GET /logs HTTP/1.1\r\n"
                               + "Authorization: Basic YWRtaW46aHVudGVyMg==\r\n"
                               + "Host: localhost:5000\r\n"
                               + "Connection: Keep-Alive\r\n"
                               + "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\n"
                               + "Accept-Encoding: gzip,deflate\r\n\r\n";
        InputStream inputStream = new ByteArrayInputStream(requestString.getBytes());

        Request request = Parser.parseAndCreateRequest(inputStream);

        assertEquals("/logs", request.getPath());
        assertEquals( "GET", request.getHttpVerb());
        assertEquals("YWRtaW46aHVudGVyMg==", request.getAuthorization());
        assertEquals(null, request.getParameters());
    }

    @Test
    public void TestCanParseForPath() {
        String request= "GET /parameters?name=boboblaw";
        assertEquals("/parameters", Parser.parseForPathUrl(request));
    }

    @Test
    public void TestCanParseForParameter() {
        String request = "GET /parameters?name=myname";
        assertEquals("name = myname", Parser.parseForParameters(request));
    }

    @Test
    public void TestCanParseForMultipleParameters() {
        String request = "GET /parameters?name=myname&city=losangeles";
        assertEquals("name = myname" + EscapeCharacters.newline + "city = losangeles",
                     Parser.parseForParameters(request));
    }

    @Test
    public void TestCanParseForParametersThatArePercentEncoded() {
        String request = "GET /parameters?variable_1=Operators%20%3C%2C&variable_2=stuff";
        assertEquals("variable_1 = Operators <," + EscapeCharacters.newline + "variable_2 = stuff",
                     Parser.parseForParameters(request));
    }

}
