import Parsers.Parser;
import Requests.Request;
import Routes.Router;
import responses.BuildResponse;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    private Router router;
    private BuildResponse buildResponse;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        this.router = new Router();
        this.buildResponse = new BuildResponse(this.router, serverSocket.getLocalPort());
    }

    public void startServer() {

        while (true) {
            try {
                socket = serverSocket.accept();
                InputStream socketInputStream = socket.getInputStream();
                Request request = Parser.parseAndCreateRequest(socketInputStream);
                String response = buildResponse.build(request);
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
