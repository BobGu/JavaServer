package servers;

import parsers.Parser;
import requests.Request;
import routes.Router;
import runnables.RunnableRequestResponse;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    private Router router;
    private String directoryName;

    public Server() {
        this(null);
    }

    public Server(Router router) {
        if (router == null) {
            this.router = new Router();
        } else {
            this.router = router;
        }
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
                RunnableRequestResponse runnable = new RunnableRequestResponse(socket, directoryName, new Parser(), router, "Thread 1");
                runnable.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
