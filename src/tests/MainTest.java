import org.junit.Test;
import servers.Server;

import java.io.IOException;
import java.net.ServerSocket;

import static junit.framework.TestCase.assertEquals;

public class MainTest {
    private MockServer server = new MockServer();


    @Test
    public void CanPassInAPortNumber() throws IOException {
        String[] args = {"5001"};
        Main.main(args, server);
        ServerSocket socket = server.getServerSocket();

        assertEquals(5001, socket.getLocalPort());
    }

    @Test
    public void ServersSocketHasADefaultPortNumber() throws IOException {
        String[] args = {};
        Main.main(args, server);
        ServerSocket socket = server.getServerSocket();

        assertEquals(5000, socket.getLocalPort());
    }

    private class MockServer extends Server {
        private ServerSocket serverSocket;

        @Override
        public void startServer() {

        }

        @Override
        public void setServerSocket(ServerSocket serverSocket) {
            this.serverSocket = serverSocket;
        }

        public ServerSocket getServerSocket() {
            return serverSocket;
        }

    }

}

