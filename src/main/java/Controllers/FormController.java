package Controllers;

import Parsers.Parser;
import Requests.Request;

import java.io.*;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormController implements Controller {
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

    public String get() throws IOException {
        String responseBody;
        File file = new File(resourcePath);

        if(file.exists()) {
            InputStream fileStream = new FileInputStream(file);
            responseBody = Parser.fileToText(fileStream);
        } else {
            responseBody = "";
        }
        return responseBody;
    }

    public void post(Request request) throws IOException {
        String textToWrite = request.getBody();
        File file = new File(resourcePath);

        if(file.exists()) {
            textToWrite = updateFileText(file, textToWrite);
        }
            FileWriter writer = new FileWriter(resourcePath, false);
            writer.write(textToWrite);
            writer.close();


    }

    public void delete() {
        File file = new File(resourcePath);

        if(file.exists()) {
            file.delete();
        }

    }

    public void put(Request request) throws IOException {
        post(request);
    }

    public void head(){}

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
