package mx.gob.sat.siat.juridica.base.web.controller.bean.controller;

import com.softtek.idc.client.util.exception.ContribuyenteInactivoException;
import com.softtek.idc.client.util.exception.ContribuyenteNoEncontradoException;
import com.softtek.idc.client.util.exception.IDCException;
import com.softtek.idc.client.util.exception.RFCNoVigenteException;
import com.softtek.idc.common.exceptions.IDCNoDisponibleException;
import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;
import mx.gob.sat.siat.juridica.base.dto.*;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.BuscarPersonaSolicitanteBussines;
import mx.gob.sat.siat.juridica.buzon.exception.BuzonNoDisponibleException;
import mx.gob.sat.siat.juridica.buzon.exception.CorreoNoRegistradoException;
import mx.gob.sat.siat.juridica.ca.util.constants.RegistroSolicitudConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public abstract class BaseBuscarSolicitanteController extends BaseControllerBean {

    /**
     * 
     */
    private static final long serialVersionUID = -5527277131868249562L;
    /**
     * 
     */
    private final transient Logger logger = LoggerFactory.getLogger(getClass());

    private static final String MENSAJE_BT_NO_DISPONIBLE = "op.validaciones.buzonTributarioNoDisponible";

    @ManagedProperty("#{buscarPersonaBussines}")
    private BuscarPersonaSolicitanteBussines buscarPersonaBussines;
    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;
    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;
    private PersonaSolicitudDTO personaSolicitud;
    private boolean blnRegistrar;
    private boolean blnPersonaFisica;
    private boolean blnPersonaMoral;
    private boolean blnMostrarPanelConsulta;
    private String url;
    private List<CatalogoDTO> comboTiposPersona;
    private String tipoPersonaSeleccionada;
    private PaisDTO paisDTO;
    private List<EntidadFederativaDTO> estados;
    private boolean blnPanelRegistro;
    private EntidadFederativaDTO estadoSelecDTO;
    private List<DelegacionMunicipioDTO> delegaciones;
    private DelegacionMunicipioDTO delegacionSelectedDTO;
    private ColoniaDTO coloniaDTO;
    private List<ColoniaDTO> colonias;
    private LocalidadDTO localidadDTO;
    private List<LocalidadDTO> localidades;
    private DomicilioSolicitudDTO domicilio;
    private boolean botonSiguiente;
    private boolean requiredCp;
    private boolean blnRfc;
    private boolean blnCorreo;

    @PostConstruct
    public void init() {
        personaSolicitud = new PersonaSolicitudDTO();
        setComboTiposPersona(buscarPersonaBussines.comboTiposPersona());
        paisDTO = buscarPersonaBussines.obtenerPais();
        estados = buscarPersonaBussines.obtenerEstados();
        estadoSelecDTO = new EntidadFederativaDTO();
        delegacionSelectedDTO = new DelegacionMunicipioDTO();
        delegaciones = new ArrayList<DelegacionMunicipioDTO>();
        coloniaDTO = new ColoniaDTO();
        colonias = new ArrayList<ColoniaDTO>();
        localidadDTO = new LocalidadDTO();
        domicilio = new DomicilioSolicitudDTO();
        personaSolicitud.setDomicilio(domicilio);
        setBotonSiguiente(false);
        requiredCp = true;
        blnRfc = true;
        blnCorreo = true;

    }

    public void guardarPersonaRegistrada(ActionEvent event) {
        blnRfc = false;
        requiredCp = true;
        
        /*Complementa el obj coloniaDTO seleccionado.*/
        for(ColoniaDTO col : colonias){
            if(coloniaDTO.getClave().equals(col.getClave())){
                coloniaDTO = col;
                break;
            }
        }
        
        PersonaSolicitudDTO persona = new PersonaSolicitudDTO();

        try {
            persona = buscarPersonaBussines.buscarPersonaSolicitante(getPersonaSolicitud().getRfc());
        }
        catch (IDCNoDisponibleException e) {
            // TODO Auto-generated catch block
            logger.error("", e);
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", messages
                            .getString("oficialia.busqueda.idcnodisponible")));
        }
        catch (IDCException e) {
            // TODO Auto-generated catch block
            logger.error("", e);
        }
        catch (ContribuyenteNoEncontradoException e) {
            logger.error("", e);
            // TODO Auto-generated catch block
        }
        catch (ContribuyenteInactivoException e) {
            // TODO Auto-generated catch block
            logger.error("", e);
        }
        catch (RFCNoVigenteException e) {
            // TODO Auto-generated catch block
            logger.error("", e);
        }
        catch (BuzonNoDisponibleException e) {
            logger.error("", e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg.getString(MENSAJE_BT_NO_DISPONIBLE)));
            continuarBuscarPersonaSolicitante(persona);
            blnCorreo = false;
        }
        catch (CorreoNoRegistradoException e) {
            logger.error("", e);
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                            .getString("op.validaciones.correoNoCapturado")));

        }

        if (persona != null && persona.getRfc() != null) {
            if (!persona.getBlnTieneCorreo()) {
                FacesContext.getCurrentInstance()
                        .addMessage(
                                null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                                        .getString(MENSAJE_BT_NO_DISPONIBLE)));
                continuarBuscarPersonaSolicitante(persona);
                blnCorreo = false;
            }
            else if (persona.getDomicilio().getCorreoElectronico().equals("")) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                                .getString("op.validaciones.correoNoCapturado")));
                blnCorreo = false;
            }
        }
        else {
            persona = new PersonaSolicitudDTO();
        }

        continuarGuardar(persona);
    }

    private void continuarGuardar(PersonaSolicitudDTO persona) {
        if (personaSolicitud.getRfc().equals(persona.getRfc())) {
            validarTipoPersona();
            setPersonaSolicitud(persona);
            setBlnMostrarPanelConsulta(true);
            setBlnPanelRegistro(false);
            setBotonSiguiente(true);
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", messages
                            .getString("oficialia.registro.contribuyente.usuarioregistrado")));

        }
        else {
            getPersonaSolicitud().getDomicilio().setColonia(getColoniaDTO().getNombre());
            getPersonaSolicitud().getDomicilio().setDelegacionMunicipio(getDelegacionSelectedDTO().getNombre());
            getPersonaSolicitud().getDomicilio().setEstado(getEstadoSelecDTO().getNombre());
            getPersonaSolicitud().getDomicilio().setNombrePais(paisDTO.getNombre());
            getPersonaSolicitud().getDomicilio().setCvePais(paisDTO.getClave());
            getPersonaSolicitud().getDomicilio().setCveEstado(estadoSelecDTO.getClave());
            getPersonaSolicitud().getDomicilio().setCveDelegacion(delegacionSelectedDTO.getClave());
            getPersonaSolicitud().getDomicilio().setCveColonia(coloniaDTO.getClave());
            getPersonaSolicitud().getDomicilio().setCveLocalidad(localidadDTO.getClave());
            if (!getPersonaSolicitud().getBlnExtranjero()) {
                getPersonaSolicitud().setBlnExtranjero(false);
            }
            botonSiguiente = true;
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "", messages
                            .getString("oficialia.confirmacion.guardado")));
        }

    }

    public void iniciarRegistro() {
        personaSolicitud.setDomicilio(domicilio);
        this.validarTipoPersona();
        blnPanelRegistro = true;

    }

    public void cargarComboDelegaciones() {
        for(EntidadFederativaDTO edo : estados){
            if(estadoSelecDTO.getClave().equals(edo.getClave())){
                estadoSelecDTO = edo;
                break;
            }
        }
        delegaciones = buscarPersonaBussines.obtenerDelegMunicipioPorCveEstado(estadoSelecDTO.getClave());
    }

    public void cargarComboColonias() {
        for(DelegacionMunicipioDTO del : delegaciones){
            if(delegacionSelectedDTO.getClave().equals(del.getClave())){
                delegacionSelectedDTO = del;
                break;
            }
        }
        colonias = buscarPersonaBussines.buscarColoniaPorClaveDelegacion(delegacionSelectedDTO.getClave());
    }

    public void cargarComboLocalidades() {
        for(DelegacionMunicipioDTO del : delegaciones){
            if(delegacionSelectedDTO.getClave().equals(del.getClave())){
                delegacionSelectedDTO = del;
                break;
            }
        }
        localidades = buscarPersonaBussines.buscarLocalidadesPorCveMunicipio(delegacionSelectedDTO.getClave());
    }

    public void agregarCodigoPostal() {
        for (ColoniaDTO col : colonias) {
            if (col.getClave().equals(getColoniaDTO().getClave())) {
                getPersonaSolicitud().getDomicilio().setCodigoPostal(col.getCp());
            }
        }

    }

    public void buscarDireccionPorCP(AjaxBehaviorEvent event) {
        requiredCp = false;
        UIInput cp = (UIInput) event.getComponent().getParent().findComponent("inputCodigoPostal");
        String codigo = cp.getValue().toString();
        if (codigo != null && !codigo.isEmpty()) {
            colonias = buscarPersonaBussines.buscarColoniasPorCp(personaSolicitud.getDomicilio().getCodigoPostal());
            localidades = buscarPersonaBussines.buscarLocalidadPorCP(personaSolicitud.getDomicilio().getCodigoPostal());
            if (colonias != null && !colonias.isEmpty()) {
                coloniaDTO = colonias.get(0);
                delegacionSelectedDTO = colonias.get(0).getDelegacionMunicipioDTO();
                for(EntidadFederativaDTO edo : estados){
                    if(delegacionSelectedDTO.getEntidadFederativaDTO().getClave().equals(edo.getClave())){
                        estadoSelecDTO = edo;
                        break;
                    }
                }
                delegaciones = buscarPersonaBussines.obtenerDelegMunicipioPorCveEstado(estadoSelecDTO.getClave());
                localidadDTO = localidades.get(0);
            }

        }
        else {
            estadoSelecDTO = new EntidadFederativaDTO();
            delegaciones.clear();
            delegacionSelectedDTO = new DelegacionMunicipioDTO();
            colonias.clear();
            coloniaDTO = new ColoniaDTO();
            localidades.clear();
            localidadDTO = new LocalidadDTO();
            requiredCp = true;
        }

    }

    public void buscarPersonaSolicitante() {
        validarTipoPersona();

        blnRegistrar = false;
        blnMostrarPanelConsulta = false;
        domicilio = new DomicilioSolicitudDTO();
        PersonaSolicitudDTO personaSol = new PersonaSolicitudDTO();
        personaSol.setDomicilio(domicilio);
        String rfc = getPersonaSolicitud().getRfc();
        String rfcUpper = rfc.toUpperCase();
        try {
            personaSol = buscarPersonaBussines.buscarPersonaSolicitante(rfcUpper);
        }
        catch (IDCNoDisponibleException e) {
            // TODO Auto-generated catch block
            logger.error("", e);
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", messages
                            .getString("oficialia.busqueda.idcnodisponible")));
        }
        catch (IDCException e) {
            // TODO Auto-generated catch block
            logger.error("", e);
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", messages
                            .getString("oficialia.busqueda.idcnodisponible")));
        }
        catch (ContribuyenteNoEncontradoException e) {
            // TODO Auto-generated catch block
            logger.error("", e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", messages.getString("oficialia.noresultados")));
        }
        catch (ContribuyenteInactivoException e) {
            // TODO Auto-generated catch block
            logger.error("", e);
        }
        catch (RFCNoVigenteException e) {
            // TODO Auto-generated catch block
            logger.error("", e);
        }
        catch (BuzonNoDisponibleException e) {
            logger.error("", e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg.getString(MENSAJE_BT_NO_DISPONIBLE)));
            continuarBuscarPersonaSolicitante(personaSol);
            blnCorreo = false;

        }
        catch (CorreoNoRegistradoException e) {
            logger.error("", e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg.getString(MENSAJE_BT_NO_DISPONIBLE)));
            continuarBuscarPersonaSolicitante(personaSol);
            blnCorreo = false;
        }

        if (personaSol != null && personaSol.getRfc() != null) {

            if (!personaSol.getBlnTieneCorreo()) {
                FacesContext.getCurrentInstance()
                        .addMessage(
                                null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                                        .getString(MENSAJE_BT_NO_DISPONIBLE)));
                blnCorreo = false;
            }
            else if (personaSol.getDomicilio().getCorreoElectronico().equals("")) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                                .getString("op.validaciones.correoNoCapturado")));
                blnCorreo = false;
            }

        }
        continuarBuscarPersonaSolicitante(personaSol);
    }

    private void continuarBuscarPersonaSolicitante(PersonaSolicitudDTO personaSol) {
        if (personaSol == null || personaSol.getRfc() == null || personaSol.getRfc().equals("")) {

            blnRegistrar = true;
            botonSiguiente = false;
            blnMostrarPanelConsulta = false;
            blnPanelRegistro = false;
        }

        else {

            if (getTipoPersonaSeleccionada().equals("PRSOLCMOR") && personaSol.getApellidoPaterno() != null
                    && !personaSol.getApellidoPaterno().equals("")) {
                setBlnMostrarPanelConsulta(false);
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "", messages
                                .getString("oficialia.busqueda.contribuyente.errorrfc")));
            }
            else {
                if (getTipoPersonaSeleccionada().equals("PRSOLCFIS") && personaSol.getRazonSocial() != null
                        && !personaSol.getRazonSocial().equals("")) {

                    setBlnMostrarPanelConsulta(false);
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "", messages
                                    .getString("oficialia.busqueda.contribuyente.errorrfc")));
                }
                else {
                    setBlnMostrarPanelConsulta(true);
                    botonSiguiente = true;
                    blnPanelRegistro = false;
                    blnRegistrar = false;
                }
            }
        }

        personaSolicitud = personaSol;
    }

    public void validarTipoPersona() {
        blnPersonaFisica = false;
        blnPersonaMoral = false;
        if (getTipoPersonaSeleccionada() != null && getTipoPersonaSeleccionada().equals("PRSOLCFIS")) {
            blnPersonaFisica = true;
            blnPersonaMoral = false;

        }
        else {
            blnPersonaMoral = true;
            blnPersonaFisica = false;
        }
    }

    private void redireccionarPagina(String url) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().setResponseCharacterEncoding("UTF-8");
        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) facesContext.getApplication().getNavigationHandler();
        configurableNavigationHandler.performNavigation(url + RegistroSolicitudConstants.FACES_REDIRECT);
    }

    public void continuarRegistro(ActionEvent event) {

        getPersonaSolicitud().getDomicilio().setCveEstado(
                getPersonaSolicitud().getDomicilio().getCveEstado() == null ? estadoSelecDTO.getClave()
                        : getPersonaSolicitud().getDomicilio().getCveEstado());
        getPersonaSolicitud().getDomicilio().setCveDelegacion(
                getPersonaSolicitud().getDomicilio().getCveDelegacion() == null ? delegacionSelectedDTO.getClave()
                        : getPersonaSolicitud().getDomicilio().getCveDelegacion());
        getPersonaSolicitud().getDomicilio().setCveColonia(
                getPersonaSolicitud().getDomicilio().getCveColonia() == null ? coloniaDTO.getClave()
                        : getPersonaSolicitud().getDomicilio().getCveColonia());
        getPersonaSolicitud().getDomicilio().setCveLocalidad(
                getPersonaSolicitud().getDomicilio().getCveLocalidad() == null ? localidadDTO.getClave()
                        : getPersonaSolicitud().getDomicilio().getCveLocalidad());
        getFlash().put("solicitante", getPersonaSolicitud());
        redireccionarPagina(getUrl());
    };

    /**
     * @return the buscarPersonaBussines
     */
    public BuscarPersonaSolicitanteBussines getBuscarPersonaBussines() {
        return buscarPersonaBussines;
    }

    /**
     * @param buscarPersonaBussines
     *            the buscarPersonaBussines to set
     */
    public void setBuscarPersonaBussines(BuscarPersonaSolicitanteBussines buscarPersonaBussines) {
        this.buscarPersonaBussines = buscarPersonaBussines;
    }

    /**
     * @return the blnRegistrar
     */
    public boolean isBlnRegistrar() {
        return blnRegistrar;
    }

    /**
     * @param blnRegistrar
     *            the blnRegistrar to set
     */
    public void setBlnRegistrar(boolean blnRegistrar) {
        this.blnRegistrar = blnRegistrar;
    }

    /**
     * @return the blnPersonaFisica
     */
    public boolean isBlnPersonaFisica() {
        return blnPersonaFisica;
    }

    /**
     * @param blnPersonaFisica
     *            the blnPersonaFisica to set
     */
    public void setBlnPersonaFisica(boolean blnPersonaFisica) {
        this.blnPersonaFisica = blnPersonaFisica;
    }

    /**
     * @return the blnPersonaMoral
     */
    public boolean isBlnPersonaMoral() {
        return blnPersonaMoral;
    }

    /**
     * @param blnPersonaMoral
     *            the blnPersonaMoral to set
     */
    public void setBlnPersonaMoral(boolean blnPersonaMoral) {
        this.blnPersonaMoral = blnPersonaMoral;
    }

    /**
     * @return the blnMostrarPanelConsulta
     */
    public boolean isBlnMostrarPanelConsulta() {
        return blnMostrarPanelConsulta;
    }

    /**
     * @param blnMostrarPanelConsulta
     *            the blnMostrarPanelConsulta to set
     */
    public void setBlnMostrarPanelConsulta(boolean blnMostrarPanelConsulta) {
        this.blnMostrarPanelConsulta = blnMostrarPanelConsulta;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the comboTiposPersona
     */
    public List<CatalogoDTO> getComboTiposPersona() {
        return comboTiposPersona;
    }

    /**
     * @param comboTiposPersona
     *            the comboTiposPersona to set
     */
    public void setComboTiposPersona(List<CatalogoDTO> comboTiposPersona) {
        this.comboTiposPersona = comboTiposPersona;
    }

    /**
     * @return the tipoPersonaSeleccionada
     */
    public String getTipoPersonaSeleccionada() {
        return tipoPersonaSeleccionada;
    }

    /**
     * @param tipoPersonaSeleccionada
     *            the tipoPersonaSeleccionada to set
     */
    public void setTipoPersonaSeleccionada(String tipoPersonaSeleccionada) {
        this.tipoPersonaSeleccionada = tipoPersonaSeleccionada;
    }

    /**
     * @return the personaSolicitud
     */
    public PersonaSolicitudDTO getPersonaSolicitud() {
        return personaSolicitud;
    }

    /**
     * @param personaSolicitud
     *            the personaSolicitud to set
     */
    public void setPersonaSolicitud(PersonaSolicitudDTO personaSolicitud) {
        this.personaSolicitud = personaSolicitud;
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
     * @return the paisDTO
     */
    public PaisDTO getPaisDTO() {
        return paisDTO;
    }

    /**
     * @param paisDTO
     *            the paisDTO to set
     */
    public void setPaisDTO(PaisDTO paisDTO) {
        this.paisDTO = paisDTO;
    }

    /**
     * @return the estados
     */
    public List<EntidadFederativaDTO> getEstados() {
        return estados;
    }

    /**
     * @param estados
     *            the estados to set
     */
    public void setEstados(List<EntidadFederativaDTO> estados) {
        this.estados = estados;
    }

    /**
     * @return the blnPanelRegistro
     */
    public boolean isBlnPanelRegistro() {
        return blnPanelRegistro;
    }

    /**
     * @param blnPanelRegistro
     *            the blnPanelRegistro to set
     */
    public void setBlnPanelRegistro(boolean blnPanelRegistro) {
        this.blnPanelRegistro = blnPanelRegistro;
    }

    /**
     * @return the estadoSelecDTO
     */
    public EntidadFederativaDTO getEstadoSelecDTO() {
        return estadoSelecDTO;
    }

    /**
     * @param estadoSelecDTO
     *            the estadoSelecDTO to set
     */
    public void setEstadoSelecDTO(EntidadFederativaDTO estadoSelecDTO) {
        this.estadoSelecDTO = estadoSelecDTO;
    }

    /**
     * @return the delegaciones
     */
    public List<DelegacionMunicipioDTO> getDelegaciones() {
        return delegaciones;
    }

    /**
     * @param delegaciones
     *            the delegaciones to set
     */
    public void setDelegaciones(List<DelegacionMunicipioDTO> delegaciones) {
        this.delegaciones = delegaciones;
    }

    /**
     * @return the delegacionSelectedDTO
     */
    public DelegacionMunicipioDTO getDelegacionSelectedDTO() {
        return delegacionSelectedDTO;
    }

    /**
     * @param delegacionSelectedDTO
     *            the delegacionSelectedDTO to set
     */
    public void setDelegacionSelectedDTO(DelegacionMunicipioDTO delegacionSelectedDTO) {
        this.delegacionSelectedDTO = delegacionSelectedDTO;
    }

    /**
     * @return the coloniaDTO
     */
    public ColoniaDTO getColoniaDTO() {
        return coloniaDTO;
    }

    /**
     * @param coloniaDTO
     *            the coloniaDTO to set
     */
    public void setColoniaDTO(ColoniaDTO coloniaDTO) {
        this.coloniaDTO = coloniaDTO;
    }

    /**
     * @return the colonias
     */
    public List<ColoniaDTO> getColonias() {
        return colonias;
    }

    /**
     * @param colonias
     *            the colonias to set
     */
    public void setColonias(List<ColoniaDTO> colonias) {
        this.colonias = colonias;
    }

    /**
     * @return the localidadDTO
     */
    public LocalidadDTO getLocalidadDTO() {
        return localidadDTO;
    }

    /**
     * @param localidadDTO
     *            the localidadDTO to set
     */
    public void setLocalidadDTO(LocalidadDTO localidadDTO) {
        this.localidadDTO = localidadDTO;
    }

    /**
     * @return the localidades
     */
    public List<LocalidadDTO> getLocalidades() {
        return localidades;
    }

    /**
     * @param localidades
     *            the localidades to set
     */
    public void setLocalidades(List<LocalidadDTO> localidades) {
        this.localidades = localidades;
    }

    /**
     * @return the domicilio
     */
    public DomicilioSolicitudDTO getDomicilio() {
        return domicilio;
    }

    /**
     * @param domicilio
     *            the domicilio to set
     */
    public void setDomicilio(DomicilioSolicitudDTO domicilio) {
        this.domicilio = domicilio;
    }

    public boolean isBotonSiguiente() {
        return botonSiguiente;
    }

    public void setBotonSiguiente(boolean botonSiguiente) {
        this.botonSiguiente = botonSiguiente;
    }

    public boolean isRequiredCp() {
        return requiredCp;
    }

    public boolean isBlnRfc() {
        return blnRfc;
    }

    public void setBlnRfc(boolean blnRfc) {
        this.blnRfc = blnRfc;
    }

    public boolean isBlnCorreo() {
        return blnCorreo;
    }

    public void setBlnCorreo(boolean blnCorreo) {
        this.blnCorreo = blnCorreo;
    }

}
