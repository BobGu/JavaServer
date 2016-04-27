import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import parsers.Parser;
import requests.Request;
import routes.Router;
import runnables.RunnableRequestResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import static org.junit.Assert.assertTrue;

public class RunnableRequestResponseTest {
    private boolean setUpIsDone = false;
    private MockSocket socket = new MockSocket();
    private MockParser parser = new MockParser();
    private MockRouter router = new MockRouter();
    private RunnableRequestResponse runnable = new RunnableRequestResponse(socket, "public", parser, router);

    public void startThread() throws InterruptedException, IOException {
        runnable.setThreadName("Thread 1");
        runnable.start();
        Thread.sleep(100);
    }

    @Before
    public void setUp() throws IOException, InterruptedException {
        if (setUpIsDone) {
            return;
        } else {
            startThread();
        }
    }

    @Test
    public void AThreadCallsTheParser() {
        assertTrue(parser.isParseAndCreateRequestInvoked());
    }

    @Test
    public void ThreadCallsTheCorrectRouterMethods() {
        assertTrue(router.isDirectInvoked());
    }

    private class MockSocket extends Socket {

        @Override
        public InputStream getInputStream() {
            String request = "The request";
            InputStream inputStream = new ByteArrayInputStream(request.getBytes());
            return inputStream;
        }
    }

    private class MockParser extends Parser {
        private boolean parseAndCreateRequestInvoked = false;

        @Override
        public Request parseAndCreateRequest(InputStream inputStream, String directoryName) {
            parseAndCreateRequestInvoked = true;
            return new Request("GET / HTTP/1.1", "/", "GET", null , null);
        }

        public boolean isParseAndCreateRequestInvoked() {
            return parseAndCreateRequestInvoked;
        }
    }

    private class MockRouter extends Router {
        private boolean directInvoked = false;

        @Override
        public byte[] direct(Request request) {
            directInvoked = true;
            return new byte[4];
        }

        public boolean isDirectInvoked() {
            return directInvoked;
        }
    }


}
