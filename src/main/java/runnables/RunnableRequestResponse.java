package runnables;

import parsers.Parser;
import requests.Request;
import routes.FileRouter;

import java.io.IOException;
import java.net.Socket;

public class RunnableRequestResponse implements Runnable{
    private Thread thread;
    private String threadName;
    private Socket socket;
    private String directoryLocation;
    private Parser parser;
    private FileRouter fileRouter;

    public RunnableRequestResponse(Socket socket, String directoryLocation, Parser parser, FileRouter fileRouter, String threadName) {
        this.socket = socket;
        this.directoryLocation = directoryLocation;
        this.parser = parser;
        this.fileRouter = fileRouter;
        this.threadName = threadName;
    }

    public void run() {
        try {
            Request request = parser.parseAndCreateRequest(socket.getInputStream());
            synchronized (fileRouter) {
                fileRouter.setDirectoryLocation(directoryLocation);
                fileRouter.setRoutes();
                byte[] response = fileRouter.direct(request);
                socket.getOutputStream().write(response);
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() throws IOException {
        if (thread == null) {
            thread = new Thread(this, threadName);
            thread.start();
        }
    }
}
