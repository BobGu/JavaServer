package writers;

import java.io.IOException;

public interface Writer {

    public void write(String location, String textToWrite) throws IOException;
    public void update(String location, String textToWrite) throws IOException;
    public void delete(String location);

}
