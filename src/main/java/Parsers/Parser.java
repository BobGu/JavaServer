package parsers;
import requests.Request;
import decoders.ParameterDecoder;
import specialCharacters.EscapeCharacters;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    public Request parseAndCreateRequest(InputStream inputStream) throws IOException {
        String request = parseInputStream(inputStream);
        String path = parseForPathUrl(request);
        String httpVerb = parseForHttpVerb(request);
        String parameters = parseForParameters(request);
        String authorization = parseForAuthorization(request);

        return new Request(request, path, httpVerb, parameters, authorization);
    }

    public String parseInputStream(InputStream inputStream) throws IOException {
        String parsedRequest= "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        parsedRequest += readHeadersOfRequest(reader);

        if(isBodyOfRequest(parsedRequest)) {
            parsedRequest += readBodyOfRequest(parsedRequest, reader);
        }

        return parsedRequest;
    }

    public String fileToText(InputStream inputStream) throws IOException {
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public String parseForHttpVerb(String requestHeader) {
        String[] words = requestHeader.split(" ");
        return words[0];
    }

    public String parseForPathUrl(String requestHeader) {
        Pattern pattern = Pattern.compile("(/[a-zA-Z0-9/_.-]*)");
        Matcher matcher = pattern.matcher(requestHeader);
        matcher.find();

        return matcher.group(0);
    }

    private String parseForUrlParameters(String requestHeader) {
        String parameters = "";
        String[] linesOfRequest= requestHeader.split(EscapeCharacters.newline);
        String firstLineOfRequest = linesOfRequest[0];
        parameters = formatParameters(parseForQuery(firstLineOfRequest));

        if (isEncoded(parameters)) {
            parameters = decodeParameters(parameters);
        }
        return parameters;
    }

    private String formatParameters(String parameters) {
        parameters = parameters.replaceAll("&", EscapeCharacters.newline);
        return parameters.replaceAll("=", " = ");
    }

    private String decodeParameters(String parameters) {
        String decodedParameters = "";
        ParameterDecoder decoder = new ParameterDecoder();
        String[] keyAndValues = parameters.split("\r\n");

        for(String keyAndValue: keyAndValues) {
            decodedParameters += decoder.decode(keyAndValue) + EscapeCharacters.newline;
        }

        return decodedParameters.trim();
    }


    private boolean isEncoded(String parameters) {
        return parameters.contains("%");
    }

    private String parseForQuery(String request) {
        Pattern pattern = Pattern.compile("([^?]+=[\\S]+)");
        Matcher matcher = pattern.matcher(request);

        matcher.find();

        return matcher.group(0);
    }

    private String parseForAuthorization(String request) {
        String authorization = "";

        if (request.contains("Authorization")) {
            authorization = findAuthorization(request);
        } else {
            authorization = null;
        }
        return authorization;
    }

    private String findAuthorization(String request) {
        Pattern pattern = Pattern.compile("Authorization: Basic (.+)");
        Matcher matcher = pattern.matcher(request);

        matcher.find();

        return matcher.group(1);
    }

    private String parseForParameters(String request) {
        String parameters;

        if (isBodyOfRequest(request)) {
            parameters = parseForBody(request);
        } else if (isQuery(request)) {
            parameters = parseForUrlParameters(request);
        } else {
            parameters = null;
        }
        return parameters;
    }

    private boolean isQuery(String request) {
        return request.contains("?");
    }

    private String readHeadersOfRequest(BufferedReader reader) throws IOException {
        String requestHeaders = "";

        String line;
        while (!(line = reader.readLine()).equals("")) {
            requestHeaders += line + EscapeCharacters.newline;
        }

        return requestHeaders;
    }

    private String readBodyOfRequest(String request, BufferedReader reader) throws IOException {
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

    private boolean isBodyOfRequest(String header) {
        return header.contains("Content-Length");
    }

    private String getContentLength(String header) {
        Pattern pattern = Pattern.compile("Content-Length: ([0-9]+)");
        Matcher matcher = pattern.matcher(header);
        matcher.find();
        return matcher.group(1);
    }

    private String parseForBody(String request) {
        String[] requestLines= request.split(EscapeCharacters.newline);
        return requestLines[requestLines.length - 1];
    }

}
