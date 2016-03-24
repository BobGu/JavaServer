import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    public static List<String> parseInputStream(InputStream inputStream) throws IOException {

        List<String> linesOfInputStream = new ArrayList<String>();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        while ((line = buffer.readLine()) != null) {
            linesOfInputStream.add(line);
        }

        return linesOfInputStream;
    }
    public static String parseForHttpVerb(String firstLineOfRequest) {
        String[] words = firstLineOfRequest.split(" ");
        return words[0];
    }

}
