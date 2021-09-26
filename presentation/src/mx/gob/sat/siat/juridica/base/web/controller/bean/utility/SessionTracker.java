package mx.gob.sat.siat.juridica.base.web.controller.bean.utility;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SessionTracker implements HttpSessionAttributeListener {

    private Logger log = Logger.getLogger(this.getClass().getName());

    @Override
    public void attributeAdded(HttpSessionBindingEvent arg0) {
        Object obj = arg0.getValue();

        if (!isSerializable(obj)) {
            log.debug("El atributo " + arg0.getName() + "no es serializable" + ": " + arg0.getValue());
            log.log(Level.ERROR, "El atributo " + arg0.getName() + "no es serializable" + ": " + arg0.getValue());

            log.debug("thread dump: " + threadDump());
            log.log(Level.ERROR, "thread dump: " + threadDump());
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent arg0) {
        // TODO Auto-generated method stub

    }

    public boolean isSerializable(Object obj) {
        boolean ret = false;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream outs = null;

        try {
            outs = new ObjectOutputStream(out);
            outs.writeObject(obj);
            ret = true;
        }
        catch (IOException e) {
            return ret;
        }
        finally {
            try {
                if (outs != null) {
                    outs.close();
                }
            }
            catch (IOException e) {
                log.error("", e);
            }
        }
        return ret;
    }

    private String threadDump() {
        StringBuffer sb = new StringBuffer();
        Thread t = Thread.currentThread();
        Thread.State s = t.getState();
        String tname = t.getName();
        if (s != null) {
            sb.append("    ");
            sb.append(tname);
            sb.append(": ");
            sb.append(s);
            sb.append("\n");
        }
        StackTraceElement[] stackTrace = t.getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            sb.append("           at ");
            sb.append(stackTraceElement);
            sb.append("\n");
        }
        return sb.toString();

    }

}
