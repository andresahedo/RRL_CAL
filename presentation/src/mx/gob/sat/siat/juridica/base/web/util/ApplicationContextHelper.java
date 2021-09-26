package mx.gob.sat.siat.juridica.base.web.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.Serializable;

public class ApplicationContextHelper implements Serializable, ApplicationContextAware {

    /**
     * 
     */
    private static final long serialVersionUID = -5653268263369177048L;

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)  {
        ApplicationContextHelper.setApplicationContextStatic(applicationContext);

    }

    public static ApplicationContext getApplicationContext() {
        return ApplicationContextHelper.applicationContext;
    }

    public static void setApplicationContextStatic(ApplicationContext applicationContext) {
        ApplicationContextHelper.applicationContext = applicationContext;

    }

}
