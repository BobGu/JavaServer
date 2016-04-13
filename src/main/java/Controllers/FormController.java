package Controllers;

import Parsers.Parser;
import Requests.Request;
import httpStatus.HttpStatus;
import specialCharacters.EscapeCharacters;

import java.io.*;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormController implements Controller {
    private String METHODS_ALLOWED = "GET,POST,PUT,DELETE,OPTIONS";
    private String resourcePath;

    public FormController(){
       this(null);
    }

    public FormController(String resourcePath) {
        if (resourcePath == null) {
            this.resourcePath = "../resources/main/form.txt";
        } else {
            this.resourcePath = resourcePath;
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

    public String get(Request request) throws IOException {
        String response;
        String responseHead = HttpStatus.okay + EscapeCharacters.newline + EscapeCharacters.newline;
        String responseBody;
        File file = new File(resourcePath);

        if(file.exists()) {
            InputStream fileStream = new FileInputStream(file);
            responseBody = Parser.fileToText(fileStream);
        } else {
            responseBody = "";
        }
        return responseHead + responseBody;
    }

    public String post(Request request) throws IOException {
        String response = HttpStatus.okay + EscapeCharacters.newline + EscapeCharacters.newline;
        String textToWrite = request.getParameters();
        File file = new File(resourcePath);

        if(file.exists()) {
            textToWrite = updateFileText(file, textToWrite);
        }
            FileWriter writer = new FileWriter(resourcePath, false);
            writer.write(textToWrite);
            writer.close();
        return response;
    }

    public String delete() {
        String response = HttpStatus.okay + EscapeCharacters.newline + EscapeCharacters.newline;
        File file = new File(resourcePath);

        if(file.exists()) {
            file.delete();
        }
        return response;
    }

    public String put(Request request) throws IOException {
        return post(request);
    }

    public String options() {
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

        Pattern keyPattern = Pattern.compile("([a-z]+=)");
        Matcher existingKeyMatcher = keyPattern.matcher(fileText);
        Matcher replacementKeyMatcher = keyPattern.matcher(textToWrite);

        existingKeyMatcher.find();
        replacementKeyMatcher.find();

        if(areKeysTheSame(existingKeyMatcher, replacementKeyMatcher)) {
            fileText = replaceValue(fileText, textToWrite);
        } else {
            fileText += "\n" + textToWrite;
        }


        return fileText;

    }
    private boolean areKeysTheSame(Matcher existingMatcher, Matcher replacementMatcher) {
        return existingMatcher.group(1).equals(replacementMatcher.group(1));
    }

    private String replaceValue(String fileText, String textToWrite) {
        Pattern valuePattern = Pattern.compile("(=[a-z ]+)");
        Matcher existingValueMatcher = valuePattern.matcher(fileText);
        Matcher replacementValueMatcher = valuePattern.matcher(textToWrite);

        existingValueMatcher.find();
        replacementValueMatcher.find();

        return fileText.replace(existingValueMatcher.group(1), replacementValueMatcher.group(1));
    }

}
