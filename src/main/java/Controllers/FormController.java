package controllers;

import readers.*;
import writers.FileWriter;
import writers.Writer;
import parsers.Parser;
import requests.Request;
import httpStatus.HttpStatus;
import specialCharacters.EscapeCharacters;
import java.io.*;
import java.io.File;
import readers.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormController implements Controller {
    private String METHODS_ALLOWED = "GET,POST,PUT,DELETE,OPTIONS";
    private String resourcePath;
    private Writer writer;
    private Reader reader;

    public FormController(Writer writer, Reader reader){
       this(null, writer, reader);
    }

    public FormController(String resourcePath, Writer writer, Reader reader) {
        if (resourcePath == null) {
            this.resourcePath = "../resources/main/form.txt";
            this.writer = writer;
            this.reader = reader;
        } else {
            this.resourcePath = resourcePath;
            this.writer = writer;
            this.reader = reader;
        }
    }

    public String handle(Request request) throws IOException {
        String response = "";
        if (METHODS_ALLOWED.contains(request.getHttpVerb())) {
            if (request.getHttpVerb().equals("GET")) {
                response = get(request);
            } else if (request.getHttpVerb().equals("POST")) {
                response = post(request);
            } else if (request.getHttpVerb().equals("PUT")) {
                response = put(request);
            } else if (request.getHttpVerb().equals("DELETE")) {
                response = delete();
            } else if (request.getHttpVerb().equals("OPTIONS")) {
                response = options();
            }
        } else {
            response = HttpStatus.methodNotAllowed;
        }

        return response;
    }

    private String get(Request request) throws IOException {
        String responseHead = HttpStatus.okay + EscapeCharacters.newline + EscapeCharacters.newline;
        String responseBody = reader.read(resourcePath);
        return responseHead + responseBody;
    }

    private String post(Request request) throws IOException {
        String response = HttpStatus.okay + EscapeCharacters.newline + EscapeCharacters.newline;
        String textToWrite = request.getParameters();
        writer.write(resourcePath, textToWrite);
        return response;
    }

    private String delete() {
        String response = HttpStatus.okay + EscapeCharacters.newline + EscapeCharacters.newline;
        File file = new File(resourcePath);

        if(file.exists()) {
            file.delete();
        }
        return response;
    }

    private String put(Request request) throws IOException {
        return post(request);
    }

    private String options() {
        return  HttpStatus.okay
                + EscapeCharacters.newline
                + "Allow: "
                + METHODS_ALLOWED
                + EscapeCharacters.newline
                + EscapeCharacters.newline;
    }

    private String updateFileText(File file, String textToWrite) throws IOException {
        InputStream fileStream = new FileInputStream(file);
        String fileText = Parser.fileToText(fileStream);

        Pattern keyPattern = Pattern.compile("([a-zA-Z0-9\"]+=)");
        Matcher existingKeyMatcher = keyPattern.matcher(fileText);
        Matcher replacementKeyMatcher = keyPattern.matcher(textToWrite);

        List<String> allExistingKeys = findMatches(fileText, existingKeyMatcher);
        replacementKeyMatcher.find();
        String replacementKey = replacementKeyMatcher.group();

        if(anyKeysTheSame(allExistingKeys, replacementKey)) {
            Pattern keyValue = Pattern.compile("(replacementKey[a-zA-Z0-9\"]+)");
            Matcher keyValueMatcher = keyValue.matcher(fileText);
            keyValueMatcher.find();
            String keyValueToReplace = keyValueMatcher.group();
            fileText.replaceFirst(keyValueToReplace, textToWrite);

        } else {
            fileText += "\n" + textToWrite;
        }


        return fileText;

    }

    private List<String> findMatches(String fileText, Matcher existingKeyMatcher) {
        List<String> allMatches = new ArrayList<String>();

        while(existingKeyMatcher.find()) {
            allMatches.add(existingKeyMatcher.group());
        }
        return allMatches;
    }

    private boolean anyKeysTheSame(List<String> existingKeys, String replacementKey) {
        return existingKeys.stream()
                           .anyMatch(key -> key.equals(replacementKey));
    }

    private String replaceValue(String fileText, String textToWrite) {
        Pattern valuePattern = Pattern.compile("(=[a-zA-Z0-9\" ]+)");
        Matcher existingValueMatcher = valuePattern.matcher(fileText);
        Matcher replacementValueMatcher = valuePattern.matcher(textToWrite);

        existingValueMatcher.find();
        replacementValueMatcher.find();

        return fileText.replace(existingValueMatcher.group(1), replacementValueMatcher.group(1));
    }

}
