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
            updatedText = updateFileText(file, textToWrite);
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
