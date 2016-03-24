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
                String responseHeader = "HTTP/1.1 200 OK\r\n";
                response(responseHeader);
                socket.shutdownOutput();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void response(String headers) throws IOException {
        socket.getOutputStream().write(headers.getBytes());
    }


}
