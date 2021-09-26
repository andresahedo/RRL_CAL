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
import mx.gob.sat.siat.juridica.base.dto.SolicitudDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.dto.UnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.rrl.api.AtenderSolicitudRevocacionFacade;
import mx.gob.sat.siat.juridica.rrl.dto.ResolucionAbogadoDTO;
import mx.gob.sat.siat.juridica.rrl.dto.ResolucionImpugnadaDTO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * Bussines que implementa la logica de negocio de un abogado.
 * 
 * @author softtek
 * 
 */
@ManagedBean(name = "abogadoBussines")
@NoneScoped
public class AbogadoBussines extends BaseBussinessBean {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 7367667025409007652L;

    /**
     * Facade para atender solicitudes de revocacion.
     */
    @ManagedProperty("#{atenderSolicitudRevocacionFacade}")
    private AtenderSolicitudRevocacionFacade atenderSolicitudRevocacionFacade;

    public TramiteDTO validarMedioDeDefensa(ResolucionImpugnadaDTO resolucionImpugnadaDTO) {

        return atenderSolicitudRevocacionFacade.validarMedioDefensaResolucion(resolucionImpugnadaDTO);
    }

    /**
     * Metodo para obtener los catalogos.
     * 
     * @param resolucionAbogadoDTO
     */
    public void obtenerCatalogos(ResolucionAbogadoDTO resolucionAbogadoDTO) {

        getAtenderSolicitudRevocacionFacade().guardar(resolucionAbogadoDTO);
    }

    /**
     * Metodo para validar la resolucion existente.
     * 
     * @param resol
     * @return
     */
    public ResolucionImpugnadaDTO validarResolucionExistente(ResolucionImpugnadaDTO resol) {
        return atenderSolicitudRevocacionFacade.verificarExistencia(resol);
    }

    /**
     * Metodo para validar la confirmacion de guardar.
     * 
     * @param numFolioResolucion
     * @return
     */
    public boolean validaBanderaGuardar(String numFolioResolucion) {
        return atenderSolicitudRevocacionFacade.validaBanderaGuardar(numFolioResolucion);

    }

    /**
     * Metodo para obtener los catalogos.
     * 
     * @param tipo
     * @return
     */
    public List<CatalogoDTO> obtenerCatalogos(String tipo) {
        List<CatalogoDTO> catalogoDTOs = new ArrayList<CatalogoDTO>();
        if (tipo.equals("concepto")) {

            catalogoDTOs = atenderSolicitudRevocacionFacade.obtenerCatalogoConcepto();

        }
        else if (tipo.equals("recurso")) {
            catalogoDTOs = atenderSolicitudRevocacionFacade.obtenerCatalogoCaracteristicas();

        }
        else if (tipo.equals("procedimiento")) {
            catalogoDTOs = atenderSolicitudRevocacionFacade.obtenerCatalogoRecurso();

        }
        return catalogoDTOs;

    }

    /**
     * Metodo para obtener el catalogo Autoridad.
     * 
     * @return
     */
    public List<UnidadAdministrativaDTO> obtenerCatalogoAutoridad() {

        return atenderSolicitudRevocacionFacade.obtenerCatalogoUnidadAdministrativa();
    }

    public List<UnidadAdministrativaDTO> obtenerCatalogoAutoridadVig() {

        return atenderSolicitudRevocacionFacade.obtenerCatalogoUnidadAdministrativaVig();
    }
    /**
     * Metodo para guardar resolucion del abogado.
     * 
     * @param resolucionAbogado
     */
    public void guardarResolucion(ResolucionAbogadoDTO resolucionAbogado) {
        this.atenderSolicitudRevocacionFacade.guardar(resolucionAbogado);

    }

    /**
     * Metodo para mostrar las resoluciones correspondiente a una
     * solicitud y a un tramite.
     * 
     * @param solicitud
     * @param tramiteDTO
     * @return
     */
    public ResolucionAbogadoDTO mostrarResoluciones(SolicitudDTO solicitud, TramiteDTO tramiteDTO) {

        return this.atenderSolicitudRevocacionFacade.obtenerInformacionFuncionario(solicitud, tramiteDTO);
    }

    public AtenderSolicitudRevocacionFacade getAtenderSolicitudRevocacionFacade() {
        return atenderSolicitudRevocacionFacade;
    }

    public void setAtenderSolicitudRevocacionFacade(AtenderSolicitudRevocacionFacade atenderSolicitudRevocacionFacade) {
        this.atenderSolicitudRevocacionFacade = atenderSolicitudRevocacionFacade;
    }

    public void actualizarAsunto(String numeroAsunto, int idInstancia, String rfcUsuario) {
        getAtenderSolicitudRevocacionFacade().actualizaDatosBP(idInstancia, numeroAsunto, rfcUsuario);

    }

    public String getIdeTareaOrigen() {
        return getAtenderSolicitudRevocacionFacade().getIdeTareaOrigen();
    }

    public String obtenerObservacionTurnado(String numeroAsunto) {
        return getAtenderSolicitudRevocacionFacade().obtenerObservacionTurnado(numeroAsunto);
    }

}
