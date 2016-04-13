package Parsers;
import Requests.Request;
import decoders.ParameterDecoder;
import specialCharacters.EscapeCharacters;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    public static Request parseAndCreateRequest(InputStream inputStream) throws IOException {
        String request = parseInputStream(inputStream);
        Map<String,String> fields = parseRequest(request);

        return new Request(fields.get("path"), fields.get("httpVerb"), fields.get("parameters"), fields.get("authorization"));
    }


    public static String parseInputStream(InputStream inputStream) throws IOException {
        String parsedRequest= "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        parsedRequest += readHeadersOfRequest(reader);

        if(isBodyOfRequest(parsedRequest)) {
            parsedRequest += readBodyOfRequest(parsedRequest, reader);
        }

        return parsedRequest;
    }

    public static String fileToText(InputStream inputStream) throws IOException {
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static String parseForHttpVerb(String requestHeader) {
        String[] words = requestHeader.split(" ");
        return words[0];
    }

    public static String parseForPathUrl(String requestHeader) {
        Pattern pattern = Pattern.compile("(/[a-zA-Z0-9]*)");
        Matcher matcher = pattern.matcher(requestHeader);
        matcher.find();

        return matcher.group(0);
    }

    public static String parseForParameters(String requestHeader) {
        String parameters = "";
        String[] linesOfRequest= requestHeader.split(EscapeCharacters.newline);
        String firstLineOfRequest = linesOfRequest[0];
        parameters = formatParameters(parseForQuery(firstLineOfRequest));

        if (isEncoded(parameters)) {
            parameters = decodeParameters(parameters);
        }
        return parameters;
    }

    private static String formatParameters(String parameters) {
        parameters = parameters.replaceAll("&", EscapeCharacters.newline);
        return parameters.replaceAll("=", " = ");
    }

    private static String decodeParameters(String parameters) {
        String decodedParameters = "";
        ParameterDecoder decoder = new ParameterDecoder();
        String[] keyAndValues = parameters.split("\r\n");

        for(String keyAndValue: keyAndValues) {
            decodedParameters += decoder.decode(keyAndValue) + EscapeCharacters.newline;
        }

        return decodedParameters.trim();
    }

    private static String parseForAuthorization(String request) {
        Pattern pattern = Pattern.compile("Authorization: Basic (.+)");
        Matcher matcher = pattern.matcher(request);

        matcher.find();

        return matcher.group(0);
    }

    private static boolean isEncoded(String parameters) {
        return parameters.contains("%");
    }

    private static String parseForQuery(String request) {
        Pattern pattern = Pattern.compile("([^?]+=[\\S]+)");
        Matcher matcher = pattern.matcher(request);

        matcher.find();

        return matcher.group(0);
    }

    private static Map<String, String> parseRequest(String request) {
        Map<String, String> fields = new HashMap<String,String>();
        String parameters;

        String path = parseForPathUrl(request);
        String httpVerb = parseForHttpVerb(request);
        if (isBodyOfRequest(request)) {
            parameters = parseForBody(request);
        } else if(isQuery(request)) {
            parameters = parseForParameters(request);
        } else {
            parameters = null;
        }
        String authorization = parseForAuthorization(request);

        fields.put("path", path);
        fields.put("httpVerb", httpVerb);
        fields.put("parameters", parameters);
        fields.put("authorization", authorization);

        return fields;
    }

    private static boolean isQuery(String request) {
        return request.contains("?");
    }

    private static String readHeadersOfRequest(BufferedReader reader) throws IOException {
        String requestHeaders = "";

        String line;
        while (!(line = reader.readLine()).equals("")) {
            requestHeaders += line + EscapeCharacters.newline;
        }

        return requestHeaders;
    }

    private static String readBodyOfRequest(String request, BufferedReader reader) throws IOException {
        String requestBody = "";
        String contentLength = getContentLength(request);
        int lettersToRead = Integer.parseInt(contentLength);
        int c;

        for (int i = 0; i < lettersToRead; i++) {
            c = reader.read();
            requestBody += (char) c;
        }

        return requestBody;
    }

    private static boolean isBodyOfRequest(String header) {
        return header.contains("Content-Length");
    }

    private static String getContentLength(String header) {
        Pattern pattern = Pattern.compile("Content-Length: ([0-9]+)");
        Matcher matcher = pattern.matcher(header);
        matcher.find();
        return matcher.group(1);
    }

    private static String parseForBody(String request) {
        String[] requestWords = request.split(" ");
        return requestWords[requestWords.length - 1];
    }

}
