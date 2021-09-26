package mx.gob.sat.siat.juridica.base.web.util;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@ManagedBean(name = "dateComparator")
@NoneScoped
public class DateComparator implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5619825248713795402L;

    public int compare(Date date1, Date date2) {

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        cal1.setTime(date1);
        Calendar calendarwotime1 = Calendar.getInstance();
        calendarwotime1.set(cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH), cal1.get(Calendar.DATE));

        cal2.setTime(date2);
        Calendar calendarwotime2 = Calendar.getInstance();
        calendarwotime2.set(cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH), cal2.get(Calendar.DATE));

        return calendarwotime1.compareTo(calendarwotime2);
    }

}
