import org.junit.Before;
import org.junit.Test;
import parsers.Parser;
import requests.Request;
import routes.Router;
import runnables.RunnableRequestResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static org.junit.Assert.assertTrue;

public class RunnableRequestResponseTest {
    private boolean setUpIsDone = false;
    private MockOutputStream outputStream = new MockOutputStream();
    private MockSocket socket = new MockSocket(outputStream);
    private MockParser parser = new MockParser();
    private MockRouter router = new MockRouter();
    private RunnableRequestResponse runnable = new RunnableRequestResponse(socket, "public", parser, router, "Thread 1");

    public void startThread() throws InterruptedException, IOException {
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

    @Test
    public void ThreadWritesToTheSocket() {
        assertTrue(outputStream.isWriteInvoked());
    }

    @Test
    public void ThreadClosesTheSocket() {
        assertTrue(socket.isCloseInvoked());
    }


    private class MockOutputStream extends OutputStream {
        private boolean writeInvoked = false;

        public MockOutputStream() {}

        @Override
        public void write(int bytes) {
            writeInvoked = true;
        }

        public boolean isWriteInvoked() {
            return writeInvoked;
        }
    }

    private class MockSocket extends Socket {
        private boolean closeInvoked = false;
        private OutputStream outputStream;

        MockSocket(OutputStream outputStream) {
            this.outputStream = outputStream;
        }

        @Override
        public InputStream getInputStream() {
            String request = "The request";
            InputStream inputStream = new ByteArrayInputStream(request.getBytes());
            return inputStream;
        }

        @Override
        public OutputStream getOutputStream() {
            return outputStream;
        }

        @Override
        public void close() {
            closeInvoked = true;
        }


        public boolean isCloseInvoked() {
            return closeInvoked;
        }
    }

    private class MockParser extends Parser {
        private boolean parseAndCreateRequestInvoked = false;

        @Override
        public Request parseAndCreateRequest(InputStream inputStream) {
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
