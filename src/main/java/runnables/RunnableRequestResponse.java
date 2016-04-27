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
    private String fileLocation;
    private Parser parser;
    private Router router;

    public RunnableRequestResponse(Socket socket, String fileLocation, Parser parser, Router router) {
        this.socket = socket;
        this.fileLocation = fileLocation;
        this.parser = parser;
        this.router = router;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public void run() {
        try {
            Request request = parser.parseAndCreateRequest(socket.getInputStream(), fileLocation);
            router.direct(request);
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
