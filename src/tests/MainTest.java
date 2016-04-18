import org.junit.Test;
import servers.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;

import static junit.framework.TestCase.assertEquals;

public class MainTest {
    private MockServer server = new MockServer();


    @Test
    public void CanPassInAPortNumber() throws IOException {
        String[] args = {"-p", "5001"};
        Main.main(args, server);
        ServerSocket socket = server.getServerSocket();

        assertEquals(5001, socket.getLocalPort());
        socket.close();
    }

    @Test
    public void ServersSocketHasADefaultPortNumber() throws IOException {
        String[] args = {};
        Main.main(args, server);
        ServerSocket socket = server.getServerSocket();

        assertEquals(5000, socket.getLocalPort());
        socket.close();
    }

    @Test
    public void RootDirectoryIsPublicDirectoryByDefault() throws IOException {
        String[] args = {};
        Main.main(args, server);

        assertEquals("public", server.getDirectoryName());
    }

    @Test
    public void CanReadBothAPortNumberAndADirectory() throws IOException {
        String[] args = {"-p", "5001", "-d", "non-public"};
        Main.main(args, server);
        ServerSocket socket = server.getServerSocket();

        assertEquals(5001, socket.getLocalPort());
        assertEquals("non-public", server.getDirectoryName());
        socket.close();
    }

    private class MockServer extends Server {
        private ServerSocket serverSocket;
        private String directoryName;

        @Override
        public void startServer() {

        }

        @Override
        public void setServerSocket(ServerSocket serverSocket) {
            this.serverSocket = serverSocket;
        }

        public void setDirectoryName(String directoryName) {
            this.directoryName = directoryName;
        }

        public ServerSocket getServerSocket() {
            return serverSocket;
        }

        public String getDirectoryName() {
            return directoryName;
        }

    }

}

