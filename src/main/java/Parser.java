import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    public static String parseInputStream(InputStream inputStream) throws IOException {
        String requestHeader = "";
        BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        while ((line = buffer.readLine()) != null) {
            if (line.trim().equals("")) {
                break;
            }
            requestHeader += line;
        }
        return requestHeader;
    }

    public static String parseForHttpVerb(String requestHeader) {
        String[] words = requestHeader.split(" ");
        return words[0];
    }

    public static String parseForPathUrl(String requestHeader) {
        String[] words = requestHeader.split(" ");
        return words[1];
    }


}
