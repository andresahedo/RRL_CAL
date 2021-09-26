package mx.gob.sat.siat.juridica.cal.op.service.impl;

import mx.gob.sat.siat.juridica.base.dao.DocumentoDao;
import mx.gob.sat.siat.juridica.base.dao.PersonaDao;
import mx.gob.sat.siat.juridica.base.dao.TramiteDao;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoDocumento;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoMensajeBPM;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.service.impl.BaseSerializableBusinessServices;
import mx.gob.sat.siat.juridica.bpm.dao.domain.MensajeTarea;
import mx.gob.sat.siat.juridica.bpm.dao.domain.TransicionBPM;
import mx.gob.sat.siat.juridica.bpm.helper.XMLHelper;
import mx.gob.sat.siat.juridica.cal.op.service.MotivosRechazoServices;
import mx.gob.sat.siat.juridica.oficialia.dao.MotivosDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("motivosRechazoServices")
public class MotivosRechazoServicesImpl extends BaseSerializableBusinessServices implements MotivosRechazoServices {

    /**
     * 
     */
    private static final long serialVersionUID = 6354618231049116498L;

    @Autowired
    private MotivosDao motivosDao;

    @Autowired
    private TramiteDao tramiteDao;

    @Autowired
    private XMLHelper xmlHelper;


    @Autowired
    private DocumentoDao documentoDao;

    @Autowired
    private PersonaDao personaDao;

    @Override
    public List<MotivoRechazo> obtenerMotivosRechazoOficialia() {
        return motivosDao.obtenerMotivosRechazo();
    }

    public void rechazoAsuntoOficialia(MotivoRechazo motivoRechazo, String rfcAutorizador, String idTarea) {

        Tramite tramite = tramiteDao.obtenerTramitePorId(motivoRechazo.getIdTramite());
        tramite.setEstadoTramite(EstadoTramite.RECHAZADO);
        tramiteDao.modificarTramite(tramite);

        motivosDao.guardarMotivo(motivoRechazo);

        MensajeBPM mensajeBPM = new MensajeBPM();
        mensajeBPM.setTipoMensajeBPM(TipoMensajeBPM.COMPLETAR_TAREA);
        String rfcCorto = personaDao.obtenerRfcCorto(rfcAutorizador);
        mensajeBPM.setUsuario(rfcCorto);
        mensajeBPM.setIdTarea(idTarea);
        MensajeTarea mensajeTarea = new MensajeTarea();
        mensajeTarea.setEstadoProcesal(tramite.getEstadoTramite().getClave());
        TransicionBPM transicionBPM = new TransicionBPM();
        transicionBPM.setTipoTransicion(TipoMensajeBPM.RECHAZO_TAREA.getClave());
        mensajeTarea.setTransicion(transicionBPM);

        mensajeBPM.setbDoc(xmlHelper.convertToXMLWithNoReferences(mensajeTarea));

    }

    @Override
    public void rechazoDocumento(MotivoRechazo motivoRechazo, String rfc) {

        DocumentoSolicitud documentoSol =
                documentoDao.obtenerDocumentoSolPorIdDocumento(motivoRechazo.getIdSolDocumento());
        Documento documento = documentoDao.obtenerDocumentoPorId(motivoRechazo.getIdSolDocumento());

        documentoSol.setEstadoDocumentoSolicitud(EstadoDocumento.RECHAZADO.getClave());
        documento.setEstadoDocumento(EstadoDocumento.RECHAZADO.getClave());

        motivoRechazo.setIdSolDocumento(documentoSol.getIdDocumentoSolicitud());
        motivosDao.guardarMotivo(motivoRechazo);

    }

    @Override
    public String obtenerModalidadTramitePorClave(Integer tipoTramite) {
        return tramiteDao.obtenerModalidadPorIdTipoTramite(tipoTramite);
    }

    @Override
    public List<MotivoRechazo> obtenerTramitesDoctosRechazadosPorFiltros(String numeroAsunto, String estado,
            Date fechaIni, Date fechaFin, String rfc) {
        List<MotivoRechazo> listaMotivoRechazo =
                motivosDao.obtenerListaMotivoPorFiltros(numeroAsunto, estado, fechaIni, fechaFin, rfc);
        for (MotivoRechazo motivoRechazo : listaMotivoRechazo) {
            if (motivoRechazo.getTramite() != null) {
                motivoRechazo.getTramite().getEstadoTramite();
                motivoRechazo.getTramite().getSolicitud();
                motivoRechazo.getDocumentoSolicitud();
                motivoRechazo.getTramite().getNumeroAsunto();
                motivoRechazo.getTramite().getFechaInicioTramite();
                motivoRechazo.getTramite().getSolicitud().getDiscriminatorValue();
                motivoRechazo.getTramite().getSolicitud().getTipoTramite().getDescripcionModalidad();
                motivoRechazo.getCveRechazo();
                motivoRechazo.getIdUsuario();
                motivoRechazo.getTramite().getFechaInicioTramite();
                motivoRechazo.getCveRechazo();
                motivoRechazo.getFechaRechazo();

                if (motivoRechazo.getDocumentoSolicitud() != null) {
                    motivoRechazo.getDocumentoSolicitud().getIdTipoDocumento();
                }
            }
        }
        return listaMotivoRechazo;
    }

    @Override
    public MotivoRechazo obtenerMotivoPorNumAsunto(String numeroAsunto) {
        return motivosDao.obtenermotivoPorNumAsunto(numeroAsunto);
    }

}
