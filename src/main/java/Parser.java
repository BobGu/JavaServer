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
        String request= "";
        BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        while (!(line = buffer.readLine()).equals("")) {
            request += line;
        }

        if(isContentLength(request)) {
            String contentLength = getContentLength(request);
            int lettersToRead = Integer.parseInt(contentLength);
            int c;

            for(int i = 0 ; i < lettersToRead; i++) {
                c = buffer.read();
                request += (char)c;
            }
        }

        System.out.println(request);
        return request;
    }

    public static String parseForHttpVerb(String requestHeader) {
        String[] words = requestHeader.split(" ");
        return words[0];
    }

    public static String parseForPathUrl(String requestHeader) {
        String[] words = requestHeader.split(" ");
        return words[1];
    }

    private static boolean isContentLength(String header) {
        return header.contains("Content-Length");
    }

    private static String getContentLength(String header) {
        Pattern pattern = Pattern.compile("Content-Length: ([0-9]+)");
        Matcher matcher = pattern.matcher(header);
        matcher.find();
        return matcher.group(1);
    }



}
