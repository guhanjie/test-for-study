package test.spring.lifecycle;

import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author saitxuc write 2014-7-7
 */
public abstract class LifeCycleSupport implements LifeCycle {

  private static final Logger logger = LoggerFactory.getLogger(LifeCycleSupport.class);

  protected AtomicBoolean inited = new AtomicBoolean(false);

  protected AtomicBoolean started = new AtomicBoolean(false);

  protected AtomicBoolean stoping = new AtomicBoolean(false);

  protected AtomicBoolean stoped = new AtomicBoolean(false);

  public void init() {
    if (inited.compareAndSet(false, true)) {
      if (logger.isInfoEnabled()) {
        logger.info("Life Cycle[{}] will init.", this.getClass().getSimpleName());
      }
      doInit();
      if (logger.isInfoEnabled()) {
        logger.info("Life Cycle[{}] has finsih init.", this.getClass().getSimpleName());
      }
    }
  }

  public void start() {
    if (!inited.get()) {
      init();
    }
    if (started.compareAndSet(false, true)) {
      if (logger.isInfoEnabled()) {
        logger.info("Life Cycle[{}] will start.", this.getClass().getSimpleName());
      }
      doStart();
      if (logger.isInfoEnabled()) {
        logger.info("Life Cycle[{}] has finsih start.", this.getClass().getSimpleName());
      }
    }
  }

  public void stop() {
    if (stoping.compareAndSet(false, true)) {
      if (logger.isInfoEnabled()) {
        logger.info("Life Cycle[{}] will stop.", this.getClass().getSimpleName());
      }
    }
    doStop();
    if (logger.isInfoEnabled()) {
      logger.info("Life Cycle[{}] has finsih stop.", this.getClass().getSimpleName());
    }
  }

  public boolean isStarted() {
    return this.started.get();
  }

  public abstract void doInit();

  public abstract void doStart();

  public abstract void doStop();

}
