package parsers;
import requests.Request;
import decoders.ParameterDecoder;
import specialCharacters.EscapeCharacters;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    public static Request parseAndCreateRequest(InputStream inputStream, String directoryName) throws IOException {
        String request = parseInputStream(inputStream);
        String path = parseForPathUrl(request);
        String httpVerb = parseForHttpVerb(request);
        String parameters = parseForParameters(request);
        String authorization = parseForAuthorization(request);
        boolean isFile = containsFile(path, directoryName);
        boolean isImage = containsImage(path, isFile);

        return new Request(path, httpVerb, parameters, authorization, isFile, isImage);
    }

    private static boolean containsFile(String path, String directoryName) {
        String fileName = path.replace("/", "");
        String location = "../resources/main/" + directoryName + "/" + fileName;
        File file = new File(location);
        return !file.isDirectory() && file.exists();
    }

    private static boolean containsImage(String path, boolean isFile) {
        if(!isFile) {
            return false;
        } else {
            return hasImageExtension(path);
        }
    }

    private static boolean hasImageExtension(String path) {
        return path.contains(".jpeg") || path.contains(".png") || path.contains(".gif");
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
        Pattern pattern = Pattern.compile("(/[a-zA-Z0-9/_.-]*)");
        Matcher matcher = pattern.matcher(requestHeader);
        matcher.find();

        return matcher.group(0);
    }

    private static String parseForUrlParameters(String requestHeader) {
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


    private static boolean isEncoded(String parameters) {
        return parameters.contains("%");
    }

    private static String parseForQuery(String request) {
        Pattern pattern = Pattern.compile("([^?]+=[\\S]+)");
        Matcher matcher = pattern.matcher(request);

        matcher.find();

        return matcher.group(0);
    }

    private static String parseForAuthorization(String request) {
        String authorization = "";

        if (request.contains("Authorization")) {
            authorization = findAuthorization(request);
        } else {
            authorization = null;
        }
        return authorization;
    }

    private static String findAuthorization(String request) {
        Pattern pattern = Pattern.compile("Authorization: Basic (.+)");
        Matcher matcher = pattern.matcher(request);

        matcher.find();

        return matcher.group(1);
    }

    private static String parseForParameters(String request) {
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
        String[] requestLines= request.split(EscapeCharacters.newline);
        return requestLines[requestLines.length - 1];
    }

}
