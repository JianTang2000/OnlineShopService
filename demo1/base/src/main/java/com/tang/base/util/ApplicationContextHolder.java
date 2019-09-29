package com.tang.base.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * < 上下文拿bean工具类，可以在各个业务中心被调用，
 *  静态方法，以类名.方法名的形式调用；
 *  非静态的公共方法，注入再调用；
 *  各个业务中心的pom里面引用了base这个项目，打包的时候base会被打到每个业务中心的里面取，
 *  非final类，注入调用，如果有静态的public方法，也可以类名+方法名直接调用。
 *
 * ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/10/9 <br>
 */
@Component("ApplicationContextHolder")
public class ApplicationContextHolder implements ApplicationContextAware {

    /**
     * context
     */
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context) {
        ApplicationContextHolder.context = context;
    }

    /**
     * Description:<>
     *
     * @param beanName <> <br>
     * @return Object <br>
     */
    public static Object getBeanByName(String beanName) {
        if (beanName == null || context == null) {
            return null;
        }
        return context.getBean(beanName);
    }

    /**
     * Description:<>
     *
     * @param clazz <> <br>
     * @return Object <br>
     */
    public static Object getBeanByType(Class clazz) {
        if (clazz == null || context == null) {
            return null;
        }
        return context.getBean(clazz);
    }

    /**
     * Description:<>
     *
     * @return String <br>
     */
    public static String[] getBeanDefinitionNames() {
        return context.getBeanDefinitionNames();
    }
}
