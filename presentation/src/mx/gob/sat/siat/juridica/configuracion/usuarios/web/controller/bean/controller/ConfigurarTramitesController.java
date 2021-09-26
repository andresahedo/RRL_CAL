/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.dto.TipoTramiteDTO;
import mx.gob.sat.siat.juridica.base.dto.UnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.TipoTramiteUnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.business.ConfigurarTramitesUnidadAdministrativaBusiness;
import mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.model.TramitesAsignarDataModel;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 
 * @author softtek
 * 
 */

@ViewScoped
@ManagedBean
public class ConfigurarTramitesController extends AdministracionUsuariosBaseController {

    /**
     * 
     */
    private static final long serialVersionUID = 9124030772588619656L;

    /**
     * Propiedad para manejar los mensajes en pantalla.
     */
    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;
    /**
     * Propiedad para manejar los mensajes de error en pantalla.
     */
    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;

    @ManagedProperty("#{configurarTramitesBusiness}")
    private transient ConfigurarTramitesUnidadAdministrativaBusiness configurarBusiness;

    private List<UnidadAdministrativaDTO> comboUnidadesAdministrativas;

    private UnidadAdministrativaDTO unidadSelected;

    private String tituloTablaTramites;

    private List<TipoTramiteDTO> tramitesSelected;

    private List<TipoTramiteDTO> modalidadesRRL;

    private List<TipoTramiteDTO> modalidadesCAL;

    private List<TipoTramiteDTO> tramites;

    private TramitesAsignarDataModel tramitesDataModel;

    private List<TipoTramiteUnidadAdministrativaDTO> modalidadesUnidad;

    private String msgValidaActualizar = "";

    private boolean activarDialogo;

    @Override
    public void init() {
        super.init();
        comboUnidadesAdministrativas = obtenerUnidadesAdministrativas();
        activarDialogo = false;
    }

    /**
     * @param datosEmpleado
     *            the datosEmpleado to set
     */
    public void back() {
        getFlash().put(VistaConstantes.DATOS_ADMINUSUARIO_DTO, getAdminUsuariosDTO());
        getFlash().put(VistaConstantes.BACK, getUrlsRegreso());
        super.back();
    }

    public void mostrarTramites(ActionEvent event) {
        if (getUnidadSelected() != null && !getUnidadSelected().getNombre().equals("")) {
            tramitesSelected = new ArrayList<TipoTramiteDTO>();
            tramites = configurarBusiness.buscarTramites(unidadSelected.getClave());
            for (TipoTramiteDTO tram : tramites) {
                if (tram.getBlnServicioSelect()) {
                    tramitesSelected.add(tram);
                }
            }
            tramitesDataModel = new TramitesAsignarDataModel(tramites);
            setTituloTablaTramites(messages.getString("usuarios.tramites.botonTramites") + " "
                    + getUnidadSelected().getNombre());
        }
    }

    public void actualizarTramites() {
        for (TipoTramiteDTO tr : tramitesSelected) {
            tr.setBlnServicioSelect(true);
        }
        configurarBusiness.actualizarTramitesUnidad(getTramitesSelected(), getUnidadSelected().getClave());
        activarDialogo = false;
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "", messages.getString("usuarios.tramites.actualizar")));
    }

    /**
     * Metodo para activar cuadro de dialogo
     */
    public void activaDialogoTramites() {
        activarDialogo = true;
        String[] parametro = new String[2];

        StringBuffer strTramites = new StringBuffer();
        for (int i = 0; i < getTramitesSelected().size(); i++) {
            TipoTramiteDTO tramite = getTramitesSelected().get(i);
            strTramites.append(tramite.getDescripcionServicio());
            if ((i + 1) != getTramitesSelected().size()) {
                strTramites.append(", ");
            }
        }
        parametro[0] = strTramites.toString();
        parametro[1] = getUnidadSelected().getNombre();

        msgValidaActualizar =
                MessageFormat.format(messages.getString("usuarios.adminUnidad.confirmacion"), (Object[]) parametro);
    }

    public void cancelar() {
        activarDialogo = false;
    }

    public List<UnidadAdministrativaDTO> obtenerUnidadesAdministrativas() {
        return configurarBusiness.comboUnidades();
    }

    public void validaActualizarTramites() {
        StringBuffer str = new StringBuffer(messages.getString("usuarios.tramites.actualizar.nTramites"));
        for (int i = 0; i < getTramitesSelected().size(); i++) {
            str.append(getTramitesSelected().get(i).getDescripcionServicio());
            if ((i + 1) != getTramitesSelected().size()) {
                str.append(", ");
            }
        }
        str.append(" a la Unidad Administrativa " + getUnidadSelected().getNombre() + ".");
        setMsgValidaActualizar(str.toString());
    }

    /**
     * @return the messages
     */
    public ResourceBundle getMessages() {
        return messages;
    }

    /**
     * @param messages
     *            the messages to set
     */
    public void setMessages(ResourceBundle messages) {
        this.messages = messages;
    }

    /**
     * @return the errorMsg
     */
    public ResourceBundle getErrorMsg() {
        return errorMsg;
    }

    /**
     * @param errorMsg
     *            the errorMsg to set
     */
    public void setErrorMsg(ResourceBundle errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * @return the configurarBusiness
     */
    public ConfigurarTramitesUnidadAdministrativaBusiness getConfigurarBusiness() {
        return configurarBusiness;
    }

    /**
     * @param configurarBusiness
     *            the configurarBusiness to set
     */
    public void setConfigurarBusiness(ConfigurarTramitesUnidadAdministrativaBusiness configurarBusiness) {
        this.configurarBusiness = configurarBusiness;
    }

    /**
     * @return the comboUnidadesAdministrativas
     */
    public List<UnidadAdministrativaDTO> getComboUnidadesAdministrativas() {
        return comboUnidadesAdministrativas;
    }

    /**
     * @param comboUnidadesAdministrativas
     *            the comboUnidadesAdministrativas to set
     */
    public void setComboUnidadesAdministrativas(List<UnidadAdministrativaDTO> comboUnidadesAdministrativas) {
        this.comboUnidadesAdministrativas = comboUnidadesAdministrativas;
    }

    /**
     * @return the unidadSelected
     */
    public UnidadAdministrativaDTO getUnidadSelected() {
        return unidadSelected;
    }

    /**
     * @param unidadSelected
     *            the unidadSelected to set
     */
    public void setUnidadSelected(UnidadAdministrativaDTO unidadSelected) {
        this.unidadSelected = unidadSelected;
    }

    /**
     * @return the tituloTablaTramites
     */
    public String getTituloTablaTramites() {
        return tituloTablaTramites;
    }

    /**
     * @param tituloTablaTramites
     *            the tituloTablaTramites to set
     */
    public void setTituloTablaTramites(String tituloTablaTramites) {
        this.tituloTablaTramites = tituloTablaTramites;
    }

    /**
     * @return the modalidadesRRL
     */
    public List<TipoTramiteDTO> getModalidadesRRL() {
        return modalidadesRRL;
    }

    /**
     * @param modalidadesRRL
     *            the modalidadesRRL to set
     */
    public void setModalidadesRRL(List<TipoTramiteDTO> modalidadesRRL) {
        this.modalidadesRRL = modalidadesRRL;
    }

    /**
     * @return the modalidadesCAL
     */
    public List<TipoTramiteDTO> getModalidadesCAL() {
        return modalidadesCAL;
    }

    /**
     * @param modalidadesCAL
     *            the modalidadesCAL to set
     */
    public void setModalidadesCAL(List<TipoTramiteDTO> modalidadesCAL) {
        this.modalidadesCAL = modalidadesCAL;
    }

    /**
     * @return the tramitesDataModel
     */
    public TramitesAsignarDataModel getTramitesDataModel() {
        return tramitesDataModel;
    }

    /**
     * @param tramitesDataModel
     *            the tramitesDataModel to set
     */
    public void setTramitesDataModel(TramitesAsignarDataModel tramitesDataModel) {
        this.tramitesDataModel = tramitesDataModel;
    }

    /**
     * @return the modalidadesUnidad
     */
    public List<TipoTramiteUnidadAdministrativaDTO> getModalidadesUnidad() {
        return modalidadesUnidad;
    }

    /**
     * @param modalidadesUnidad
     *            the modalidadesUnidad to set
     */
    public void setModalidadesUnidad(List<TipoTramiteUnidadAdministrativaDTO> modalidadesUnidad) {
        this.modalidadesUnidad = modalidadesUnidad;
    }

    /**
     * @return the tramitesSelected
     */
    public List<TipoTramiteDTO> getTramitesSelected() {
        return tramitesSelected;
    }

    /**
     * @param tramitesSelected
     *            the tramitesSelected to set
     */
    public void setTramitesSelected(List<TipoTramiteDTO> tramitesSelected) {
        this.tramitesSelected = tramitesSelected;
    }

    /**
     * @return the tramites
     */
    public List<TipoTramiteDTO> getTramites() {
        return tramites;
    }

    /**
     * @param tramites
     *            the tramites to set
     */
    public void setTramites(List<TipoTramiteDTO> tramites) {
        this.tramites = tramites;
    }

    public String getMsgValidaActualizar() {
        return msgValidaActualizar;
    }

    public void setMsgValidaActualizar(String msgValidaActualizar) {
        this.msgValidaActualizar = msgValidaActualizar;
    }

    public boolean isActivarDialogo() {
        return activarDialogo;
    }

    public void setActivarDialogo(boolean activarDialogo) {
        this.activarDialogo = activarDialogo;
    }

}
