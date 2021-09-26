/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness;

import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.rrl.api.AutorizarRemisionFacade;
import mx.gob.sat.siat.juridica.rrl.util.exception.RemitirAsuntoException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import java.util.Date;
import java.util.List;

/**
 * Bean para conectar con el Backend, que atiende todas las solictudes
 * para autorizar remision.
 * 
 * @author softtek
 * 
 */
@ManagedBean(name = "autorizarRemisionBussiness")
@NoneScoped
public class AutorizarRemisionBussiness extends BaseBussinessBean {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -5844280954569585761L;

    /**
     * Facade que publica los servicios para implementar la
     * autorizacion de una remision.
     */
    @ManagedProperty("#{autorizarRemisionFacade}")
    private AutorizarRemisionFacade autorizarRemisionFacade;
    /**
     * Propiedad que representa un nombre de la remision.
     */
    private String nombre;

    /**
     * Metodo que obtiene el nombre de una remision de acuerdo al rfc.
     * 
     * @param rfc
     * @return
     */
    public String obtenerNombre(String rfc) {
        nombre = autorizarRemisionFacade.obtenerNombre(rfc);
        return nombre;
    }

    /**
     * Metodo para obtener el tramite correspondiente al numero de
     * asunto recibido como parametro.
     * 
     * @param numAsunto
     * @return
     */
    public TramiteDTO obtenerTramite(String numAsunto) {
        return autorizarRemisionFacade.obtenerTramite(numAsunto);
    }

    /**
     * Metodo para obtener las unidades emisoras.
     * 
     * @return
     */
    public List<CatalogoDTO> obtenerUnidadesEmisoras() {
        return autorizarRemisionFacade.obtenerAutoridadesEmisoras();
    }

    /**
     * 
     * @return autorizarRemisionFacade
     */
    public AutorizarRemisionFacade getAutorizarRemisionFacade() {
        return autorizarRemisionFacade;
    }

    /**
     * 
     * @param autorizarRemisionFacade
     *            la autorizarRemisionFacade
     */
    public void setAutorizarRemisionFacade(AutorizarRemisionFacade autorizarRemisionFacade) {
        this.autorizarRemisionFacade = autorizarRemisionFacade;
    }

    /**
     * Metodo para obtener la unidad emisora.
     * 
     * @param numAsunto
     * @return
     */
    public String obtenerUnidad(String numAsunto) {
        return autorizarRemisionFacade.obtenerUnidad(numAsunto);
    }

    /**
     * Metodo para actualizar datos de una remision en especifico.
     * 
     * @param numeroAsunto
     * @param unidad
     */
    public void actualizarRemision(String numeroAsunto, String unidad) throws RemitirAsuntoException {
        autorizarRemisionFacade.actualizarRemision(numeroAsunto, unidad);
    }

    /**
     * Genera la cadena original de la remision usada en la firma
     * 
     * @param numAsunto
     * @param fechaFirma
     * @return
     */
    public String generaCadenaOriginal(String numAsunto, Date fechaFirma) {
        // TODO Auto-generated method stub
        return null;
    }

    public void rechazarRemision(String numeroAsunto, Long idTarea, String rfc, Long idSolicitud) {
        autorizarRemisionFacade.rechazarRemision(numeroAsunto, idTarea.toString(), rfc, idSolicitud);

    }

    public List<DocumentoDTO> obtenerDocumentosComplementariosAutorizacion(long idSolicitud) {
        return autorizarRemisionFacade.obtenerDocumentosComplementariosAutorizacion(idSolicitud);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
