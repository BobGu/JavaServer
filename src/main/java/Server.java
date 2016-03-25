import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void startServer() {

        while (true) {
            try {
                socket = serverSocket.accept();

                InputStream clientSocketInputStream = socket.getInputStream();
                List<String> clientHeaderRequestLines = Parser.parseInputStream(clientSocketInputStream);
                String httpVerb = Parser.parseForHttpVerb(clientHeaderRequestLines.get(0));
                String url =  urlConstructor(clientHeaderRequestLines);
                String responseHeader = responseHeader(httpVerb, url);

                respond(responseHeader);
                socket.shutdownOutput();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String responseHeader(String httpVerb, String url) {
        Routes routes = new Routes();
        String responseHeader;

        if(routes.exist(httpVerb, url)) {
            responseHeader = "HTTP/1.1 200 OK\r\n";
        } else {
            responseHeader = "HTTP/1.1 404 Not Found\r\n";
        }

        return responseHeader;
    }

    private void respond(String headers) throws IOException {
        socket.getOutputStream().write(headers.getBytes());
    }

    private String urlConstructor(List<String> clientHeaderRequestLines) {
        String rootUrl = Parser.parseForRootUrl(clientHeaderRequestLines.get(1));
        String pathUrl = Parser.parseForPathUrl(clientHeaderRequestLines.get(0));
        return rootUrl + pathUrl;
    }


}
