package Parsers;

import Requests.Request;

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

        return new Request(fields.get("path"), fields.get("httpVerb"), fields.get("body"));
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
        Pattern pattern = Pattern.compile("(/[a-z]+)");
        Matcher matcher = pattern.matcher(requestHeader);
        matcher.find();

        return matcher.group(0);
    }

    public static List<String> parseForParameters(String requestHeader) {
        List<String> allMatches = new ArrayList<String>();
        Pattern pattern = Pattern.compile("([a-z]+=[a-z]+)");
        Matcher matcher = pattern.matcher(requestHeader);

        while(matcher.find()) {
            allMatches.add(matcher.group());
        }

        return allMatches;
    }

    private static Map<String, String> parseRequest(String request) {
        Map<String, String> fields = new HashMap<String,String>();

        String path = parseForPathUrl(request);
        String httpVerb = parseForHttpVerb(request);
        String body = isBodyOfRequest(request) ? parseForBody(request) : null;

        fields.put("path", path);
        fields.put("httpVerb", httpVerb);
        fields.put("body", body);

        return fields;
    }

    private static String readHeadersOfRequest(BufferedReader reader) throws IOException {
        String requestHeaders = "";

        String line;
        while (!(line = reader.readLine()).equals("")) {
            requestHeaders += line + " ";
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
