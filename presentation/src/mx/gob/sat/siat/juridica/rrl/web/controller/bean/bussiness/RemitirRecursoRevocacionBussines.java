/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
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
import mx.gob.sat.siat.juridica.bpm.excepcion.TareaInicialException;
import mx.gob.sat.siat.juridica.bpm.excepcion.TereaSinUsuarioAsignadoException;
import mx.gob.sat.siat.juridica.rrl.api.RemitirRecursoRevocacionFacade;
import mx.gob.sat.siat.juridica.rrl.util.exception.RemitirAsuntoException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import java.util.List;

/**
 * Bussines que implementan la l&oacute;gica de negocio para turnar
 * recurso de revocacion
 * 
 * @author Softtek
 * 
 */
@ManagedBean(name = "remitirRecursoRevocacionBussines")
@NoneScoped
public class RemitirRecursoRevocacionBussines extends BaseBussinessBean {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 3688290193065023930L;

    /** Facade para turnar recurso revocacion */
    @ManagedProperty("#{remitirRecursoRevocacionFacade}")
    private RemitirRecursoRevocacionFacade remitirRecursoRevocacionFacade;

    /**
     * M&eacute;todo para obtener las autorizades emisoras
     */
    public List<CatalogoDTO> obtenerAutoridadesEmisoras() {
        return remitirRecursoRevocacionFacade.obtenerAutoridadesEmisoras();
    }

    public List<CatalogoDTO> obtenerPersonasAsignar(String numAsunto) {
        return remitirRecursoRevocacionFacade.obtenerPersonasAsignar(numAsunto);
    }

    /**
     * M&eacute;todo para obtener las autorizades emisoras
     */
    public void remitir(String numAsunto, String autoridadEmisora, Long idTarea, String rfcAsignar, String rfcUsuario,
            String ideTareaOrigen) throws TareaInicialException, TereaSinUsuarioAsignadoException,
            RemitirAsuntoException {
        remitirRecursoRevocacionFacade.remitir(numAsunto, autoridadEmisora, idTarea, rfcAsignar, rfcUsuario,
                ideTareaOrigen);
    }

    /**
     * Metodo para enviar correo a tarea a asignar.
     * 
     * @param numeroAsunto
     * @param rfc
     */
    public void envioCorreoAsignarTarea(String numeroAsunto, String rfc, String nombreTarea) {
        remitirRecursoRevocacionFacade.enviarCorreo(numeroAsunto, rfc, nombreTarea);
    }

    /**
     * @return the remitirRecursoRevocacionFacade
     */
    public RemitirRecursoRevocacionFacade getRemitirRecursoRevocacionFacade() {
        return remitirRecursoRevocacionFacade;
    }

    /**
     * @param remitirRecursoRevocacionFacade
     *            the remitirRecursoRevocacionFacade to set
     */
    public void setRemitirRecursoRevocacionFacade(RemitirRecursoRevocacionFacade remitirRecursoRevocacionFacade) {
        this.remitirRecursoRevocacionFacade = remitirRecursoRevocacionFacade;
    }

    public TramiteDTO obtenerTramite(String numAsunto) {
        return remitirRecursoRevocacionFacade.obtenerTramite(numAsunto);
    }

    public String getDefaultIdeTareaOrigen() {
        return remitirRecursoRevocacionFacade.getDefaultIdeTareaOrigen();
    }

    public List<DocumentoDTO> obtenerDocumentosComplementarios(Long idSolicitud) {
        return remitirRecursoRevocacionFacade.obtenerDocumentosComplementarios(idSolicitud);
    }

}
