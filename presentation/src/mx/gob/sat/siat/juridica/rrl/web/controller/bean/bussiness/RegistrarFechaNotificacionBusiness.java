/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness;

import mx.gob.sat.siat.juridica.base.api.BaseCloudFacade;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Notificacion;
import mx.gob.sat.siat.juridica.base.dto.DocumentoOficialDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.BaseCloudBusiness;
import mx.gob.sat.siat.juridica.rrl.api.RegistrarFechaNotificacionFacade;
import mx.gob.sat.siat.juridica.rrl.util.exception.FechaInvalidaException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.Date;
import java.util.List;

/**
 * Bean para conectar con el backend, atiende todas las peticiones de
 * la autorizacion de resolucion.
 * 
 * @author softtek
 * 
 */
@ManagedBean(name = "registrarFechaNotificacionBusiness")
@SessionScoped
public class RegistrarFechaNotificacionBusiness extends BaseCloudBusiness {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 6153327019799060919L;
    /**
     * Facade que publica los servicios para autorizar resolucion de
     * recurso revocacion.
     */
    @ManagedProperty("#{registrarFechaNotificacionFacade}")
    private RegistrarFechaNotificacionFacade registrarFechaNotificacionFacade;

    /**
     * @throws BaseBussinessException
     *             Metodo para guardar la notificacion con fecha.
     * 
     * @param solicitudDTO
     * @param tramiteDTO
     * @return
     * @throws
     */
    public Long guardarFechaNotificacion(String numeroAsunto, Boolean blnAutorizacion, Date fechaNotificacion,
            Long idNotificacion) throws FechaInvalidaException {

        Boolean validacionFecha =
                getRegistrarFechaNotificacionFacade().validarFechaNotificacion(fechaNotificacion, numeroAsunto);

        if (validacionFecha) {
            throw new FechaInvalidaException("fechas invalidas");
        }
        return getRegistrarFechaNotificacionFacade().guardarFechaNotificacion(numeroAsunto, blnAutorizacion,
                fechaNotificacion, idNotificacion);
    }
    
    public Notificacion buscarNotifParcial(String numeroAsunto, Boolean blnAutorizacion){
        return registrarFechaNotificacionFacade.buscarNotifParcial(numeroAsunto, blnAutorizacion);
    }

    public String generaCadenaOriginal(String numeroAsunto, Date fechaFirma) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return registrarFechaNotificacionFacade
     */
    public RegistrarFechaNotificacionFacade getRegistrarFechaNotificacionFacade() {
        return registrarFechaNotificacionFacade;
    }

    /**
     * @param registrarFechaNotificacionFacade
     *            registrarFechaNotificacionFacade a fijar
     */
    public void setRegistrarFechaNotificacionFacade(RegistrarFechaNotificacionFacade registrarFechaNotificacionFacade) {
        this.registrarFechaNotificacionFacade = registrarFechaNotificacionFacade;
    }

    public TramiteDTO obtenerTramite(String numAsunto) {
        return getRegistrarFechaNotificacionFacade().obtenerTramiteIdAsunto(numAsunto);
    }

    public Date buscarFechaNotificacionByIdTramite(String numeroAsunto) {

        return getRegistrarFechaNotificacionFacade().buscarFechaNotificacionByIdTramite(numeroAsunto);
    }
    
    public Date buscarFechaNotificacionByIdRequerimiento(Long idRequerimiento) {

        return getRegistrarFechaNotificacionFacade().buscarFechaNotificacionByIdRequerimiento(idRequerimiento);
    }

    public List<DocumentoOficialDTO> buscarDocumentosOficialesByIdTramite(String numeroAsunto) {

        return getRegistrarFechaNotificacionFacade().obtenerDocumentosFecha(numeroAsunto);
    }

    @Override
    public BaseCloudFacade getCloudFacade() {

        return registrarFechaNotificacionFacade;
    }

    public void eliminaDocumentosNotificacionAnexados(String numAsunto) {
        getRegistrarFechaNotificacionFacade().eliminaDocumentosNotificacionAnexados(numAsunto);
    }

    public Date buscarFechaNotificacionNyB() {
        // TODO Auto-generated method stub
        return new Date();
    }

}
