import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) {
        Server server;

        try {
            if(args.length == 0) {
                server = new Server(new ServerSocket(5000), new Routes());
            } else {
                String port = args[0];
                int portNumber = Integer.parseInt(port);
                server = new Server(new ServerSocket(5000), new Routes());
            }
            server.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
