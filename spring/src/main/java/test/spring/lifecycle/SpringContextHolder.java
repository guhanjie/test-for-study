package test.spring.lifecycle;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author yangxin
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {
  private static ApplicationContext applicationContext;

  public void setApplicationContext(ApplicationContext applicationContext) {
    SpringContextHolder.applicationContext = applicationContext;
  }

  public static ApplicationContext getApplicationContext() {
    checkApplicationContext();
    return applicationContext;
  }

  @SuppressWarnings("unchecked")
  public static <T> T getBean(String name) {
    checkApplicationContext();
    return (T) applicationContext.getBean(name);
  }

  @SuppressWarnings("unchecked")
  public static <T> T getBean(Class<T> clazz) {
    checkApplicationContext();
    @SuppressWarnings("rawtypes")
    Map beanMaps = applicationContext.getBeansOfType(clazz);
    if (beanMaps != null && !beanMaps.isEmpty()) {
      return (T) beanMaps.values().iterator().next();
    } else {
      return null;
    }
  }

  private static void checkApplicationContext() {
    if (applicationContext == null) {
      throw new IllegalStateException(
          "SpringContextHolder is not injected in applicationContext.xml");
    }
  }

}
