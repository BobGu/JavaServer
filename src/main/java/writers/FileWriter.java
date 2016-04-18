package writers;
import parsers.Parser;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileWriter implements Writer {

    public void write(String location, String textToWrite) throws IOException {
        File file = new File(location);

        java.io.FileWriter writer = new java.io.FileWriter(location, true);
        writer.write(textToWrite);
        writer.close();
    }

    public void update(String location, String textToWrite) throws IOException {
        String updatedText = "";
        File file = new File(location);

        if (file.exists()) {
            InputStream fileStream = new FileInputStream(file);
            String fileText = Parser.fileToText(fileStream);
            updatedText = updateFileText(fileText, textToWrite);
        }

        java.io.FileWriter writer = new java.io.FileWriter(location, false);
        writer.write(updatedText);
        writer.close();
    }

    public void delete(String location) {
        File file = new File(location);

        if (file.exists()) {
            file.delete();
        }
    }

    private String updateFileText(String fileText, String textToWrite) throws IOException {
        String updatedFileText = "";
        List<String> allExistingKeys = findExistingKeys(fileText);
        String replacementKey = getReplacementKey(textToWrite);

        if(anyKeysTheSame(allExistingKeys, replacementKey)) {
            updatedFileText = replaceValue(replacementKey, fileText, textToWrite);
        } else {
            updatedFileText += "\n" + textToWrite;
        }
        return updatedFileText;

    }

    private List<String> findExistingKeys(String text) {
        Pattern keyPattern = Pattern.compile("([a-zA-Z0-9\"]+=)");
        Matcher existingKeyMatcher = keyPattern.matcher(text);
        return findMatches(text, existingKeyMatcher);
    }

    private String replaceValue(String replacementKey, String textToReplace, String textToWrite) {
        Pattern keyValue = Pattern.compile("(" + replacementKey + "[a-zA-Z0-9\"]+)");
        Matcher keyValueMatcher = keyValue.matcher(textToReplace);

        keyValueMatcher.find();
        String keyValueToReplace = keyValueMatcher.group();
        return textToReplace.replaceFirst(keyValueToReplace, textToWrite);
    }

    private String getReplacementKey(String text) {
        Pattern keyPattern = Pattern.compile("([a-zA-Z0-9\"]+=)");
        Matcher replacementKeyMatcher = keyPattern.matcher(text);

        replacementKeyMatcher.find();
        return replacementKeyMatcher.group();
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

}
