import org.junit.Test;
import static junit.framework.TestCase.assertEquals;


public class ServerTest {

    @Test
    public void TestServerUsesPort5000ByDefault() {
        Server server = new Server();
        assertEquals(5000, server.getPort());
    }
}