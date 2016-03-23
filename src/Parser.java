import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

}
