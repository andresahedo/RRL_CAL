package mx.gob.sat.siat.juridica.ca.web.controller.bean.bussiness;

import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoOficial;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.service.RequerimientoServices;
import mx.gob.sat.siat.juridica.cal.api.ConfirmarNotificacionRequerimientoFacade;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import java.util.Date;

@NoneScoped
@ManagedBean(name = "confirmarNotificacionRequerimientoBusiness")
public class ConfirmarNotificacionRequerimientoBusiness extends BaseBussinessBean {

    /**
     * 
     */
    private static final long serialVersionUID = -9162656900993817407L;

    @ManagedProperty("#{confirmarNotificacionRequerimientoFacade}")
    private ConfirmarNotificacionRequerimientoFacade confirmarNotificacionRequerimientoFacade;

    @ManagedProperty("#{requerimientoServices}")
    private RequerimientoServices requerimientoServices;

    /**
     * M&eacute;todo para firmar la tarea de confirmar notificacion
     * 
     * @param idRequerimiento
     */
    public void firmarConfirmarNotificacion(String numAsunto, Long idTarea, Long idRequerimiento, String rfcUsuario) {
        getConfirmarNotificacionRequerimientoFacade().firmarConfirmarNotificacion(numAsunto, idTarea.toString(),
                idRequerimiento, rfcUsuario);
    }

    public void guardarFirmaConfirmarNotificacion(FirmaDTO firma, Long idRequerimiento) {
        getConfirmarNotificacionRequerimientoFacade().guardarFirmaConfirmarNotificacion(firma, idRequerimiento);
    }

    /**
     * Metodo para guardar DocOficial.
     * 
     * @param numAsunto
     * @param documentoOficial
     * @return
     */
    public Long guardarDocOficial(String numAsunto, DocumentoOficial documentoOficial) {

        return getConfirmarNotificacionRequerimientoFacade().guardarDocOficial(numAsunto, documentoOficial);

    }

    public ConfirmarNotificacionRequerimientoFacade getConfirmarNotificacionRequerimientoFacade() {
        return confirmarNotificacionRequerimientoFacade;
    }

    public void setConfirmarNotificacionRequerimientoFacade(
            ConfirmarNotificacionRequerimientoFacade confirmarNotificacionRequerimientoFacade) {
        this.confirmarNotificacionRequerimientoFacade = confirmarNotificacionRequerimientoFacade;
    }

    public String generaCadenaOriginalNotificacionRequerimiento(Long idSolicitud, Long idRequerimiento,
            Date fechaFirma, String rfcUsuario) {
        return requerimientoServices.generaCadenaOriginalNotificacionRequerimiento(idSolicitud, idRequerimiento,
                fechaFirma, rfcUsuario);
    }

    public RequerimientoServices getRequerimientoServices() {
        return requerimientoServices;
    }

    public void setRequerimientoServices(RequerimientoServices requerimientoServices) {
        this.requerimientoServices = requerimientoServices;
    }

}
