import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class Controller {

    public abstract String get() throws FileNotFoundException;
    public abstract String post(String request) throws IOException;

}
