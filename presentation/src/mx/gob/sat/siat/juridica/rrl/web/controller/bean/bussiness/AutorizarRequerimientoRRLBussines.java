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
import mx.gob.sat.siat.juridica.base.api.FirmarFacade;
import mx.gob.sat.siat.juridica.base.dto.*;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.BaseCloudBusiness;
import mx.gob.sat.siat.juridica.rrl.api.AutorizarRequerimientoRRLFacade;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * Bean para conectar con el backend, atiende todas las peticiones de
 * la autorizaci&oacute;n del requerimiento.
 * 
 * @author Softtek
 * 
 */
@ManagedBean(name = "autorizarRequerimientoRRLBussines")
@NoneScoped
public class AutorizarRequerimientoRRLBussines extends BaseCloudBusiness {

    /**
     * 
     */
    private static final long serialVersionUID = -7129919710716680347L;

    /**
     * Facade para atender la autorizaci&oacute;n del requerimiento.
     */
    @ManagedProperty("#{autorizarRequerimientoRRLFacade}")
    private AutorizarRequerimientoRRLFacade autorizarRequerimientoRRLFacade;

    /**
     * Facade para manejo de firmas del requerimiento.
     */
    @ManagedProperty("#{firmarFacade}")
    private transient FirmarFacade firmarFacade;

    /**
     * M&eacute;todo para obtener los tipos de requerimiento.
     */
    public List<CatalogoDTO> obtenerTiposDeRequerimiento() {
        return getAutorizarRequerimientoRRLFacade().obtenerTiposDeRequerimiento();
    }

    /**
     * M&eacute;todo para obtener las autoridades a las que se
     * solicitar&aacute; el requerimiento.
     */
    public List<CatalogoDTO> obtenerAutoridades() {
        return getAutorizarRequerimientoRRLFacade().obtenerAutoridades();
    }

    /**
     * M&eacute;todo para obtener las autoridades a las que se
     * solicitar&aacute; el requerimiento.
     */
    public List<CatalogoDTO> obtenerAutoridadesTodas() {
        return getAutorizarRequerimientoRRLFacade().obtenerAutoridadesTodas();
    }

    /**
     * M&eacute;todo para preparar el requerimiento que ser&aacute;
     * autorizado.
     */
    public RequerimientoDTO prepararAutorizarRequerimiento(String numeroAsunto, UserProfileDTO userProfile,
            String modalidad) {
        return getAutorizarRequerimientoRRLFacade()
                .prepararAutorizarRequerimiento(numeroAsunto, userProfile, modalidad);
    }

    /**
     * Metodo que obtiene los documentos correspondiente al numFolio y
     * los devuelve en una lista DocumentoDTO.
     * 
     * @param numFolio
     * @return
     */
    public List<DocumentoOficialDTO> obtenerDocumentos(Long idRequerimiento) {
        return getAutorizarRequerimientoRRLFacade().obtenerDocumentosPorRequerimiento(idRequerimiento);
    }

    public void eliminaDocumentosRequerimiento(Long idRequerimiento) {
        getAutorizarRequerimientoRRLFacade().eliminaDocumentosRequerimiento(idRequerimiento);
    }

    /**
     * M&eacute;todo para descargar el archivo asociado al documento
     * oficial.
     */
    public InputStream descargarArchivoOficial(DocumentoOficialDTO documento) {
        return getAutorizarRequerimientoRRLFacade().descargarArchivoOficial(documento);
    }

    /**
     * M&eacute;todo para guardar los documentos asociadas al
     * requerimiento (autorizaci&oacute;n).
     */
    public void guardarDocumentosAutorizarRequerimiento(List<DocumentoOficialDTO> documentos,
            RequerimientoDTO requerimiento) {
        getAutorizarRequerimientoRRLFacade().guardarDocumentosAutorizarRequerimiento(documentos, requerimiento);
    }

    public void actualizaRequerimiento(RequerimientoDTO req) {
        getAutorizarRequerimientoRRLFacade().actualizarRequerimiento(req);
    }

    /**
     * M&eacute;todo para borrado logico de documentos
     * 
     * @param idDocumento
     */
    public void eliminaDocumentoOficial(long idDocumento) {
        getAutorizarRequerimientoRRLFacade().eliminaDocumento(idDocumento);
    }

    /**
     * M&eacute;todo para firmar la autorizaci&oacute;n del
     * requerimiento.
     */
    public void firmarAutorizarRequerimiento(FirmaDTO firma, RequerimientoDTO requerimiento,
            DatosBandejaTareaDTO datosBandeja, String rfcUsuario) {
        getAutorizarRequerimientoRRLFacade().firmarAutorizarRequerimiento(requerimiento, datosBandeja, rfcUsuario);
        getFirmarFacade().guardarFirmaAutorizarRequerimento(firma, requerimiento.getIdRequerimiento());
    }

    /**
     * 
     * @return autorizarRequerimientoRRLFacade
     */
    public AutorizarRequerimientoRRLFacade getAutorizarRequerimientoRRLFacade() {
        return autorizarRequerimientoRRLFacade;
    }

    /**
     * 
     * @param autorizarRequerimientoRRLFacade
     *            a fijar
     */
    public void setAutorizarRequerimientoRRLFacade(AutorizarRequerimientoRRLFacade autorizarRequerimientoRRLFacade) {
        this.autorizarRequerimientoRRLFacade = autorizarRequerimientoRRLFacade;
    }

    /**
     * Metodo que prepara la autorizacion de un requerimiento.
     * 
     * @param idRequerimiento
     * @param userProfile
     * @return
     */
    public RequerimientoDTO prepararAutorizarRequerimientoByIdRequerimiento(Long idRequerimiento,
            UserProfileDTO userProfile) {
        return getAutorizarRequerimientoRRLFacade().prepararAutorizarRequerimientoByIdRequerimiento(idRequerimiento,
                userProfile);
    }

    /**
     * 
     * @return true
     */
    public Boolean validaFiel() {
        // TODO Auto-generated method stub
        return true;
    }

    /**
     * 
     * @param idRequerimiento
     * @param fechaFirma
     * @return true
     */
    public String generaCadenaOriginal(Long idRequerimiento, Date fechaFirma) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<CatalogoDTO> obtenerTiposDeRequerimiento(String tipoReq) {
        return getAutorizarRequerimientoRRLFacade().obtenerTiposDeRequerimiento(tipoReq);
    }

    public void rechazarRequerimiento(DatosBandejaTareaDTO datosBandeja, String rfc, Long idRequerimiento) {
        getAutorizarRequerimientoRRLFacade().rechazarRequerimiento(datosBandeja.getNumeroAsunto(),
                datosBandeja.getIdTareaUsuario(), rfc, idRequerimiento, datosBandeja.getIdSolicitud());

    }

    @Override
    public BaseCloudFacade getCloudFacade() {
        // TODO Auto-generated method stub
        return autorizarRequerimientoRRLFacade;
    }

    public FirmarFacade getFirmarFacade() {
        return firmarFacade;
    }

    public void setFirmarFacade(FirmarFacade firmarFacade) {
        this.firmarFacade = firmarFacade;
    }

    public String generaCadenaOriginalAutorizarRequerimiento(Long idRequerimiento, Date date, String rfcFuncionario) {
        return autorizarRequerimientoRRLFacade.generaCadenaOriginalAutorizarRequerimiento(idRequerimiento, date,
                rfcFuncionario);
    }

    public List<DocumentoDTO> obtenerDocumentosComplementarios(Long idSolicitud) {
        return autorizarRequerimientoRRLFacade.obtenerDocumentosComplementarios(idSolicitud);
    }
}
