package threadpool;


import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class FixedThreadPool {
    private static FixedThreadPool threadPool =  new FixedThreadPool();
    private LinkedList<Runnable> jobQueue = new LinkedList<>();

    private FixedThreadPool() {}

    public static FixedThreadPool getInstance() {
        return threadPool;
    }

    public void start() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

        while(!jobQueue.isEmpty()) {
            Runnable runnable = getFirstJob();
            executor.execute(runnable);
        }
        executor.shutdown();
    }

    public void addJob(Runnable runnable) {
        jobQueue.add(runnable);
    }

    public Runnable getFirstJob() {
        return jobQueue.poll();
    }
}
