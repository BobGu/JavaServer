package runnables;

import parsers.Parser;
import requests.Request;
import routes.Router;

import java.io.IOException;
import java.net.Socket;

public class RunnableRequestResponse implements Runnable{
    private Thread thread;
    private String threadName;
    private Socket socket;
    private String directoryLocation;
    private Parser parser;
    private Router router;

    public RunnableRequestResponse(Socket socket, String directoryLocation, Parser parser, Router router, String threadName) {
        this.socket = socket;
        this.directoryLocation = directoryLocation;
        this.parser = parser;
        this.router = router;
        this.threadName = threadName;
    }

    public void run() {
        try {
            Request request = parser.parseAndCreateRequest(socket.getInputStream());
            synchronized (router) {
                router.setFileLocation(directoryLocation + request.getPath());
                router.setRoutes();
                byte[] response = router.direct(request);
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
