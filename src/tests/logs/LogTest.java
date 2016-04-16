import logs.Log;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class LogTest {
    private Log log;

    @Before
    public void getLog() {
        log = Log.getInstance();
    }

    @Test
    public void TestCanAddAVisitToTheLog() {
        String onlyVisit = "GET /form HTTP/1.1";
        log.addVisit(onlyVisit);

        List<String> recentVisits = log.recentVisits(1);
        String mostRecentVisit = recentVisits.get(0);

        assertEquals(onlyVisit, mostRecentVisit);
    }

    @Test
    public void TestRecentVisitsReturnsVisits() {
        log.addVisit("GET /form HTTP/1.1");
        log.addVisit("POST /form HTTP/1.1");
        log.addVisit("OPTIONS / HTTP/1.1");

        List<String> recentVisits = log.recentVisits(2);
        String secondMostRecentVisit = recentVisits.get(0);

        assertEquals("POST /form HTTP/1.1", secondMostRecentVisit);
    }

    @Test
    public void TestRecentVisitsCanReturnAllVisits() {
        log.addVisit("GET /form HTTP/1.1");
        log.addVisit("POST /form HTTP/1.1");
        log.addVisit("OPTIONS / HTTP/1.1");

        List<String> recentVisits = log.recentVisits(100);
        String mostRecentVisit = recentVisits.get(2);
        String secondMostRecentVisit = recentVisits.get(1);
        String thirdMostRecentVisit = recentVisits.get(0);

        assertEquals("GET /form HTTP/1.1", thirdMostRecentVisit);
        assertEquals("POST /form HTTP/1.1", secondMostRecentVisit);
        assertEquals("OPTIONS / HTTP/1.1", mostRecentVisit);

    }

    @Test
    public void TestOnlyOneLoggerIsCreated() {
        Log instanceOfLogOne = Log.getInstance();
        Log instanceOfLogTwo = Log.getInstance();

        assertSame(instanceOfLogOne, instanceOfLogTwo);
    }
}
