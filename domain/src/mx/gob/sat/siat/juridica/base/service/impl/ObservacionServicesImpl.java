/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service.impl;

import mx.gob.sat.siat.juridica.base.dao.ObservacionDao;
import mx.gob.sat.siat.juridica.base.dao.TramiteDao;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Observacion;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.base.service.ObservacionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para atender las peticiones del observacion.
 * 
 * @author Softtek
 * 
 */
@Service("observacionServices")
public class ObservacionServicesImpl extends BaseSerializableBusinessServices implements ObservacionServices {

    /** N&uacute;mero de versi&oacute;n */
    private static final long serialVersionUID = -4465439269873257374L;

    @Autowired
    private TramiteDao tramiteDao;

    @Autowired
    private ObservacionDao observacionDao;

    /**
     * M&eacute;todo para guardar observacion.
     */
    @Override
    public void guardarObservacion(Observacion observacion) {
        Tramite tramite = tramiteDao.obtenerTramitePorId(observacion.getTramite().getNumeroAsunto());
        observacion.setTramite(tramite);
        observacionDao.guardarObservacion(observacion);
    }

    /**
     * M&eacute;todo para obtener el &uacute;ltimo observacion del
     * tramite solicitado.
     */
    @Override
    public Observacion obtenerUltimaObservacionPorTramite(String idTramite) {
        Observacion observacion = observacionDao.obtenerUltimaObservacionPorTramite(idTramite);
        observacion.getTramite().getNumeroAsunto();

        return observacion;

    }

    @Override
    public Observacion obtenerObservacionById(Long idObservacion) {
        return observacionDao.obtenerObservacionPorId(idObservacion);
    }

    @Override
    public List<Observacion> obtenerObservacionesPorTramite(String numAsunto) {
        List<Observacion> observaciones = observacionDao.obtenerObservacionesPorTramite(numAsunto);
        if (observaciones != null) {
            for (Observacion obs : observaciones) {

                obs.getTramite().getEstadoTramite();
            }
        }
        return observaciones;
    }

}
