import java.io.*;
import java.net.URL;

public class Handler {
    private Router router;

    public Handler() {
        router = new Router();
    }

    public String handleRequestAndResponse(InputStream socketInputStream) throws IOException {
        String request = request(socketInputStream);
        return response(request);
    }

    private String request(InputStream socketInputStream) throws IOException {
        return Parser.parseInputStream(socketInputStream);
    }

    private String response(String request) throws IOException {
        return router.getResponse(request);
    }

}
