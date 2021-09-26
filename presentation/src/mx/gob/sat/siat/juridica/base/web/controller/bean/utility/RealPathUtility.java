package mx.gob.sat.siat.juridica.base.web.controller.bean.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.io.Serializable;

@Component
public class RealPathUtility implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4325862492611971305L;

    @Autowired
    private transient ServletContext servletContext;

    @Override
    public String toString() {
        return servletContext.getRealPath("/");
    }

}
