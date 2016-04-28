import org.junit.Test;
import threadpool.FixedThreadPool;

import static junit.framework.TestCase.assertEquals;

public class FixedThreadPoolTest {

    @Test
    public void GetInstanceReturnsSameInstanceOfThreadPool() {
        FixedThreadPool threadPool = FixedThreadPool.getInstance();
        FixedThreadPool sameThreadPool = FixedThreadPool.getInstance();

        assertEquals(threadPool, sameThreadPool);
    }

    @Test
    public void CanAddAJob() {
        FixedThreadPool threadPool = FixedThreadPool.getInstance();
        MockRunnable runnable = new MockRunnable();
        threadPool.addJob(runnable);

        assertEquals(runnable, threadPool.getFirstJob());
    }

    private class MockRunnable implements Runnable {

        public void run() {

        }
    }
}
