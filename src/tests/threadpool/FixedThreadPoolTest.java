import org.junit.Test;
import threadpool.FixedThreadPool;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class FixedThreadPoolTest {
    private FixedThreadPool threadPool = FixedThreadPool.getInstance();

    @Test
    public void GetInstanceReturnsSameInstanceOfThreadPool() {
        FixedThreadPool sameThreadPool = FixedThreadPool.getInstance();

        assertEquals(threadPool, sameThreadPool);
    }

    @Test
    public void CanAddAJob() {
        MockRunnable runnable = new MockRunnable();
        threadPool.addJob(runnable);

        assertEquals(runnable, threadPool.getFirstJob());
    }

    @Test
    public void CanRunAJob() {
        MockRunnable runnable = new MockRunnable();
        threadPool.addJob(runnable);
        threadPool.start();

        assertTrue(runnable.getIsRun());
    }

    private class MockRunnable implements Runnable {
        private boolean isRun = false;

        public void run() {
            isRun = true;
        }

        public boolean getIsRun() {
            return isRun;
        }
    }
}
