package mx.gob.sat.siat.juridica.rrl.api.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.constants.DiscriminadorConstants;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.FirmaTransformer;
import mx.gob.sat.siat.juridica.base.service.FirmarServices;
import mx.gob.sat.siat.juridica.cal.service.GenerarResolucionCALService;
import mx.gob.sat.siat.juridica.rrl.api.FirmarAutorizacionResolucionFacade;
import mx.gob.sat.siat.juridica.rrl.service.AutorizarRemitirService;
import mx.gob.sat.siat.juridica.rrl.service.FirmaTareaService;
import mx.gob.sat.siat.juridica.rrl.service.SolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component("firmarAutorizacionResolucionFacade")
public class FirmarAutorizacionResolucionFacadeImpl implements FirmarAutorizacionResolucionFacade {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private transient FirmarServices firmarServices;

    @Autowired
    private transient FirmaTransformer firmaTransformer;

    @Autowired
    private transient GenerarResolucionCALService generarResolucionCALService;

    @Autowired
    private transient AutorizarRemitirService autorizarRemitirService;

    @Autowired
    private transient FirmaTareaService firmaTareaService;

    @Autowired
    private transient SolicitudService solicitudService;

    public void guardarFirma(FirmaDTO firmaDTO, Long idResol) {
        Firma firma = firmaTransformer.transformarDTO(firmaDTO);
        firmarServices.guardarFrima(firma);
        FirmaResolucion firmaResol = new FirmaResolucion();
        firmaResol.setFirmaResolucionPK(new FirmaResolucionPK());
        firmaResol.getFirmaResolucionPK().setIdFirma(firma.getIdFirma());
        firmaResol.getFirmaResolucionPK().setIdResolucion(idResol);
        firmarServices.guardarFirmaAutorizacionResolucion(firmaResol);
    }

    @Override
    public void guardarFirmaRemitir(FirmaDTO firmaDTO, String numAsunto, Long idTarea, String rfcUsuario) {
        firmaTareaService.firmarRemitir(numAsunto, idTarea, rfcUsuario);
        Firma firma = firmaTransformer.transformarDTO(firmaDTO);
        firmarServices.guardarFrima(firma);
        FirmaRemision firmaRem = new FirmaRemision();
        firmaRem.setFirmaRemisionPK(new FirmaRemisionPK());
        firmaRem.getFirmaRemisionPK().setIdFirma(firma.getIdFirma());
        firmarServices.guardarFirmaAutorizacionRemision(firmaRem, numAsunto);
    }

    @Override
    public void guardarFirmaNotificacion(FirmaDTO firmaDTO, Long idNotificacion) {
        Firma firma = firmaTransformer.transformarDTO(firmaDTO);
        firmarServices.guardarFrima(firma);
        FirmaNotificacion firmaNot = new FirmaNotificacion();
        firmaNot.setFirmaNotificacionPK(new FirmaNotificacionPK());
        firmaNot.getFirmaNotificacionPK().setIdFirma(firma.getIdFirma());
        firmaNot.getFirmaNotificacionPK().setIdNotificacion(idNotificacion);
        firmarServices.guardarFirmaNotificacion(firmaNot);
    }

    @Override
    public String generaCadenaOriginal(long idSolicitud, Date fechaFirma) {
        if (solicitudService.obtenerSolicitudporId(idSolicitud).getTipoTramiteSolicitud().getIdTipoTramite().toString()
                .startsWith(DiscriminadorConstants.T2_TIPO_TRAMITE_PREFIJO)) {
            return generarResolucionCALService.generaCadenaOriginal(idSolicitud, fechaFirma, "");
        }
        else {
            return ""; 
            // TODO Implementar cadena para RRL
        }
    }

    @Override
    public String generaCadenaOriginalAutorizarRemitir(Long idSolicitud, String rfcAutorizador) {
        // La cadena de autorizacion de remision es comun para RRL y
        // CAL
        return autorizarRemitirService.generaCadenaOriginalAutorizarRemitir(idSolicitud, rfcAutorizador);
    }
}
