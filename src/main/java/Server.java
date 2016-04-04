import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    private Handler handler;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        this.handler = new Handler();
    }

    public void startServer() {

        while (true) {
            try {
                socket = serverSocket.accept();
                InputStream socketInputStream = socket.getInputStream();
                String response = handler.handleRequestAndResponse(socketInputStream);
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
