package cx.ath.jbzdak.oef.vme;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-11-01
 */
public abstract class CloseableThread extends Thread{

  private final ReentrantLock shutdownLock = new ReentrantLock();

  private final Condition shutdownCondition = shutdownLock.newCondition();

  volatile  boolean needsShutDown;

  volatile boolean shutDown;

  public void shutdown(){
     shutdownLock.lock();
     try{
        needsShutDown = true;
        while(!shutDown){
           try {
              this.interrupt();
              shutdownCondition.await(100, TimeUnit.MILLISECONDS);
           } catch (InterruptedException e) {
              //ignore
           }
        }
     }finally {
        shutdownLock.unlock();
     }
  }

  /**
   * This metod may be interrupted.
   */
  protected abstract void executeOneIteration();

  protected void beforeShutDown() {}

  @Override
  public void run() {
     while(!needsShutDown){
        executeOneIteration();
     }
     beforeShutDown();
     shutDown = true;
     shutdownLock.lock();
     try {
        shutdownCondition.signalAll();
     } finally {
        shutdownLock.unlock();
     }
  }
}