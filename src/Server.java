import java.io.Console;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public static void main(String port) {
    }

    public void run() {
        try {
            System.out.print(serverSocket);
            Socket socket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
