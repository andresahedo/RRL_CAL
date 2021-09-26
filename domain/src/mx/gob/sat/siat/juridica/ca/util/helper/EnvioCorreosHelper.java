/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.ca.util.helper;

import mx.gob.sat.siat.juridica.base.dao.TipoTramiteDAO;
import mx.gob.sat.siat.juridica.base.dao.TramiteDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoReproceso;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoErrorEnvioBPM;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Requerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.base.dao.domain.model.TramiteReprocesar;
import mx.gob.sat.siat.juridica.base.helper.BaseHelper;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.domain.model.DatosCorreoNotificacion;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author softtek
 * 
 */
@Component
public class EnvioCorreosHelper extends BaseHelper {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 3670592720116588157L;
    
    private static final String DATE_FORMAT_STR = "dd/MM/yyyy HH:mm:ss";

    /**
     * Atributo privado "tramiteDao" tipo TramiteDao
     */
    @Autowired
    private TramiteDao tramiteDao;

    /**
     * Atributo privado "tipoTramiteDAO" tipo TipoTramiteDAO
     */
    @Autowired
    private TipoTramiteDAO tipoTramiteDAO;

    /**
     * Metodo que crea un correo de aviso de tarea pendiente
     * 
     * @param numeroAsunto
     * @param persona
     * @return
     */
    public Map<String, String> correoAvisoTareaPendiente(String numeroAsunto, Persona persona) {

        DateFormat dia = new SimpleDateFormat("dd");
        DateFormat mes = new SimpleDateFormat("MM");
        DateFormat year = new SimpleDateFormat("yyyy");
        DateFormat fechaInicioFormat = new SimpleDateFormat(DATE_FORMAT_STR);
        Date hoy = new Date();
        Tramite tramite = tramiteDao.obtenerTramitePorId(numeroAsunto);
        Map<String, String> datos = new HashMap<String, String>();
        datos.put("dia", dia.format(hoy));
        datos.put("mes", mes.format(hoy));
        datos.put("year", year.format(hoy));
        datos.put("personaNombre",
                StringEscapeUtils.escapeHtml4(persona.getNombre() + " " + 
                   persona.getApellidoPaterno() + " " + persona.getApellidoMaterno()));
        datos.put("numeroAsunto", tramite.getNumeroAsunto());
        datos.put("descripcionServicio",
                StringEscapeUtils.escapeHtml4(tipoTramiteDAO.obtenerDescripcionServicio(
                    tramite.getSolicitud().getTipoTramite().getIdTipoTramite())));
        datos.put("fechaInicioTramite", fechaInicioFormat.format(tramite.getSolicitud().getFechaInicioTramite()));

        return datos;
    }

    /**
     * Metodo que crea un correo de aviso de tarea pendiente
     * 
     * @param numeroAsunto
     * @param persona
     * @return
     */
    public Map<String, String> correoAutoridad(String numeroAsunto) {

        DateFormat fechaInicioFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat horaInicioFormat = new SimpleDateFormat("HH:mm:ss");
        Date hoy = new Date();
        Tramite tramite = tramiteDao.obtenerTramitePorId(numeroAsunto);
        Map<String, String> datos = new HashMap<String, String>();
        datos.put("fechaHora", horaInicioFormat.format(hoy));
        datos.put("fechaEnvio", fechaInicioFormat.format(hoy));
        datos.put("descripcionModalidad",
                StringEscapeUtils.escapeHtml4(tipoTramiteDAO.findById(tramite.getSolicitud().getTipoTramite().getIdTipoTramite())));
        datos.put("numeroAsunto", tramite.getNumeroAsunto());

        return datos;
    }

    /**
     * Metodo que crea un correo de aviso de tarea pendiente
     * 
     * @param numeroAsunto
     * @param persona
     * @return
     */
    public Map<String, String> correoRecepcionDocumentos(String numeroAsunto, Persona persona) {

        DateFormat fechaInicioFormat = new SimpleDateFormat(DATE_FORMAT_STR);
        Date hoy = new Date();
        Tramite tramite = tramiteDao.obtenerTramitePorId(numeroAsunto);
        Map<String, String> datos = new HashMap<String, String>();
        datos.put("numeroAsunto", tramite.getNumeroAsunto());
        datos.put(
                "nombreContribuyente",
                StringEscapeUtils.escapeHtml4(persona.getNombre() != null ? (persona.getNombre() + " " + persona.getApellidoPaterno() + " " + persona
                        .getApellidoMaterno()) : persona.getRazonSocial()));
        datos.put("rfcContribuyente", persona.getRfc());
        datos.put("fechaHora", fechaInicioFormat.format(hoy));

        return datos;
    }

    public Map<String, String> correoNotificacionActualizacionMovimientos(DatosCorreoNotificacion datosCorreo) {
        String noAplica = "No aplica";
        DateFormat fechaFormat = new SimpleDateFormat(DATE_FORMAT_STR);
        Date hoy = new Date();
        Map<String, String> datos = new HashMap<String, String>();
        datos.put("fechaActualizacion", fechaFormat.format(hoy));
        datos.put("numeroEmpleado", datosCorreo.getNumeroEmpleado().toString());
        datos.put("nombre", StringEscapeUtils.escapeHtml4(datosCorreo.getNombre()));
        datos.put("apPaterno", StringEscapeUtils.escapeHtml4(datosCorreo.getApPaterno()));
        datos.put("apMaterno", StringEscapeUtils.escapeHtml4(datosCorreo.getApMaterno()));
        datos.put("rfc", StringEscapeUtils.escapeHtml4(datosCorreo.getRfc()));
        datos.put("corto", datosCorreo.getRfcCorto());
        datos.put("mail", datosCorreo.getMail());
        if (datosCorreo.getDescripcionPermiso() != null && !datosCorreo.getDescripcionPermiso().equals("")) {
            datos.put("permiso", StringEscapeUtils.escapeHtml4(datosCorreo.getDescripcionPermiso()));
        }
        else {
            datos.put("permiso", noAplica);
        }
        datos.put("tipoMovimiento", StringEscapeUtils.escapeHtml4(datosCorreo.getTipoMovimiento().getDescripcion()));
        datos.put("edoActualizacion", StringEscapeUtils.escapeHtml4(datosCorreo.getDescripcionEdoActualizacion()));
        if (datosCorreo.getExcepcion() == null) {
            datos.put("excepcion", noAplica);
        }
        else {
            datos.put("excepcion", StringEscapeUtils.escapeHtml4(datosCorreo.getExcepcion().getDescripcion()));
        }

        return datos;
    }

    public Map<String, String> correoCumplimentacionRequerimiento(Requerimiento requerimiento,
            String nombreFuncionario, String unidadAdmin, String numAsunto) {
        DateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
        Map<String, String> datos = new HashMap<String, String>();
        datos.put("nombreFuncionario", StringEscapeUtils.escapeHtml4(nombreFuncionario));
        datos.put("uniAdmin", StringEscapeUtils.escapeHtml4(unidadAdmin));
        datos.put("numAsunto", numAsunto);
        datos.put("fechaCreacionRequerimiento", formatDate.format(requerimiento.getFechaCreacion()));
        datos.put("fechaNotificacion", formatDate.format(requerimiento.getFechaVerificacion()));
        datos.put("fechaCumplimentacion", formatDate.format(requerimiento.getFechaAtencion()));
        return datos;
    }

    public Map<String, String> correoRechazoAsunto(String nombreFuncionario, String unidadAdmin, String numAsunto,
            String elemento, Date fechaRecepcion, String nombreRechazo) {
        DateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
        Map<String, String> datos = new HashMap<String, String>();
        datos.put("nombreFuncionario", StringEscapeUtils.escapeHtml4(nombreFuncionario));
        datos.put("uniAdmin", StringEscapeUtils.escapeHtml4(unidadAdmin));
        datos.put("numAsunto", numAsunto);
        datos.put("elemento", elemento);
        datos.put("fechaRecepcion",
                fechaRecepcion != null ? formatDate.format(fechaRecepcion) : formatDate.format(new Date()));
        datos.put("nomRechazo", StringEscapeUtils.escapeHtml4(nombreRechazo));
        return datos;
    }
    
    public Map<String, String> correoTramitesReprocesados(List<TramiteReprocesar> reprocesos) {
        Map<String, String> datos = new HashMap<String, String>();
        StringBuilder tableBuilder = new StringBuilder();
        // Se define la tabla.
        tableBuilder.append("<table>");

        // Se definen encabezados
        tableBuilder.append("<thead>");
        tableBuilder.append("<tr>");
        tableBuilder.append("<th>Asunto</th>");
        tableBuilder.append("<th>Descripci&oacute;n de Error</th>");
        tableBuilder.append("<th>Estado reproceso</th>");
        tableBuilder.append("</tr>");
        tableBuilder.append("</thead>");

        // Creamos el cuerpo de la tabla
        tableBuilder.append("<tbody>");
        for (TramiteReprocesar tramiteReprocesar : reprocesos) {
            tableBuilder.append("<tr>");

            tableBuilder.append("<td>");
            tableBuilder.append(tramiteReprocesar.getNumeroAsunto());
            tableBuilder.append("</td>");
            tableBuilder.append("<td>");
            tableBuilder.append(TipoErrorEnvioBPM.parse(tramiteReprocesar.getTipoError()).getDescripcion());
            tableBuilder.append("</td>");
            tableBuilder.append("<td>");
            tableBuilder.append(EstadoReproceso.parse(tramiteReprocesar.getEstatusEnvio()).getDescripcion());
            tableBuilder.append("</td>");

            tableBuilder.append("</tr>");
        }

        tableBuilder.append("</tbody>");
        tableBuilder.append("</table>");

        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_STR);
        Date date = new Date();

        datos.put("tabla", tableBuilder.toString());
        datos.put("fecha", dateFormat.format(date));
        return datos;
    }

    public String concatenarNombreFuncionario(Persona persona) {
        StringBuffer nombre = new StringBuffer();
        nombre.append(persona.getNombre());
        nombre.append(" ");
        nombre.append(persona.getApellidoPaterno());
        nombre.append(" ");
        nombre.append(persona.getApellidoMaterno());
        return nombre.toString();
    }

}
