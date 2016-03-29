import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    private Router router;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        this.router = new Router();
    }

    public void startServer() {

        while (true) {
            try {
                socket = serverSocket.accept();

                InputStream clientSocketInputStream = socket.getInputStream();
                List<String> clientHeaderRequestLines = Parser.parseInputStream(clientSocketInputStream);
                String httpVerb = Parser.parseForHttpVerb(clientHeaderRequestLines.get(0));
                String pathUrl = Parser.parseForPathUrl(clientHeaderRequestLines.get(0));
                String responseHeader = responseHeader(httpVerb, pathUrl);
                InputStream fileStream = Server.class.getResourceAsStream("index.html");
                String responseBody = Parser.convertStreamToString(fileStream);
                String response = responseHeader + responseBody;

                respond(response);
                socket.shutdownOutput();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String responseHeader(String httpVerb, String url) {
        String responseHeader;

        if(router.requestAllowed(url, httpVerb) && httpVerb.equals("OPTIONS")) {
            responseHeader = "HTTP/1.1 200 OK\r\nAllow: GET,HEAD,POST,OPTIONS,PUT\r\n";
        } else if(router.requestAllowed(url, httpVerb)) {
            responseHeader = "HTTP/1.1 200 OK\r\n\r\n";
        } else {
            responseHeader = "HTTP/1.1 404 Not Found\r\n";
        }

        return responseHeader;
    }


    private void respond(String headers) throws IOException {
        socket.getOutputStream().write(headers.getBytes());
    }

}
