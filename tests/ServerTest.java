import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;


public class ServerTest {
    private MockServerSocket serverSocket;

    @After
    public void CloseSockets() throws IOException {
        serverSocket.close();
    }

    @Test
    public void TestASocketIsOpened() throws IOException {
        MockServerSocket serverSocket = new MockServerSocket(9092);
        Server server = new Server(serverSocket);
        server.run();
        assertTrue(serverSocket.hasInvokedAccepted());
    }

    private class MockServerSocket extends ServerSocket {
        private boolean acceptInvoked = false;

        public MockServerSocket(int port) throws IOException {
            super(port);
        }

        public boolean hasInvokedAccepted() {
            return acceptInvoked;
        }

        @Override
        public Socket accept() {
            acceptInvoked = true;
            return new Socket();
        }

    }

}