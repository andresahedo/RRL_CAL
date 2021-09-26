package mx.gob.sat.siat.juridica.cal.web.controller.bean.business;

import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoOficialDTO;
import mx.gob.sat.siat.juridica.base.dto.RequerimientoDTO;
import mx.gob.sat.siat.juridica.base.dto.UserProfileDTO;
import mx.gob.sat.siat.juridica.cal.api.AutorizarRequerimientoCALFacade;
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
@ManagedBean(name = "autorizarRequerimientoCALBussines")
@NoneScoped
public class AutorizarRequerimientoCALBussines extends BaseBussinessBean {

    private static final long serialVersionUID = -7129919710716680347L;
    /**
     * Facade para atender la autorizaci&oacute;n del requerimiento.
     */
    @ManagedProperty("#{autorizarRequerimientoCALFacade}")
    private transient AutorizarRequerimientoCALFacade autorizarRequerimientoCALFacade;

    /**
     * M&eacute;todo para obtener los tipos de requerimiento.
     */
    public List<CatalogoDTO> obtenerTiposDeRequerimiento() {
        return getAutorizarRequerimientoCALFacade().obtenerTiposDeRequerimiento();
    }

    /**
     * M&eacute;todo para obtener las autoridades a las que se
     * solicitar&aacute; el requerimiento.
     */
    public List<CatalogoDTO> obtenerAutoridades() {
        return getAutorizarRequerimientoCALFacade().obtenerAutoridades();
    }

    /**
     * M&eacute;todo para preparar el requerimiento que ser&aacute;
     * autorizado.
     */
    public RequerimientoDTO prepararAutorizarRequerimiento(String numeroAsunto, UserProfileDTO userProfile,
            String modalidad) {
        return getAutorizarRequerimientoCALFacade()
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
        return getAutorizarRequerimientoCALFacade().obtenerDocumentosPorRequerimiento(idRequerimiento);
    }

    /**
     * M&eacute;todo para descargar el archivo asociado al documento
     * oficial.
     */
    public InputStream descargarArchivoOficial(DocumentoOficialDTO documento) {
        return getAutorizarRequerimientoCALFacade().descargarArchivoOficial(documento);
    }

    /**
     * M&eacute;todo para guardar los documentos asociadas al
     * requerimiento (autorizaci&oacute;n).
     */
    public void guardarDocumentosAutorizarRequerimiento(List<DocumentoOficialDTO> documentos,
            RequerimientoDTO requerimiento) {
        getAutorizarRequerimientoCALFacade().guardarDocumentosAutorizarRequerimiento(documentos, requerimiento);
    }

    /**
     * M&eacute;todo para borrado logico de documentos
     * 
     * @param idDocumento
     */
    public void eliminaDocumentoOficial(long idDocumento) {
        getAutorizarRequerimientoCALFacade().eliminaDocumento(idDocumento);
    }

    /**
     * M&eacute;todo para firmar la autorizaci&oacute;n del
     * requerimiento.
     */
    public void firmarAutorizarRequerimiento(RequerimientoDTO requerimiento, DatosBandejaTareaDTO datosBandeja,
            String rfcUsuario) {
        getAutorizarRequerimientoCALFacade().firmarAutorizarRequerimiento(requerimiento, datosBandeja, rfcUsuario);
    }

    /**
     * 
     * @return autorizarRequerimientoCALFacade
     */
    public AutorizarRequerimientoCALFacade getAutorizarRequerimientoCALFacade() {
        return autorizarRequerimientoCALFacade;
    }

    /**
     * 
     * @param autorizarRequerimientoCALFacade
     *            a fijar
     */
    public void setAutorizarRequerimientoCALFacade(AutorizarRequerimientoCALFacade autorizarRequerimientoCALFacade) {
        this.autorizarRequerimientoCALFacade = autorizarRequerimientoCALFacade;
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
        return getAutorizarRequerimientoCALFacade().prepararAutorizarRequerimientoByIdRequerimiento(idRequerimiento,
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

}
