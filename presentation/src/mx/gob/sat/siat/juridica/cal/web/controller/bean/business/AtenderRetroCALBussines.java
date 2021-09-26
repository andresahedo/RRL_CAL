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
package mx.gob.sat.siat.juridica.cal.web.controller.bean.business;

import mx.gob.sat.siat.juridica.base.api.BaseCloudFacade;
import mx.gob.sat.siat.juridica.base.api.FirmarFacade;
import mx.gob.sat.siat.juridica.base.api.RegistroRecursoRevocacionFacade;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoOficialDTO;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.RequerimientoDTO;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.BaseCloudBusiness;
import mx.gob.sat.siat.juridica.cal.api.AtenderRetroCALFacade;
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
@ManagedBean(name = "atenderRetroCALBussines")
public class AtenderRetroCALBussines extends BaseCloudBusiness {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -5147855292746059873L;

    /**
     * Facade que publica los metodos que implementan la logica de
     * negocio para atender un requerimiento.
     */
    @ManagedProperty("#{atenderRetroCALFacade}")
    private AtenderRetroCALFacade atenderRetroCALFacade;

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

        return null;
    }

    /**
     * M&eacute;todo para descargar un documento
     */
    public InputStream descargarDocumento(DocumentoDTO documento) {
        return getAtenderRetroCALFacade().descargarDocumento(documento);
    }

    /**
     * M&eacute;todo para almacenar los documentos a la solicitud
     */
    public void guardarDocumentosSolicitud(long idSolicitud, long idRequerimiento, List<DocumentoDTO> documentos,
            String tipoTramite, String rfc) throws ArchivoNoGuardadoException {
        getAtenderRetroCALFacade().guardarDocumentosSolicitud(idSolicitud, idRequerimiento, documentos, tipoTramite,
                rfc);
    }

    public List<DocumentoDTO> obtenerDocumentosSolicitudRequerimiento(long idRequerimiento) {
        return getAtenderRetroCALFacade().obtenerDocumentosSolicitudRequerimiento(idRequerimiento);
    }

    public void eliminaDocumento(long idDocumento) {
        getAtenderRetroCALFacade().eliminaDocumentoOficial(idDocumento);
    }

    /**
     * Metodo para atender la firma del requerimiento.
     * 
     * @param datosBandeja
     * @param idRequerimiento
     */
    public void firmaAtender(DatosBandejaTareaDTO datosBandeja, Long idRequerimiento, String rfcUsuario,
            FirmaDTO firma, String numAsunto) {
        getAtenderRetroCALFacade().firmaAtender(datosBandeja, idRequerimiento, rfcUsuario, numAsunto);
        getAtenderRetroCALFacade().guardarFirmaAtender(firma, idRequerimiento);
    }

    /**
     * 
     * @return atenderRequerimientoFacade
     */
    public AtenderRetroCALFacade getAtenderRetroCALFacade() {
        return atenderRetroCALFacade;
    }

    /**
     * 
     * @param atenderRequerimientoFacade
     */
    public void setAtenderRetroCALFacade(AtenderRetroCALFacade atenderRetroCALFacade) {
        this.atenderRetroCALFacade = atenderRetroCALFacade;
    }

    public String generaCadenaOriginal(String numeroAsunto, Date fechaFirma, String rfcFuncionario) {
        return getAtenderRetroCALFacade().generarCadenaOriginal(numeroAsunto, fechaFirma, rfcFuncionario);
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

    public void guardarDocumentosAutorizarRequerimiento(List<DocumentoOficialDTO> listaDocumentosOficiales,
            String numeroAsunto, Long idRequerimiento) {
        getAtenderRetroCALFacade().guardarDocumentosAutorizarRequerimiento(listaDocumentosOficiales, numeroAsunto,
                idRequerimiento);

    }

    @Override
    public BaseCloudFacade getCloudFacade() {
        return atenderRetroCALFacade;
    }

    public FirmaDTO obtenSelloAtenderRetroSIAT(String numeroAsunto, Date fechaFirma, String rfcFuncionario) {
        return atenderRetroCALFacade.obtenSelloAtenderRetroSIAT(numeroAsunto, fechaFirma, rfcFuncionario);
    }

    public FirmarFacade getFirmarFacade() {
        return firmarFacade;
    }

    public void setFirmarFacade(FirmarFacade firmarFacade) {
        this.firmarFacade = firmarFacade;
    }

    public void sellarDocumentosAtencion(String numeroAsunto) {
        getAtenderRetroCALFacade().sellarDocumentosAtencion(numeroAsunto);
        
    }
}
