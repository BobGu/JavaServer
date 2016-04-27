package controllers;

import httpStatus.HttpStatus;
import readers.Reader;
import requests.Request;
import specialCharacters.EscapeCharacters;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PartialController implements Controller{
    private Reader reader;
    private String fileLocation;

    public PartialController(Reader reader, String fileLocation) {
        this.reader = reader;
        this.fileLocation = fileLocation;
    }

    public byte[] handle(Request request) throws IOException {
        String fullRequest = request.getFullRequest();
        String[] linesOfRequest = fullRequest.split(EscapeCharacters.newline);
        String rangeRequest = linesOfRequest[1];

        Pattern startOfRangePattern = Pattern.compile("([0-9]+)-");
        Pattern endOfRangePattern = Pattern.compile("-([0-9]+)");
        String startOfRange = findRange(rangeRequest, startOfRangePattern);
        String endOfRange = findRange(rangeRequest, endOfRangePattern);
        byte[] fullContent = reader.read(fileLocation);

        Map<String,String> rangeValues = findRange(startOfRange, endOfRange, fullContent.length);
        int start = Integer.parseInt(rangeValues.get("start"));
        int end = Integer.parseInt(rangeValues.get("end"));
        byte[] responseBody = Arrays.copyOfRange(fullContent, start, end);
        byte[] responseHeader = partialResponseHeader(start, end, fullContent.length);

        byte[] response = addResponseHeaderAndBody(responseHeader, responseBody);
        return response;
    }

    private byte[] addResponseHeaderAndBody(byte[] responseHeader, byte[] responseBody) {
        byte[] response = new byte[responseHeader.length + responseBody.length];
        System.arraycopy(responseHeader, 0, response, 0, responseHeader.length);
        System.arraycopy(responseBody, 0, response, responseHeader.length, responseBody.length);
        return response;
    }

    private byte[] partialResponseHeader(int start, int end, int fileLength) {
        String responseHeaderString = "";
        responseHeaderString += HttpStatus.PARTIAL_CONTENT.getResponseCode() + EscapeCharacters.newline;
        responseHeaderString += "Content-Range: bytes " + String.valueOf(start) + "-" + String.valueOf(end - 1) + "/" + fileLength + EscapeCharacters.newline;
        responseHeaderString += "Content-Length: " + String.valueOf(end - start) + EscapeCharacters.newline;
        responseHeaderString += "Content-Type: " + determineContentType(fileLocation) + EscapeCharacters.newline + EscapeCharacters.newline;
        return responseHeaderString.getBytes();
    }

    private Map<String,String> findRange(String startOfRange, String endOfRange, int lengthOfFileContent) {
        Map<String,String> rangeValues = new HashMap<String,String>();
        int start;
        int end;

        if (startOfRange.equals("")) {
            start = lengthOfFileContent - Integer.parseInt(endOfRange);
            end = lengthOfFileContent;
        } else if(endOfRange.equals("")) {
            start = Integer.parseInt(startOfRange);
            end = lengthOfFileContent;
        } else {
            start = Integer.parseInt(startOfRange);
            end = Integer.parseInt(endOfRange) + 1;
        }
        rangeValues.put("start", String.valueOf(start));
        rangeValues.put("end", String.valueOf(end));

        return rangeValues;
    }

    private String findRange(String rangeRequest, Pattern pattern) {
        Matcher matcher = pattern.matcher(rangeRequest);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    private String determineContentType(String resourcePath) {
        String contentType = "";

        if (resourcePath.contains(".gif")) {
            contentType = "image/gif";
        } else if (resourcePath.contains(".jpeg") || resourcePath.contains(".jpg")) {
            contentType = "image/jpeg";
        } else if (resourcePath.contains(".png")) {
            contentType = "image/png";
        } else {
            contentType = "text/html";
        }
        return contentType;
    }

}
