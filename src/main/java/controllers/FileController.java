package controllers;

import httpStatus.HttpStatus;
import readers.FileReader;
import readers.Reader;
import requests.Request;
import specialCharacters.EscapeCharacters;
import writers.FileWriter;
import writers.Writer;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileController implements Controller {
    private final String METHODS_ALLOWED = "GET,OPTIONS";
    private String fileLocation;
    private Reader reader;
    private Writer writer = new FileWriter();

    public FileController(Reader reader, Writer writer, String fileLocation) {
        this.reader = reader;
        this.fileLocation = fileLocation;
        this.writer = writer;
    }

    public byte[] handle(Request request) throws IOException {
       if (request.getHttpVerb().equals("GET")) {
           return get(request);
       } else if (request.getHttpVerb().equals("OPTIONS")) {
           return options().getBytes();
       } else if (request.getHttpVerb().equals("PATCH")) {
           return patch(request);
       } else {
           return methodNotAllowed().getBytes();
       }
    }

    private byte[] get(Request request) throws IOException {
        File file = new File(fileLocation);

        if (file.isDirectory() || request.getPath().equals("/")) {
            return directoryResponse(request);
        } else if(partialContentRequested(request.getFullRequest())) {
            Controller controller = new PartialController(new FileReader(), fileLocation);
            return controller.handle(request);
        } else {
            return fileResponse();
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

    private String options() {
        return HttpStatus.OKAY.getResponseCode()
               + EscapeCharacters.newline
               + "Allow: "
               + METHODS_ALLOWED
               + EscapeCharacters.newline
               + EscapeCharacters.newline;
    }

    private byte[] directoryResponse(Request request) throws IOException {
        String response = "";
        response += HttpStatus.OKAY.getResponseCode() + EscapeCharacters.newline;
        response += "Content-Type: text/html;" + EscapeCharacters.newline + EscapeCharacters.newline;
        byte[] directoryAndFiles = reader.read(fileLocation);
        String files = new String(directoryAndFiles);

        response += "<!DOCTYPE html><html><head><title>bobsjavaserver</title></head><body>";
        if (request.getPath().equals("/")) {
            response += "<h1>Hello world</h1><ul>";
        }
        response += format(files, request) + "</ul>";
        response += "</body></html>";
        return response.getBytes();
    }

    private byte[] fileResponse() throws IOException {
        String contentType = determineContentType(fileLocation);
        String responseHeadersString = "";

        responseHeadersString += HttpStatus.OKAY.getResponseCode() + EscapeCharacters.newline;
        responseHeadersString += "Content-Type: " + contentType + EscapeCharacters.newline;
        byte[] responseBody = reader.read(fileLocation);
        responseHeadersString += "Content-Length: " + responseBody.length + EscapeCharacters.newline + EscapeCharacters.newline;
        byte[] responseHeader = responseHeadersString.getBytes();

        byte[] response = addResponseHeaderAndBody(responseHeader, responseBody);
        return response;
    }


    private String methodNotAllowed() {
        return HttpStatus.METHOD_NOT_ALLOWED.getResponseCode() + EscapeCharacters.newline + EscapeCharacters.newline;
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

    private String format(String directoryAndFiles, Request request) {
        String htmlFormat = "";
        String[] dirAndFiles = directoryAndFiles.split(" ");
        int count = 0;
        for(String file: dirAndFiles) {
            if (count == 0) {
                htmlFormat += "<li>" + file + "</li>";
            } else {
                String currentDirectory = request.getPath().equals("/") ? "" : request.getPath();
                htmlFormat += "<li><a href=\"" + currentDirectory +  "/"+  file + "\">" + file + "</a></li>";
            }
            count += 1;
        }
        return htmlFormat;
    }

    private byte[] addResponseHeaderAndBody(byte[] responseHeader, byte[] responseBody) {
        byte[] response = new byte[responseHeader.length + responseBody.length];
        System.arraycopy(responseHeader, 0, response, 0, responseHeader.length);
        System.arraycopy(responseBody, 0, response, responseHeader.length, responseBody.length);
        return response;
    }

    private String convertToSHA1(byte[] message) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        return byteArrayToHex(md.digest(message));
    }

    public String getEtag(String fullRequest) {
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

    private boolean partialContentRequested(String fullRequest) {
        return fullRequest.contains("Range");
    }


}
