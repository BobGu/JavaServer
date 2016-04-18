import servers.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) throws IOException {
        main(args, null);
    }

    public static void main(String[] args, Server customServer) throws IOException {
        Server server = customServer == null ? new Server() : customServer;

        if (args.length == 0) {
            ServerSocket serverSocket = new ServerSocket(5000);
            server.setServerSocket(serverSocket);
        } else {
            String port = args[0];
            int portNumber = Integer.parseInt(port);
            ServerSocket serverSocket = new ServerSocket(portNumber);
            server.setServerSocket(serverSocket);
        }
        server.startServer();
    }

}
