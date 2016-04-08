package decoders;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParameterDecoder {
    private Map<String,String> encodedCharacters = createEncodedCharacters();

    public String decode(String percentEncodedPhrase) {
        List<String> allMatches = findMatches(percentEncodedPhrase);
        ArrayList<String> decodedCharacters = decodeCharacters(allMatches);
        return join(decodedCharacters);
    }

    private ArrayList<String> decodeCharacters(List<String> allMatches) {
        return allMatches.stream()
                         .map(character -> decodeCharacter(character))
                         .collect(Collectors.toCollection(ArrayList::new));
    }

    private String decodeCharacter(String character) {
        String decodedCharacter = encodedCharacters.get(character);
        return decodedCharacter == null ? character: decodedCharacter;
    }

    private List<String> findMatches(String encodedPhrase) {
        List<String> allMatches = new ArrayList<String>();
        Matcher matcher = Pattern.compile("(%.{2})|([a-z=?&]+)").matcher(encodedPhrase);

        while(matcher.find()) {
            allMatches.add(matcher.group());
        }
        return allMatches;
    }

    private String join(ArrayList<String> decodedCharacters) {
        String decodedString = "";

        for (String character : decodedCharacters) {
            decodedString += character;
        }

        return decodedString;
    }


    private Map<String,String> createEncodedCharacters() {
        Map<String, String> encodedCharacters = new HashMap<String,String>();

        encodedCharacters.put("%21", "!");
        encodedCharacters.put("%23", "#");
        encodedCharacters.put("%24", "$");
        encodedCharacters.put("%26", "&");
        encodedCharacters.put("%27", "'");
        encodedCharacters.put("%28", "(");
        encodedCharacters.put("%29", ")");
        encodedCharacters.put("%2A", "*");
        encodedCharacters.put("%2B", "+");
        encodedCharacters.put("%2C", ",");
        encodedCharacters.put("%2F", "/");
        encodedCharacters.put("%3A", ":");
        encodedCharacters.put("%3B", ";");
        encodedCharacters.put("%3D", "=");
        encodedCharacters.put("%3F", "?");
        encodedCharacters.put("%40", "@");
        encodedCharacters.put("%5B", "[");
        encodedCharacters.put("%5D", "]");
        encodedCharacters.put("%20", " ");
        encodedCharacters.put("%22", "\"" );
        encodedCharacters.put("%25", "%");
        encodedCharacters.put("%2C", ",");
        encodedCharacters.put("%2D", "-");
        encodedCharacters.put("%2E", ".");
        encodedCharacters.put("%3C", "<");
        encodedCharacters.put("%3E", ">");
        encodedCharacters.put("%5C", "\\");
        encodedCharacters.put("%5E", "^");
        encodedCharacters.put("%5F", "_");
        encodedCharacters.put("%60", "`");
        encodedCharacters.put("%7B", "{");
        encodedCharacters.put("%7C", "|");
        encodedCharacters.put("%7D", "}");
        encodedCharacters.put("%7E", "~");

        return encodedCharacters;
    }
}
