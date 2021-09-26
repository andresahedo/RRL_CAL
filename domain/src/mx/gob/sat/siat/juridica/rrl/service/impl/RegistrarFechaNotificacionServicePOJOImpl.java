/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.service.impl;

import mx.gob.sat.siat.juridica.base.dao.*;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.DiscriminadorConstants;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoNotificacion;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoDocumentoOficial;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.service.impl.BaseSerializableBusinessServices;
import mx.gob.sat.siat.juridica.rrl.service.RegistrarFechaNotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Service("RegistrarFechaNotificacionService")
public class RegistrarFechaNotificacionServicePOJOImpl extends BaseSerializableBusinessServices implements
        RegistrarFechaNotificacionService {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo tramiteDao tipo TramiteDao
     */
    @Autowired
    private TramiteDao tramiteDao;

    @Autowired
    private DocumentoDao documentoDao;

    @Autowired
    private NotificacionDao notificacionDao;

    /**
     * Atributo requerimientoDao tipo RequerimientoDao
     */
    @Autowired
    private RequerimientoDao requerimientoDao;

    /**
     * Atributo resolucionDao tipo ResolucionDao
     */
    @Autowired
    private ResolucionDao resolucionDao;

    @Override
    public Long guardarFechaNotificacion(String numAsunto, Boolean blnResolucion, Date fechaNotificacion,
            Long idNotificacion) {
  
        Notificacion notificacion = null;


        if (!blnResolucion) {
            Requerimiento req = requerimientoDao.obtenerUltimoRequerimientoPorTramiteRRLCAL(numAsunto);
            if (req != null) {
                notificacion = notificacionDao.obtenerNotificacionByIdRequerimiento(req.getIdRequerimiento());
                if (notificacion == null) {

                    notificacion = generarNotificacionRequerimiento(numAsunto);
                }
            }
            else {
                notificacion = generarNotificacionRequerimiento(numAsunto);
            }

        }
        else {
            Resolucion resolucion = resolucionDao.obtenerUltimaResolucionPorTramite(numAsunto);
            if (resolucion != null) {
                notificacion = notificacionDao.obtenerNotificacionByIdResolucion(resolucion.getIdResolucion());
                if (notificacion == null) {

                    notificacion = generarNotificacionAutorizacion(numAsunto);
                }
            }
            else {
                notificacion = generarNotificacionAutorizacion(numAsunto);
            }
        }

        notificacion.setFechaEnvioNotificacion(fechaNotificacion);
        notificacion.setFechaAcuseNotificacion(fechaNotificacion);
        notificacion.setEstadoNotificacion(EstadoNotificacion.NOTIFICADO_SISTEMA);
        notificacionDao.guardarNotificacion(notificacion);
        return notificacion.getIdNotificacion();

    }

    @Override
    public NotificacionRequerimiento obtenerNotificacionRequerimientoByReqId(Long idRequerimiento) {
        return notificacionDao.obtenerNotificacionByIdRequerimiento(idRequerimiento);
    }

    private NotificacionRequerimiento generarNotificacionRequerimiento(String numeroAsunto) {
        Tramite tramite = tramiteDao.obtenerTramitePorId(numeroAsunto);
        NotificacionRequerimiento notificacionRequerimiento = new NotificacionRequerimiento();

        if ((tramite.getSolicitud().getClaveModalidad().toString())
                .equals(DiscriminadorConstants.T1_CLASIFICACION_ARANCELARIA)) {
            notificacionRequerimiento.setRequerimiento(requerimientoDao
                    .obtenerUltimoRequerimientoPorTramiteRRLTarea(numeroAsunto));
        }
        else {
            notificacionRequerimiento.setRequerimiento(requerimientoDao
                    .obtenerUltimoRequerimientoPorTramiteCAL(numeroAsunto));
        }

        return notificacionRequerimiento;
    }

    private NotificacionResolucion generarNotificacionAutorizacion(String numeroAsunto) {

        NotificacionResolucion notificacionResolucion = new NotificacionResolucion();
        notificacionResolucion.setResolucion(resolucionDao.obtenerUltimaResolucionPorTramite(numeroAsunto));

        return notificacionResolucion;
    }

    @Override
    public Boolean validarFechaNotificacion(Date fechaNotificacion, String numeroAsunto) {
        Date fecha = new Date();
        Calendar fecRes = Calendar.getInstance();
        Calendar fecNot = Calendar.getInstance();
        Resolucion resolucion = resolucionDao.obtenerUltimaResolucionPorTramite(numeroAsunto);

        if (resolucion != null && resolucion.getIdeEstadoResolucion() != null) {
            String[] estadosResolucion =
                    { "ESTRES.AT", "AUTSEN0001", "AUTSEN0002", "AUTSEN0003", "AUTSEN0004", "AUTSEN0005", "CONSEN0001",
                            "CONSEN0002", "CONSEN0003", "CONSEN0004", "CONSEN0005" };

            if (Arrays.asList(estadosResolucion).contains(resolucion.getIdeEstadoResolucion().toUpperCase())) {
                fecha = resolucion.getFechaResolucion();
            }
        }
        else {
            Requerimiento requerimiento = requerimientoDao.obtenerUltimoRequerimientoPorTramiteCAL(numeroAsunto);
            if (requerimiento == null) {
                requerimiento = requerimientoDao.obtenerUltimoRequerimientoPorTramiteRRL(numeroAsunto);
            }

            fecha = requerimiento.getFechaAutorizacion();
        }
        fecNot.setTime(fechaNotificacion);
        Calendar calNot = Calendar.getInstance();
        calNot.set(fecNot.get(Calendar.YEAR), fecNot.get(Calendar.MONTH), fecNot.get(Calendar.DATE));

        fecRes.setTime(fecha);
        Calendar calRes = Calendar.getInstance();
        calRes.set(fecRes.get(Calendar.YEAR), fecRes.get(Calendar.MONTH), fecRes.get(Calendar.DATE));

        // fecnot es menor a fecres, si puede ser igual, entonces se
        // cambia el -1 por 0
        return (calNot.compareTo(calRes) < 0);

    }

    @Override
    public Date buscarFechaNotificacionByIdTramite(String numeroAsunto) {
        Notificacion notificacion;
        Resolucion resolucion = resolucionDao.obtenerUltimaResolucionPorTramite(numeroAsunto);

        notificacion = notificacionDao.obtenerNotificacionByIdResolucion(resolucion.getIdResolucion());

        if (notificacion != null) {
            return notificacion.getFechaEnvioNotificacion();
        }
        else {
            return null;
        }
    }

    @Override
    public Date buscarFechaNotificacionByIdRequerimiento(Long idRequerimiento) {
        Notificacion notificacion;

        notificacion = notificacionDao.obtenerNotificacionByIdRequerimiento(idRequerimiento);

        if (notificacion != null) {
            return notificacion.getFechaEnvioNotificacion();
        }
        else {
            return null;
        }
    }

    @Override
    public List<DocumentoOficial> buscarTiposDocumentosPorIdTramite(String numAsunto) {

        return documentoDao.obtenerDocumentosPorTipo(numAsunto, TipoDocumentoOficial.ACTA_NOTIFICACION.getClave());
    }

    @Override
    public List<DocumentoOficial> buscarTiposDocumentosAnexadosPorIdTramite(String numAsunto) {

        return documentoDao.obtenerDocumentosOficialesAnexadosPorTipo(numAsunto,
                TipoDocumentoOficial.ACTA_NOTIFICACION.getClave());
    }

    @Override
    public Notificacion buscarNotificacionParcial(String numAsunto, Boolean blnResolucion) {
        Notificacion notificacion = null;
        if (!blnResolucion) {
            Requerimiento req = requerimientoDao.obtenerUltimoRequerimientoPorTramiteRRLCAL(numAsunto);
            if (req != null) {
                notificacion = notificacionDao.obtenerNotificacionByIdRequerimiento(req.getIdRequerimiento());
            }
        } else {
            Resolucion resolucion = resolucionDao.obtenerUltimaResolucionPorTramite(numAsunto);
            if (resolucion != null) {
                notificacion = notificacionDao.obtenerNotificacionByIdResolucion(resolucion.getIdResolucion());
            }
        }
        return notificacion;
    }

}
