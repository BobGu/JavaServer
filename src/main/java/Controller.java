import java.io.File;
import java.io.IOException;

public abstract class Controller {

    public abstract String get();
    public abstract String post(String request) throws IOException;

}
