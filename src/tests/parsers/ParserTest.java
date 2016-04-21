import parsers.Parser;
import requests.Request;
import org.junit.Test;
import org.junit.Assert;
import specialCharacters.EscapeCharacters;

import java.io.ByteArrayInputStream;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
    public void TestCanParsePathWithUnderscore() {
        String firstLineOfRequestHeader = "GEt /method_options";

        assertEquals("/method_options", Parser.parseForPathUrl(firstLineOfRequestHeader));
    }

    @Test
    public void TestCanCreateARequestObject() throws IOException {
        String requestString = "GET /logs HTTP/1.1" + EscapeCharacters.newline
                               + "Authorization: Basic YWRtaW46aHVudGVyMg==" + EscapeCharacters.newline + EscapeCharacters.newline;
        InputStream inputStream = new ByteArrayInputStream(requestString.getBytes());

        Request request = Parser.parseAndCreateRequest(inputStream, "public");

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
    public void TestCanParseForParameter() throws IOException {
        String requestHeader = "GET /parameters?name=myname" + EscapeCharacters.newline + EscapeCharacters.newline;
        InputStream stream = new ByteArrayInputStream(requestHeader.getBytes());
        Request request = Parser.parseAndCreateRequest(stream, "public");

        assertEquals("name = myname", request.getParameters());
    }

    @Test
    public void TestCanParseForMultipleParameters() throws IOException {
        String requestHeader = "GET /parameters?name=myname&city=losangeles" + EscapeCharacters.newline + EscapeCharacters.newline;
        InputStream stream = new ByteArrayInputStream(requestHeader.getBytes());
        Request request = Parser.parseAndCreateRequest(stream, "public");

        assertEquals("name = myname" + EscapeCharacters.newline + "city = losangeles", request.getParameters());
    }

    @Test
    public void TestCanParseForParametersThatArePercentEncoded() throws IOException {
        String requestHeader = "GET /parameters?variable_1=Operators%20%3C%2C&variable_2=stuff" + EscapeCharacters.newline + EscapeCharacters.newline;
        InputStream stream = new ByteArrayInputStream((requestHeader.getBytes()));
        Request request = Parser.parseAndCreateRequest(stream, "public");

        assertEquals("variable_1 = Operators <," + EscapeCharacters.newline + "variable_2 = stuff",
                      request.getParameters());

    }

}
