package controllers;

import httpStatus.HttpStatus;
import readers.Reader;
import requests.Request;
import specialCharacters.EscapeCharacters;
import writers.Writer;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatchController implements Controller {
    private Reader reader;
    private Writer writer;
    private String fileLocation;

    public PatchController(Reader reader, Writer writer, String fileLocation) {
        this.reader = reader;
        this.writer = writer;
        this.fileLocation = fileLocation;
    }

    public byte[] handle(Request request) throws IOException {
        if (request.getHttpVerb().equals("PATCH")) {
            return patch(request);
        } else {
            return "".getBytes();
        }
    }

    private byte[] patch(Request request) throws IOException {
        String response = HttpStatus.NO_CONTENT.getResponseCode() + EscapeCharacters.newline;
        String eTagRequest = getEtag(request.getFullRequest());
        byte[] fileContents = reader.read(fileLocation);
        String fileEtag = "";
        try {
            fileEtag = convertToSHA1(fileContents);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if (fileEtag.equals(eTagRequest))  {
            writer.write(fileLocation, request.getParameters());
        }
        return response.getBytes();
    }

    private String convertToSHA1(byte[] message) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        return byteArrayToHex(md.digest(message));
    }

    private String getEtag(String fullRequest) {
        String[] linesOfRequest = fullRequest.split(EscapeCharacters.newline);
        String eTagLineOfRequest = linesOfRequest[1];
        Pattern pattern = Pattern.compile("If-Match: ([0-9a-z]+)");
        Matcher matcher = pattern.matcher(eTagLineOfRequest);

        matcher.find();

        return matcher.group(1);
    }

    private static String byteArrayToHex(byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }


}
