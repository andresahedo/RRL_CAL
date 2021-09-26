/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
/**
 * 
 */
package mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness;

import mx.gob.sat.siat.juridica.base.api.AtenderRequerimientoFacade;
import mx.gob.sat.siat.juridica.base.api.BaseCloudFacade;
import mx.gob.sat.siat.juridica.base.api.FirmarFacade;
import mx.gob.sat.siat.juridica.base.api.RegistroRecursoRevocacionFacade;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.RequerimientoDTO;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.BaseCloudBusiness;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * Bean que implementan la l&oacute;gica de negocio para atender
 * requerimiento
 * 
 * @author Softtek
 * 
 */
@NoneScoped
@ManagedBean(name = "atenderRequerimientoBussines")
public class AtenderRequerimientoBussines extends BaseCloudBusiness {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -5147855292746059873L;

    /**
     * Facade que publica los metodos que implementan la logica de
     * negocio para atender un requerimiento.
     */
    @ManagedProperty("#{atenderRequerimientoFacade}")
    private AtenderRequerimientoFacade atenderRequerimientoFacade;

    @ManagedProperty("#{registroRecursoRevocacionFacade}")
    private RegistroRecursoRevocacionFacade registroRecursoRevocacionFacade;

    @ManagedProperty("#{firmarFacade}")
    private transient FirmarFacade firmarFacade;

    /**
     * Metodo que prepara un requerimiento para atender.
     * 
     * @param numeroAsunto
     * @return
     */
    public RequerimientoDTO preparaAtenderRequerimiento(String numeroAsunto, String modalidad) {
        return getAtenderRequerimientoFacade().prepararAtenderRequerimiento(numeroAsunto, modalidad);
    }
    
    public RequerimientoDTO obtenerRequerimientoporId(Long idRequerimiento) {
        return getAtenderRequerimientoFacade().obtenerRequerimientoporId(idRequerimiento);
    }

    /**
     * M&eacute;todo para descargar un documento
     */
    public InputStream descargarDocumento(DocumentoDTO documento) {
        return getAtenderRequerimientoFacade().descargarDocumento(documento);
    }

    /**
     * M&eacute;todo para almacenar los documentos a la solicitud
     */
    public void guardarDocumentosSolicitud(long idSolicitud, long idRequerimiento, List<DocumentoDTO> documentos,
            String tipoTramite, String rfc) throws ArchivoNoGuardadoException {
        getAtenderRequerimientoFacade().guardarDocumentosSolicitud(idSolicitud, idRequerimiento, documentos,
                tipoTramite, rfc);
    }

    public List<DocumentoDTO> obtenerDocumentosSolicitudRequerimiento(long idRequerimiento) {
        return atenderRequerimientoFacade.obtenerDocumentosSolicitudRequerimiento(idRequerimiento);
    }

    public void eliminaDocumento(long idDocumento) {
        getAtenderRequerimientoFacade().eliminaDocumento(idDocumento);
    }

    /**
     * Metodo para atender la firma del requerimiento.
     * 
     * @param datosBandeja
     * @param firma
     * @param idSolicitud
     * @param idRequerimiento
     */
    public void firmaAtender(DatosBandejaTareaDTO datosBandeja, RequerimientoDTO requerimiento, String rfcUsuario,
            FirmaDTO firma, Long idSolicitud) {
        getAtenderRequerimientoFacade().firmaAtender(datosBandeja, requerimiento, rfcUsuario);
        getFirmarFacade().guardarFirmaRequerimento(firma, requerimiento.getIdRequerimiento(), idSolicitud);

    }

    /**
     * 
     * @return atenderRequerimientoFacade
     */
    public AtenderRequerimientoFacade getAtenderRequerimientoFacade() {
        return atenderRequerimientoFacade;
    }

    /**
     * 
     * @param atenderRequerimientoFacade
     */
    public void setAtenderRequerimientoFacade(AtenderRequerimientoFacade atenderRequerimientoFacade) {
        this.atenderRequerimientoFacade = atenderRequerimientoFacade;
    }

    public String generaCadenaOriginal(Long idSolicitud, Date fechaFirma) {
        return getAtenderRequerimientoFacade().generarCadenaOriginal(idSolicitud, fechaFirma);
    }

    public String generaCadenaOriginalPromocion(Long idSolicitud, Date fechaFirma) {
        return getAtenderRequerimientoFacade().generarCadenaOriginalPromocion(idSolicitud, fechaFirma);
    }

    /**
     * 
     * @return registroRecursoRevocacionFacade
     */
    public RegistroRecursoRevocacionFacade getRegistroRecursoRevocacionFacade() {
        return registroRecursoRevocacionFacade;
    }

    /**
     * 
     * @param registroRecursoRevocacionFacade
     *            a fijar
     */
    public void setRegistroRecursoRevocacionFacade(RegistroRecursoRevocacionFacade registroRecursoRevocacionFacade) {
        this.registroRecursoRevocacionFacade = registroRecursoRevocacionFacade;
    }

    public boolean validaFiel() {
        // TODO Auto-generated method stub
        return true;
    }

    /**
     * @return the firmarFacade
     */
    public FirmarFacade getFirmarFacade() {
        return firmarFacade;
    }

    /**
     * @param firmarFacade
     *            the firmarFacade to set
     */
    public void setFirmarFacade(FirmarFacade firmarFacade) {
        this.firmarFacade = firmarFacade;
    }

    @Override
    public BaseCloudFacade getCloudFacade() {
        return atenderRequerimientoFacade;
    }

    public FirmaDTO obtenSelloAtenderRequerimientoSIAT(String numAsunto, long idSolicitud, Date fechaFirma) {
        return atenderRequerimientoFacade.obtenSelloAtenderRequerimientoSIAT(numAsunto, idSolicitud, fechaFirma);
    }

    public void eliminaDocumentosSolicitudRequerimiento(Long idRequerimiento) {
        atenderRequerimientoFacade.eliminaDocumentosSolicitudRequerimiento(idRequerimiento);
    }
}
