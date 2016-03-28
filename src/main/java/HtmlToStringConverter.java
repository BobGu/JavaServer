import java.io.*;

public class HtmlToStringConverter {

    public static String convert(File htmlFile) throws IOException {
            StringBuilder contentBuilder = new StringBuilder();
            BufferedReader input = new BufferedReader(new FileReader(htmlFile));
            String lineOfHtml;
            while((lineOfHtml = input.readLine()) !=null) {
                contentBuilder.append(lineOfHtml);
            }
            input.close();
            return contentBuilder.toString();
    }
}
