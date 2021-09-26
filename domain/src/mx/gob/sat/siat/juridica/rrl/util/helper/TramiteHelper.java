/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.util.helper;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.DiaInhabil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Component
public class TramiteHelper implements Serializable {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private DiasHabilesHelper diasHabilesHelper;

    /**
     * Metodo para generar un numero de asunto
     * 
     * @param prefijo
     * @param anio
     * @param secuencia
     * @return sb
     */
    public String generarNumeroAsunto(String prefijo, String anio, String secuencia) {
        StringBuffer sb =
                new StringBuffer().append(prefijo).append(anio)
                        .append(String.format("%06d", Integer.parseInt(secuencia)));

        return sb.toString();
    }

    /**
     * Metodo para obtener la fecha de vencimiento
     * 
     * @param fechaPromocion
     * @param listaDias
     * @return fechaVencimiento
     */
    public Date fechaVencimientoTramite(Date fechaPromocion, List<DiaInhabil> listaDias) {
        Calendar calFechIni = Calendar.getInstance();
        calFechIni.setTime(fechaPromocion);

        List<Date> diasInhabiles = this.obtenerDiasInhabiles(listaDias);
        Calendar fechaVencimiento = Calendar.getInstance();
        Date fechaActual = new Date();
        fechaActual = diasHabilesHelper.limpiarFecha(fechaActual);
        fechaActual = diasHabilesHelper.siguienteDiaHabil(fechaActual, diasInhabiles);
        fechaVencimiento.setTime(fechaActual);
        fechaVencimiento.set(fechaVencimiento.get(Calendar.YEAR), fechaVencimiento.get(Calendar.MONTH),
                fechaVencimiento.get(Calendar.DAY_OF_MONTH));
        fechaVencimiento.add(Calendar.MONTH, NumerosConstantes.TRES);
        fechaVencimiento.setTime(diasHabilesHelper.siguienteDiaHabil(fechaVencimiento.getTime(), diasInhabiles));

        return fechaVencimiento.getTime();
    }

    /**
     * Metodo para obtener la fecha de vencimiento
     * 
     * @param fechaPromocion
     * @param listaDias
     * @return fechaVencimiento
     */
    public Date fechaVencimientoTramiteClasifArancelaria(Date fechaPromocion, List<DiaInhabil> listaDias) {
        Calendar calFechIni = Calendar.getInstance();
        calFechIni.setTime(fechaPromocion);

        List<Date> diasInhabiles = this.obtenerDiasInhabiles(listaDias);
        Calendar fechaVencimiento = Calendar.getInstance();
        Date fechaActual = new Date();
        fechaActual = diasHabilesHelper.limpiarFecha(fechaActual);
        fechaActual = diasHabilesHelper.siguienteDiaHabil(fechaActual, diasInhabiles);
        fechaVencimiento.setTime(fechaActual);
        fechaVencimiento.set(fechaVencimiento.get(Calendar.YEAR), fechaVencimiento.get(Calendar.MONTH),
                fechaVencimiento.get(Calendar.DAY_OF_MONTH));
        fechaVencimiento.add(Calendar.MONTH, NumerosConstantes.CUATRO);
        fechaVencimiento.setTime(diasHabilesHelper.siguienteDiaHabil(fechaVencimiento.getTime(), diasInhabiles));

        return fechaVencimiento.getTime();
    }

    /**
     * Metodo para obtener los dias inhabiles
     * 
     * @param diasInhabiles
     * @return dias
     */
    public List<Date> obtenerDiasInhabiles(List<DiaInhabil> diasInhabiles) {
        List<Date> dias = new ArrayList<Date>();
        for (DiaInhabil diaInhabil : diasInhabiles) {
            dias.add(diaInhabil.getFechaNoLaborable());
        }
        return dias;
    }

}
