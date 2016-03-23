import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) {
        Server server;

        try {
            server = new Server(new ServerSocket(5000));
            server.run();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
