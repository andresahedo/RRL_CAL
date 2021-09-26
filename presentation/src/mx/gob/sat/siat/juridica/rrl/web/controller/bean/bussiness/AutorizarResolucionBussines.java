/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness;

import mx.gob.sat.siat.juridica.base.api.AutorizarResolucionFacade;
import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.SolicitudDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.rrl.api.AutorizarResolucionRecursoRevocacionFacade;
import mx.gob.sat.siat.juridica.rrl.dto.ResolucionAbogadoDTO;
import mx.gob.sat.siat.juridica.rrl.dto.ResolucionImpugnadaDTO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import java.util.Date;
import java.util.List;

/**
 * Bean para conectar con el backend, atiende todas las peticiones de
 * la autorizacion de resolucion.
 * 
 * @author softtek
 * 
 */
@ManagedBean(name = "autorizarResolucionBussines")
@NoneScoped
public class AutorizarResolucionBussines extends BaseBussinessBean {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 6153327019799060919L;

    /**
     * Facade que publica los servicios para autorizar resolucion de
     * recurso revocacion.
     */
    @ManagedProperty("#{autorizarResolucionRecursoRevocacionFacade}")
    private AutorizarResolucionRecursoRevocacionFacade autorizarResolucionRecursoRevocacionFacade;

    /**
     * Facade que publica los servicios para autorizar resolucion
     * com&uacute;n
     */
    @ManagedProperty("#{autorizarResolucionFacade}")
    private AutorizarResolucionFacade autorizarResolucionFacade;

    public AutorizarResolucionFacade getAutorizarResolucionFacade() {
        return autorizarResolucionFacade;
    }

    public void setAutorizarResolucionFacade(AutorizarResolucionFacade autorizarResolucionFacade) {
        this.autorizarResolucionFacade = autorizarResolucionFacade;
    }

    /**
     * Metodo para mostrar resoluciones de una solicitud y tramite.
     * 
     * @param solicitudDTO
     * @param tramiteDTO
     * @return
     */
    public ResolucionAbogadoDTO mostrarResoluciones(SolicitudDTO solicitudDTO, TramiteDTO tramiteDTO) {
        return getAutorizarResolucionRecursoRevocacionFacade().consultarInformacionInicial(solicitudDTO, tramiteDTO);
    }

    /**
     * Metodo para guardar una resolucion.
     * 
     * @param abogadoDTO
     */
    public void guardarResolucion(ResolucionAbogadoDTO abogadoDTO) {
        getAutorizarResolucionRecursoRevocacionFacade().guardar(abogadoDTO);

    }

    public void rechazarResolucion(String numeroAsunto, Long idTarea, String rfc, Long idSolicitud) {
        getAutorizarResolucionRecursoRevocacionFacade().rechazarResolucion(numeroAsunto, idTarea.toString(), rfc,
                idSolicitud);

    }

    /**
     * Metodo para validar si existe una resolucion.
     * 
     * @param resol
     * @return
     */
    public ResolucionImpugnadaDTO validarResolucionExistente(ResolucionImpugnadaDTO resol) {

        return getAutorizarResolucionRecursoRevocacionFacade().validarExistencia(resol);
    }

    /**
     * 
     * @return autorizarResolucionRecursoRevocacionFacade
     */
    public AutorizarResolucionRecursoRevocacionFacade getAutorizarResolucionRecursoRevocacionFacade() {
        return autorizarResolucionRecursoRevocacionFacade;
    }

    /**
     * 
     * @param autorizarResolucionRecursoRevocacionFacade
     *            la autorizarResolucionRecursoRevocacionFacade
     */
    public void setAutorizarResolucionRecursoRevocacionFacade(
            AutorizarResolucionRecursoRevocacionFacade autorizarResolucionRecursoRevocacionFacade) {
        this.autorizarResolucionRecursoRevocacionFacade = autorizarResolucionRecursoRevocacionFacade;
    }

    public List<DocumentoDTO> obtenerDocumentosComplementariosAutorizacion(Long idSolicitud) {

        return autorizarResolucionRecursoRevocacionFacade.obtenerDocumentosComplementariosAutorizacion(idSolicitud);

    }

    public String generaCadenaOriginalAutorizarResolucion(Long idSolicitud, Date date, String rfcFuncionario) {
        return autorizarResolucionFacade.generaCadenaOriginalAutorizarResolucion(idSolicitud, date, rfcFuncionario);
    }

}
