package test.spring.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


/**
 * spring加载完毕后,初始化
 * 
 * @author owen
 * @version $Id: InitAfterStartListener.java, v 0.1 2014年9月19日 下午6:43:45 owen Exp $
 */
@Component
public class InitAfterStartListener implements ApplicationListener<ContextRefreshedEvent> {

	  private static final Logger logger = LoggerFactory.getLogger(InitAfterStartListener.class);
  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
	  logger.info("ApplicationListener1 ----  Context Refreshed.......");
		Thread start = new Thread() {
			public void run() {
				ConcreteService us = SpringContextHolder.getBean(ConcreteService.class);
				us.doService();
			}
		};
		start.setDaemon(true);
		start.start();
		try {
			Thread.sleep(5000);
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
  }

}
