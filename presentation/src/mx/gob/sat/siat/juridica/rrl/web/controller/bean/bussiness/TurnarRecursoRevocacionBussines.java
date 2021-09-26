/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */

package mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness;

import mx.gob.sat.siat.juridica.base.api.TurnarRecursoRevocacionFacade;
import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.base.dto.AbogadoDTO;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;

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
@ManagedBean(name = "turnarRecursoRevocacionBussines")
@NoneScoped
public class TurnarRecursoRevocacionBussines extends BaseBussinessBean {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -5933273073716481561L;

    /** Facade para turnar recurso revocacion */
    @ManagedProperty("#{turnarRecursoRevocacionFacade}")
    private TurnarRecursoRevocacionFacade turnarRecursoRevocacionFacade;

    /**
     * M&eacute;todo para obtener los abogados
     */
    public List<AbogadoDTO> listaAbogado(String numAsunto) {
        return turnarRecursoRevocacionFacade.obtenerAbogados(numAsunto);
    }

    /**
     * Metodo para obtener el estado procesal de un asunto.
     * 
     * @param numAsunto
     * @return
     */
    public TramiteDTO obtenerEstadoProcesal(String numAsunto) {
        return turnarRecursoRevocacionFacade.obtenerEstadoProcesal(numAsunto);
    }

    /**
     * 
     * @return turnarRecursoRevocacionFacade
     */
    public TurnarRecursoRevocacionFacade getTurnarRecursoRevocacionFacade() {
        return turnarRecursoRevocacionFacade;
    }

    /**
     * 
     * @param turnarRecursoRevocacionFacade
     *            a fijar
     */
    public void setTurnarRecursoRevocacionFacade(TurnarRecursoRevocacionFacade turnarRecursoRevocacionFacade) {
        this.turnarRecursoRevocacionFacade = turnarRecursoRevocacionFacade;
    }

    /**
     * Metodo para enviar correo.
     * 
     * @param numeroAsunto
     * @param idPersona
     */

    public void enviarCorreoFacade(String numeroAsunto, Long idPersona, String nombreTarea) {
        this.turnarRecursoRevocacionFacade.enviarCorreo(numeroAsunto, idPersona, nombreTarea);

    }

    public String getIdeTareaOrigen() {
        return turnarRecursoRevocacionFacade.getIdeTareaOrigen();
    }

    public List<CatalogoDTO> obtenerMotivosRechazo() {
        return getTurnarRecursoRevocacionFacade().obtenerMotivosRechazo();
    }

}
