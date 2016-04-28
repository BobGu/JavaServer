package servers;

import parsers.Parser;
import routes.FileRouter;
import runnables.RunnableRequestResponse;
import threadpool.FixedThreadPool;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    private FileRouter fileRouter;
    private String directoryName;
    private FixedThreadPool threadPool = FixedThreadPool.getInstance();
    private Parser parser = new Parser();

    public Server() {
        this(null);
    }

    public Server(FileRouter fileRouter) {
        if (fileRouter == null) {
            this.fileRouter = new FileRouter();
        } else {
            this.fileRouter = fileRouter;
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
                RunnableRequestResponse runnable = new RunnableRequestResponse(socket, directoryName, parser, fileRouter, "Thread");
                threadPool.addJob(runnable);
                threadPool.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
