package servers;

import parsers.Parser;
import requests.Request;
import routes.Router;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    private Router router;
    private String directoryName;

    public Server() {
        this.router = new Router();
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public void startServer() {

        while (true) {
            try {
                socket = serverSocket.accept();
                InputStream socketInputStream = socket.getInputStream();
                Request request = Parser.parseAndCreateRequest(socketInputStream);
                String response = router.direct(request);
                respond(response);
                socket.shutdownOutput();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void respond(String response) throws IOException {
        socket.getOutputStream().write(response.getBytes());
    }

}
