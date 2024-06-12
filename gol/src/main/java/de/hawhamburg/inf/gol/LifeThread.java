package de.hawhamburg.inf.gol;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Interruptible Thread that receives its next tasks from a LifeThreadPool.
 * 
 * @author Christian Lins
 */
public class LifeThread extends Thread {
    
    private final LifeThreadPool pool;
    
    public LifeThread(LifeThreadPool pool) {
        this.pool = pool;
    }
    
    @Override
    public void run() {
        while (!interrupted()) {
            try {
                // TODO
                pool.nextTask().run();
                
            } catch (InterruptedException ex) {
                Logger.getLogger(LifeThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
