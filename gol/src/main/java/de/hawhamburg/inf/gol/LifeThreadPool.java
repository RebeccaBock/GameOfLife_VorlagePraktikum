package de.hawhamburg.inf.gol;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Stream;

/**
 * A pool of LifeThreads and a queue holding Runnables to be processed.
 * 
 * @author Christian Lins
 */
public class LifeThreadPool {
    /* Unsynchronized Queue of Runnables */
    private final Queue<Runnable> tasks = new LinkedList<>();
    
    /* Number of threads managed by this pool */
    private final int numThreads;
    
    /* The collection of LifeThread instances forming this pool */
    private final LifeThread[] threads;
    
    public LifeThreadPool(int numThreads) {
        this.numThreads = numThreads;
        this.threads = new LifeThread[numThreads];
    }
    
    /**
     * Calls interrupt() on every thread in this pool.
     */
    public void interrupt() {
        // TODO Nutzen Sie Streams!
        Stream.of(threads).forEach(Thread::interrupt);
    }
    
    /**
     * Waits for all tasks to finish and calls interrupt on every thread. This
     * method is identical to synchronized calling barrier() and interrupt().
     * 
     * @throws InterruptedException 
     */
    public void joinAndExit() throws InterruptedException {
        // TODO
        for(int i = 0; i < numThreads; i++){
            threads[i].interrupt();
            threads[i].join();
        }
        
    }
    
    /**
     * Adds a task to the queue of this pool.
     * 
     * @param task Runnable containing the work to be done 
     */
    public synchronized void submit(Runnable task) {
        // TODO
        tasks.add(task);
        notifyAll();
    }
    
    /**
     * Removes and returns the next task from the queue of this pool.
     * This method blocks if the queue is currently empty.
     * 
     * @return Next task from the pool queue
     * @throws InterruptedException 
     */
    public synchronized Runnable nextTask() throws InterruptedException {
        // TODO
        while(tasks.isEmpty()){
            wait();    
        }
        return tasks.poll();
    }
    
    /**
     * Start all threads in this pool.
     */
    public void start() {
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new LifeThread(this);
            threads[i].start();
            // TODO
        }
    }
}
