package threadpool;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FixedThreadPool {
    private static FixedThreadPool threadPool =  new FixedThreadPool();
    private LinkedList<Runnable> jobQueue = new LinkedList<Runnable>();

    private FixedThreadPool() {}

    public static FixedThreadPool getInstance() {
        return threadPool;
    }

    public void addJob(Runnable runnable) {
        jobQueue.add(runnable);
    }

    public Runnable getFirstJob() {
        return jobQueue.poll();
    }
}
