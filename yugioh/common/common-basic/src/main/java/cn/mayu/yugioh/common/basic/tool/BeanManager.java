package cn.mayu.yugioh.common.basic.tool;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class BeanManager implements ApplicationContextAware {

    private volatile static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext app) throws BeansException {
        if (applicationContext == null) {
            synchronized (BeanManager.class) {
                if (applicationContext == null) {
                    applicationContext = app;
                }
            }
        }
    }

    public static <T> T getBean(Class<T> classType) {
        return applicationContext.getBean(classType);
    }

    public static <T> T getBean(String beanName, Class<T> classType) throws BeansException {
        return applicationContext.getBean(beanName, classType);
    }

    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }
}
