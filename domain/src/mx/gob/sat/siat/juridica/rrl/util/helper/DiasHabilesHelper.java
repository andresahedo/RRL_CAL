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
import mx.gob.sat.siat.juridica.base.dao.DiaInhabilDAO;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.DiaInhabil;
import mx.gob.sat.siat.juridica.base.helper.BaseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * @author softtek
 * 
 */
@Component
public class DiasHabilesHelper extends BaseHelper {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1218822943043637109L;

    /**
     * Atributo diaInHabilDAO tipo DiaInhabilDAO
     */
    @Autowired
    private DiaInhabilDAO diaInhabilDAO;

    @Autowired
    private FechaCapturaHelper fechaCapturaHelper;

    /**
     * 
     * @param idSolicitud
     * @return
     */
    public Integer calcularDiasFaltantesSolParcial(Date fechaCreacionSolicitud) {

        return NumerosConstantes.TRES - calcularDiasTranscurridos(fechaCreacionSolicitud, new Date());
    }

    /**
     * Metodo para calcular los dias transcurridos
     * 
     * @param fechaInicio
     * @param fechaFin
     * @return contadorDias
     */
    public Integer calcularDiasTranscurridos(Date fechaInicio, Date fechaFin) {

        Integer contadorDias = 0;
        Date fechaInicioNueva = null;

        if (fechaInicio != null) {
            fechaInicioNueva = fechaInicio;
        }
        else {
            fechaInicioNueva = new Date();
        }

        long diferenciaDias = dateDifference(fechaInicioNueva, fechaFin);
        long finesSemana = buscaFinSemana(fechaInicioNueva, fechaFin);
        int diasFeriados = diaInhabilDAO.numeroDiasFeriadosEnCalendario(fechaInicioNueva, fechaFin);
        contadorDias = (int) (diferenciaDias - (diasFeriados + finesSemana));
        if (contadorDias < 0) {
            contadorDias = 0;
        }
        return contadorDias;
    }

    public Date sumarDiasInhabiles(int dias, Date fecha) {

        Calendar cal = Calendar.getInstance();

        cal.setTime(fecha);

        cal.add(Calendar.DATE, dias);

        return cal.getTime();
    }

    public Date limpiarFecha(Date fecha) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        return cal.getTime();
    }

    public int obtenerDiasInhabiles(Date fechaInicio, Date fechaFin) {
        int contadorDias = 0;
        Date fechaStart = limpiarFecha(fechaInicio);
        Date fechaEnd = limpiarFecha(fechaFin);
        long finesSemana = buscaFinSemana(fechaStart, fechaEnd);
        int diasFeriados = diaInhabilDAO.numeroDiasFeriadosEnCalendario(fechaStart, fechaEnd);
        contadorDias = (int) ((diasFeriados + finesSemana));

        return contadorDias;
    }

    public int obtenerDiasInhabiles(Date fechaInit, int diasFechaFin) {
        int contadorDias = 0;
        Date fechaInicio = limpiarFecha(fechaInit);
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaInicio);
        cal.add(Calendar.DATE, diasFechaFin);
        Date fechaFin = cal.getTime();
        fechaFin = limpiarFecha(fechaFin);
        long finesSemana = buscaFinSemana(fechaInicio, fechaFin);
        int diasFeriados = diaInhabilDAO.numeroDiasFeriadosEnCalendario(fechaInicio, fechaFin);
        contadorDias = (int) ((diasFeriados + finesSemana));

        return contadorDias;
    }

    /**
     * Metodo para obtener la diferencia entre la fecahde inicio y
     * fecha de fin
     * 
     * @param startDate
     * @param endDate
     * @return diferencia
     */
    private long dateDifference(Date startDate, Date endDate) {
        Calendar calStart = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
        calStart.setTime(startDate);
        calEnd.setTime(endDate);
        calStart.set(Calendar.HOUR_OF_DAY, 0);
        calStart.set(Calendar.MINUTE, 0);
        calStart.set(Calendar.SECOND, 0);
        calEnd.set(Calendar.HOUR_OF_DAY, 0);
        calEnd.set(Calendar.MINUTE, 0);
        calEnd.set(Calendar.SECOND, 0);
        long diff = calEnd.getTimeInMillis() - calStart.getTimeInMillis();
        long diferencia = 0;
        diferencia = (diff / NumerosConstantes.MILIS_POR_DIA);
        return diferencia;
    }

    /**
     * Metodo para buscar una semana
     * 
     * @param fechaInicial
     * @param fechaFinal
     * @return diffDays
     */
    public int buscaFinSemana(Date fechaInicial, Date fechaFinal) {
        Calendar calFechIni = Calendar.getInstance();
        Calendar calFechFin = Calendar.getInstance();
        calFechIni.setTime(fechaInicial);
        calFechFin.setTime(fechaFinal);
        int diffDays = 0;
        while (calFechIni.before(calFechFin) || calFechIni.equals(calFechFin)) {
            if (calFechIni.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
                    || calFechIni.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                diffDays++;
            }
            calFechIni.add(Calendar.DATE, 1);
        }
        return diffDays;
    }

    public Date obtenerFechaDiasHabiles(Date fechaIni, int dias) {

        Date fechaIniInterna = limpiarFecha(fechaIni);
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaIniInterna);
        // Se suma uno por uno el numero de dias y cada uno se valida
        // si es inhabil o fin de semana
        // y se reubica hasta el siguiente dia habil para continuar
        // con la suma.
        for (int i = 0; i < dias; i++) {
            Date fechaHabil;
            cal.add(Calendar.DAY_OF_MONTH, 1);
            try {
                fechaHabil = fechaCapturaHelper.calcularFechaCaptura(cal.getTime());
            }
            catch (ParseException e) {
                fechaHabil = cal.getTime();
            }
            cal.setTime(fechaHabil);
        }
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public List<String> obtenerDiasInhabiles(List<DiaInhabil> diasInhabiles) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<String> dias = new ArrayList<String>();
        for (DiaInhabil diaInhabil : diasInhabiles) {
            dias.add(dateFormat.format(diaInhabil.getFechaNoLaborable()));
        }

        return dias;
    }

    /**
     * Metodo para obtener los minutos
     * 
     * @param parametro
     * @return minutos
     */
    public long obtenerTiempoMinutosParametro(String parametro) {

        double dias = Double.parseDouble(parametro);
        return calcularMinutosDiaHabil(dias);

    }

    /**
     * Metodo para calcular los minutos de una dia habil
     * 
     * @param numeroDias
     * @return
     */
    public long calcularMinutosDiaHabil(double numeroDias) {
        return Math.round(numeroDias * NumerosConstantes.VEINTICUATRO * NumerosConstantes.SESENTA);
    }

    public Date siguienteDiaHabil(Date dia, List<Date> diasInhabiles) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(dia);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            cal.add(Calendar.DAY_OF_WEEK, 1);
        }
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            cal.add(Calendar.DAY_OF_WEEK, 1);
        }
        for (Date diaInhabil : diasInhabiles) {
            if (diaInhabil.compareTo(new Date(cal.getTime().getTime())) == 0) {
                cal.add(Calendar.DATE, 1);
            }
        }
        if ((new Date(dia.getTime())).compareTo((new Date(cal.getTime().getTime()))) == 0) {
            return dia;
        }
        else {
            return siguienteDiaHabil(cal.getTime(), diasInhabiles);
        }
    }
}
