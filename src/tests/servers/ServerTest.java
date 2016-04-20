package servers;

import org.junit.Test;
import servers.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ServerTest {
    private MockServerSocket serverSocket;

    @Test
    public void TestASocketIsOpened() throws IOException {
        MockServerSocket serverSocket = new MockServerSocket(9092);
        Server server = new Server();
        server.startServer();
        assertTrue(serverSocket.hasInvokedAccepted());
        serverSocket.close();
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