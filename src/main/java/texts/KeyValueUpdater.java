package texts;

import specialCharacters.EscapeCharacters;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeyValueUpdater implements TextUpdater{

    public String update(String fileText, String textToWrite) {
        String updatedFileText = "";
        List<String> allExistingKeys = findExistingKeys(fileText);
        String replacementKey = getReplacementKey(textToWrite);

        if(anyKeysTheSame(allExistingKeys, replacementKey)) {
            updatedFileText = replaceValue(replacementKey, fileText, textToWrite);
        } else {
            updatedFileText += fileText + EscapeCharacters.newline + textToWrite;
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
