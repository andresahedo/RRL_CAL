/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.util.helper;

import mx.gob.sat.siat.juridica.base.dao.DiaInhabilDAO;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.DiaInhabil;
import mx.gob.sat.siat.juridica.base.helper.BaseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author softtek
 */
@Component
public class FechaCapturaHelper extends BaseHelper {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 7232931726030939369L;

    /**
     * Atributo diaInHabilDAO tipo DiaInhabilDAO
     */
    @Autowired
    private DiaInhabilDAO diaInhabilDAO;

    /**
     * Metodo que calcula la fecha de captura en dia habil a partir de
     * una fecha de captura
     * 
     * @param fechaCaptura
     * @return
     * @throws ParseException
     */
    public Date calcularFechaCaptura(Date fechaCaptura) throws ParseException {

        Calendar fecha = Calendar.getInstance();
        fecha.setTime(fechaCaptura);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<String> diasInhabiles = obtenerDiasInhabiles(diaInhabilDAO.obtenerDiasInhabiles());
        HashSet<String> diasSet = new HashSet<String>(diasInhabiles);

        if (fecha.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            fecha.add(Calendar.DAY_OF_WEEK, 1);
            fecha.set(Calendar.HOUR_OF_DAY, 0);
            fecha.clear(Calendar.MINUTE);
            fecha.clear(Calendar.SECOND);
        }
        if (fecha.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            fecha.add(Calendar.DAY_OF_WEEK, 1);
            fecha.set(Calendar.HOUR_OF_DAY, 0);
            fecha.clear(Calendar.MINUTE);
            fecha.clear(Calendar.SECOND);
        }

        for (String diaInhabil : diasInhabiles) {
            Date todayDate = dateFormat.parse(dateFormat.format(fecha.getTime()));
            Date convertedDate = dateFormat.parse(diaInhabil);
            if (todayDate.equals(convertedDate)) {
                fecha.add(Calendar.DAY_OF_WEEK, 1);
                fecha.set(Calendar.HOUR_OF_DAY, 0);
                fecha.clear(Calendar.MINUTE);
                fecha.clear(Calendar.SECOND);
            }
        }
        if (fecha.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || fecha.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
                || diasSet.contains(dateFormat.format(fecha.getTime()))) {
            return calcularFechaCaptura(fecha.getTime());
        }

        return fecha.getTime();
    }

    /**
     * Metodo que obtiene los dias inhabiles de BD
     * 
     * @param diasInhabiles
     * @return
     */
    public List<String> obtenerDiasInhabiles(List<DiaInhabil> diasInhabiles) {
        List<String> dias = new ArrayList<String>();
        for (DiaInhabil diaInhabil : diasInhabiles) {
            dias.add(diaInhabil.getFechaNoLaborable().toString());
        }

        return dias;
    }

}
