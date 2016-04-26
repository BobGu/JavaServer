import servers.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Arrays;

public class Main {
    private static final String PORT_FLAG = "-p";
    private static final String DIRECTORY_FLAG = "-d";

    public static void main(String[] args) throws IOException {
        main(args, null);
    }

    public static void main(String[] args, Server customServer) throws IOException {
        Server server = setServer(customServer);
        setServerSocketPort(server, args);
        setDirectory(server, args);

        server.startServer();
    }

    private static void  setServerSocketPort(Server server, String[] args) throws IOException {
        int portNumber = 5000;

        if (containsFlag(args, PORT_FLAG)) {
            String port = getValueAfterFlag(args, PORT_FLAG);
            portNumber = Integer.parseInt(port);
        }
        ServerSocket serverSocket = new ServerSocket(portNumber);
        server.setServerSocket(serverSocket);
    }

    private static void setDirectory(Server server, String[] args) {
        String directoryName = "/Users/robertgu/cob_spec/public";

        if (containsFlag(args, DIRECTORY_FLAG)) {
            directoryName = getValueAfterFlag(args, DIRECTORY_FLAG);
        }
        server.setDirectoryName(directoryName);
    }

    private static Server setServer(Server server) {
        return server == null ? new Server() : server;
    }

    private static String getValueAfterFlag(String[] args, String flag) {
        int dirFlag = indexOfFlag(args, flag);
        return args[dirFlag + 1];
    }

    private static boolean containsFlag(String[] args, String flag) {
        return Arrays.asList(args).contains(flag);
    }

    private static int indexOfFlag(String[] args, String flag) {
        return Arrays.asList(args).indexOf(flag);
    }

}
