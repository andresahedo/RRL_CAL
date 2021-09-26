package mx.gob.sat.siat.juridica.cal.api.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.FirmaTransformer;
import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.base.service.FirmarServices;
import mx.gob.sat.siat.juridica.base.service.RequerimientoServices;
import mx.gob.sat.siat.juridica.cal.api.ConfirmarNotificacionRequerimientoFacade;
import mx.gob.sat.siat.juridica.rrl.service.AutorizarRemitirService;
import mx.gob.sat.siat.juridica.rrl.service.FirmaTareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("confirmarNotificacionRequerimientoFacade")
public class ConfirmarNotificacionRequerimientoFacadeImpl extends BaseFacadeImpl implements
        ConfirmarNotificacionRequerimientoFacade {

    /**
     * 
     */
    private static final long serialVersionUID = -7804085700931072447L;
    @Autowired
    private transient FirmaTareaService firmaTareaService;

    @Autowired
    private transient FirmarServices firmarServices;
    @Autowired
    private transient AutorizarRemitirService autorizarRemitirService;
    @Autowired
    private transient RequerimientoServices requerimientoServices;

    @Autowired
    private transient FirmaTransformer firmaTransformer;

    @Override
    public void firmarConfirmarNotificacion(String numAsunto, String idTarea, Long idRequerimiento, String rfcUsuario) {
        getFirmaTareaService().firmarConfirmarNotificacion(numAsunto, idTarea, idRequerimiento, rfcUsuario);

    }

    @Override
    public Long guardarDocOficial(String numFolio, DocumentoOficial documentoOficial) {

        return getAutorizarRemitirService().guardarDoc(numFolio, documentoOficial);

    }

    /**
     * @return the firmaTareaService
     */
    public FirmaTareaService getFirmaTareaService() {
        return firmaTareaService;
    }

    /**
     * @param firmaTareaService
     *            the firmaTareaService to set
     */
    public void setFirmaTareaService(FirmaTareaService firmaTareaService) {
        this.firmaTareaService = firmaTareaService;
    }

    /**
     * @return the autorizarRemitirService
     */
    public AutorizarRemitirService getAutorizarRemitirService() {
        return autorizarRemitirService;
    }

    /**
     * @param autorizarRemitirService
     *            the autorizarRemitirService to set
     */
    public void setAutorizarRemitirService(AutorizarRemitirService autorizarRemitirService) {
        this.autorizarRemitirService = autorizarRemitirService;
    }

    /**
     * Guarda la firma de notificacion de requerimiento a autoridad
     * 
     * @param firma
     * @param idRequerimiento
     */
    @Override
    public void guardarFirmaConfirmarNotificacion(FirmaDTO firmaDTO, Long idRequerimiento) {
        Firma firma = firmaTransformer.transformarDTO(firmaDTO);
        firmarServices.guardarFrima(firma);

        NotificacionRequerimiento notificacion =
                requerimientoServices.obtenerNotificacionRequerimiento(idRequerimiento);
        if (notificacion != null) {
            FirmaNotificacion firmaNot = new FirmaNotificacion();
            firmaNot.setFirmaNotificacionPK(new FirmaNotificacionPK());
            firmaNot.getFirmaNotificacionPK().setIdFirma(firma.getIdFirma());
            firmaNot.getFirmaNotificacionPK().setIdNotificacion(notificacion.getIdNotificacion());
            firmarServices.guardarFirmaNotificacion(firmaNot);
        }
    }

}
