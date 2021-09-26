package mx.gob.sat.siat.juridica.cal.service.impl;

import mx.gob.sat.siat.juridica.base.dao.EnumeracionDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.*;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.service.BalanceadorServices;
import mx.gob.sat.siat.juridica.base.service.TipoTramiteServices;
import mx.gob.sat.siat.juridica.bpm.dao.domain.ParametroTramiteBPM;
import mx.gob.sat.siat.juridica.ca.util.constants.RegistroSolicitudConstants;
import mx.gob.sat.siat.juridica.cal.service.SolicitudCALServices;
import mx.gob.sat.siat.juridica.rrl.service.impl.SolicitudServiceImpl;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("solicitudCALServices")
public class SolicitudCALServicesImpl extends SolicitudServiceImpl implements SolicitudCALServices {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo enumeracionDao tipo EnumeracionDao
     */
    @Autowired
    private EnumeracionDao enumeracionDao;
    @Autowired
    private transient TipoTramiteServices tipoTramiteServices;
    @Autowired
    private transient BalanceadorServices balanceadorServices;

    @Override
    public void agregaDatos(mx.gob.sat.siat.juridica.bpm.dao.domain.Tramite inicioTramite) {

        String fechaVencAutorizarConfReq =
                enumeracionDao.obtenerParametroByEnum(EnumeracionParametro.DIAS_CONF_NOTIF_REQ_AUTORIDAD.getClave());
        String fechaVencAtenReq =
                enumeracionDao.obtenerParametroByEnum(EnumeracionParametro.DIAS_ATENDER_REQ_INFORMACION_CAL.getClave());
        String fechaVencReq =
                enumeracionDao.obtenerParametroByEnum(EnumeracionParametro.DIAS_CONF_NOTIF_REQUERIMIENTO.getClave());

        ParametroTramiteBPM parametroTramiteBPM = new ParametroTramiteBPM();

        try {
            if (fechaVencAtenReq != null) {
                parametroTramiteBPM.setTiempoTimerAtenReq(String.valueOf(fechaVencAtenReq));
            }
        }
        catch (NumberFormatException e) {
            parametroTramiteBPM.setTiempoTimerAtenReq(null);
        }

        try {
            if (fechaVencAutorizarConfReq != null) {
                parametroTramiteBPM.setTiempoTimerConfReq(String.valueOf(fechaVencAutorizarConfReq));
            }
        }
        catch (NumberFormatException e) {
            parametroTramiteBPM.setTiempoTimerConfReq(null);
        }

        try {
            if (fechaVencReq != null) {
                parametroTramiteBPM.setTiempoTimerReq(String.valueOf(fechaVencReq));
            }
        }
        catch (NumberFormatException e) {
            parametroTramiteBPM.setTiempoTimerReq(null);
        }

        inicioTramite.setParametroTramite(parametroTramiteBPM);
    }

    public List<Object> firmarSolicitud(Long idSolicitud, String rfcSolicitante, String usuario, Firma firma) {
    
        MensajeBPM mensajeBPM = new MensajeBPM();
        mensajeBPM.setTipoMensajeBPM(TipoMensajeBPM.INICIAR_TRAMITE_JSON);
        mensajeBPM.setBpdId(ProcesosBPM.CONSULTAS_Y_AUTORIZACIONES.getBpdId());
        mensajeBPM.setProcessAppId(ProcesosBPM.CONSULTAS_Y_AUTORIZACIONES.getProcessAppId());

        // Asume secuencia de autorizaciones
        String prefijoFolio = DiscriminadorConstants.PREFIJO_CAL_AUTORIZACIONES;
        String tipoTramiteSecuencia = DiscriminadorConstants.T2_BASE_AUTORIZACIONES;
        Solicitud solT2 = this.obtenerSolicitudporId(idSolicitud);
        List<TipoTramite> listaTipoTramite =
                tipoTramiteServices.obtenerTipoTramiteModulo(solT2.getTipoTramiteSolicitud().getIdTipoTramite()
                        .toString());
        if (listaTipoTramite != null && !listaTipoTramite.isEmpty()
                && listaTipoTramite.get(0).getSubservicio().equals(RegistroSolicitudConstants.MODALIDADES_CONSULTA)) {
            prefijoFolio = DiscriminadorConstants.PREFIJO_CAL_CONSULTAS;
            tipoTramiteSecuencia = DiscriminadorConstants.T2_BASE_CONSULTAS;
        }

        Tramite tramite =
                this.generarTramite(idSolicitud,
                        firma, null, prefijoFolio, tipoTramiteSecuencia);

        this.actualizaEstadoSolicitud(idSolicitud, EstadoSolicitud.RECIBIDA);
        this.guardarTramite(tramite);

        String administradorResponsable = null;

        ResultadoAdminResponsable resultado1 =
                balanceadorServices
                        .getAdministradorResponsableUnidadLocalRecaudadoraCAL((SolicitudDatosGenerales) tramite
                                .getSolicitud());

        SolicitudDatosGenerales sol =
                this.obtenerSolicitudConsultaAutorizacionporId(idSolicitud);
        if (resultado1 != null) {
            administradorResponsable = resultado1.getRfcAdministrador();
            if (resultado1.isAdministradorDefault()) {
                sol.setUnidadAdminBalanceo(resultado1.getUnidadAdmin());
                this.actualizarUnidadAdminBalanceo(sol, resultado1.getUnidadAdmin());
            }
        }
        mx.gob.sat.siat.juridica.bpm.dao.domain.Tramite inicioTramite =
                this.generarMensajeInicio(sol, tramite, usuario, rfcSolicitante,
                        administradorResponsable);
        JSONObject dDoc = new JSONObject();
        dDoc.put("tramite", new JSONObject(inicioTramite));
        mensajeBPM.setbDoc(dDoc.toString());
    
        List<Object> resultados = new ArrayList<Object>();
        resultados.add(mensajeBPM);
        resultados.add(sol);
        resultados.add(administradorResponsable);
        resultados.add(inicioTramite);
        resultados.add(tramite);
        
        return resultados;
    }
}
