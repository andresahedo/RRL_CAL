package mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.dao.domain.model.PersonaInterna;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.InformacionUsuarioDTO;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.AdminUsuariosDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.FiltroPersonaTareaDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.PersonaInternaDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.PersonaReasignacionDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.business.ReasignarTareaBusiness;
import mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.utility.PersonaReasignacionLazyList;
import mx.gob.sat.siat.juridica.rrl.dto.DatosPersonaTareaDTO;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.text.MessageFormat;
import java.util.*;

@ViewScoped
@ManagedBean(name = "reasignarTareaController")
public class ReasignarTareaController extends AdministracionUsuariosBaseController {

    @ManagedProperty("#{reasignarTareaBusiness}")
    private transient ReasignarTareaBusiness reasignarTareaBusiness;

    private List<CatalogoDTO> listaDeTramites = new ArrayList<CatalogoDTO>();

    private List<CatalogoDTO> listaDeModalidades = new ArrayList<CatalogoDTO>();

    private List<CatalogoDTO> listaDeRoles = new ArrayList<CatalogoDTO>();

    private String valorTramite;

    private static final long serialVersionUID = -4248974582723145902L;
    private Map<String, String> data = new LinkedHashMap<String, String>();
    private transient DataTable dataTableFil = new DataTable();
    private FiltroPersonaTareaDTO filtroPersonaTareaDTO = new FiltroPersonaTareaDTO();
    private List<DatosPersonaTareaDTO> listDatosPersona = new ArrayList<DatosPersonaTareaDTO>();
    private DatosPersonaTareaDTO[] listaPersonasSelecionadas;
    private DatosPersonaTareaDTO datosPersonaTareaDTOPruba = new DatosPersonaTareaDTO();
    private PersonaInterna empleado = new PersonaInterna();
    private Long numeroEmpleado;
    private String rfc = "";
    private String nombre = "";

    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;
    private PersonaReasignacionLazyList personaReasignacionLazyList;
    private String mensajePersona = "";
    private Boolean showMessages;
    private CatalogoDTO tramiteSelected;
    private CatalogoDTO modalidadSelected;
    private CatalogoDTO permisoSelected;
    private List<PersonaReasignacionDTO> personaReasignacionDTO;
    private PersonaReasignacionDTO personaReasignar;
    private boolean botonReasignar;
    private boolean botonBuscar;
    private boolean botonBuscarEmpleado;
    private AdminUsuariosDTO admin;
    private List<DatosPersonaTareaDTO> datosTareasBP;
    private Integer numDatosTareasBP = 0;
    private String nomTramiteTareas = "";
    private boolean desactivaBusquedaEmp;
    private String msgConfirmReasignar = "";
    private PersonaInternaDTO datosEmpleadoFlash;

    /**
     * Propiedades de pantallas anteriores.
     */

    private int botonCancelar;
    private List<InformacionUsuarioDTO> rolesUsuario;

    public List<DatosPersonaTareaDTO> getDatosTareasBP() {
        return datosTareasBP;
    }

    public void setDatosTareasBP(List<DatosPersonaTareaDTO> datosTareasBP) {
        this.datosTareasBP = datosTareasBP;
    }

    /**
     * Metodo que carga las modalidades de acuerdo al servicio
     * enviado.
     */
    public void cargarModalidadByTipoTramite() {
        // Variable que recibir� el id del servicio.
        listaDeModalidades = reasignarTareaBusiness.getModalidadByTipoTramite(tramiteSelected.getClave());
    }

    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;

    /**
     * Metodo que carga los catalogos despues de la creacion del bean.
     */
    @PostConstruct
    public void initFiltro() {
        super.init();
        personaReasignacionDTO = new ArrayList<PersonaReasignacionDTO>();
        setAdmin((AdminUsuariosDTO) getFlash().get(VistaConstantes.DATOS_ADMINUSUARIO_DTO));
        if (getAdmin() != null) {
            filtroPersonaTareaDTO.setClaveUnidadAdministrativa(getAdmin().getIdUnidadAmin());
        }
        setDesactivaBusquedaEmp(false);
        if ((PersonaInternaDTO) getFlash().get("empleado") == null) {
            setDatosEmpleadoFlash((PersonaInternaDTO) getFlash().get("datosEmpleado"));
        }
        else {
            setDatosEmpleadoFlash((PersonaInternaDTO) getFlash().get("empleado"));
        }

        if (null != getDatosEmpleadoFlash()) {
            setNumeroEmpleado(getDatosEmpleadoFlash().getNumeroEmpleado());
            buscarEmpleadoPorNumero();
            setDesactivaBusquedaEmp(true);
        }
        obtenerDatosCombos();
        listDatosPersona.clear();
        String show =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("showMessages");
        setShowMessages(null == show ? true : null);
        numDatosTareasBP = 0;
        setUrlsRegreso((LinkedList<String>) getFlash().get(VistaConstantes.BACK));

        if (getFlash().get("accionBotonCancelar") != null) {
            setBotonCancelar((Integer) getFlash().get("accionBotonCancelar"));
        }
        else {
            setBotonCancelar(0);
        }
        if (getFlash().get("rolesUsuario") != null) {
            setRolesUsuario((List<InformacionUsuarioDTO>) getFlash().get("rolesUsuario"));
        }
        else {
            setRolesUsuario(new ArrayList<InformacionUsuarioDTO>());
        }
    }



    public void numeroTareasBP() {
        if (null == datosTareasBP || datosTareasBP.isEmpty()) {
            setNumDatosTareasBP(0);
        }
        else {
            setNumDatosTareasBP(datosTareasBP.size());
        }
    }

    public void buscarEmpleadoPorNumero() {
        obtenerDatosCombos();
        datosTareasBP = new ArrayList<DatosPersonaTareaDTO>();
        personaReasignacionDTO = new ArrayList<PersonaReasignacionDTO>();
        numeroTareasBP();
        empleado = reasignarTareaBusiness.obtenerEmpleado(numeroEmpleado);
        if (empleado != null) {
            rfc = empleado.getRfc();
            nombre = empleado.getNombre() + " " + empleado.getApellidoPaterno() + " " + empleado.getApellidoMaterno();
            filtroPersonaTareaDTO.setRfcEmpleado(rfc);
            botonBuscar = true;
        }
        else {
            botonBuscar = false;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No existe el Empleado."));
            return;
        }
    }



    public void confirmarReasignar() {
        String[] param = new String[2];
        param[0] = getPersonaReasignar().getNombreAbogado();
        param[1] = getPersonaReasignar().getRfc();
        setMsgConfirmReasignar(MessageFormat.format(messages.getString("usuarios.reasignar.tarea.confirmacion"),
                (Object[]) param));
    }

    public void reasignar(ActionEvent e) {

        reasignarTareaBusiness.reasignar(Arrays.asList(listaPersonasSelecionadas), permisoSelected.getClave(),
                personaReasignar.getRfc(), rfc);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "", errorMsg.getString("vuj.reasignar.exito")));
        datosTareasBP = new ArrayList<DatosPersonaTareaDTO>();
        numeroTareasBP();
        personaReasignacionDTO = new ArrayList<PersonaReasignacionDTO>();
        setBotonReasignar(false);
        setBotonBuscarEmpleado(false);
        listaPersonasSelecionadas = null;
        personaReasignar = new PersonaReasignacionDTO();
        setMensajePersona("");
    }

    public void rowSelectCheckbox(SelectEvent event) {

        setBotonBuscarEmpleado(true);

    }

    public void rowUnselectCheckbox(UnselectEvent event) {
        if (getListaPersonasSelecionadas().length < 1) {
            setBotonBuscarEmpleado(false);
        }
    }

    public void obtenerDatosCombos() {
        tramiteSelected = new CatalogoDTO();
        modalidadSelected = new CatalogoDTO();
        permisoSelected = new CatalogoDTO();
        List<String> roles = new ArrayList<String>();
        roles.add("abogado");
        roles.add("administrador");
        listaDeTramites = reasignarTareaBusiness.getTiposDeTramite(); 
        listaDeRoles = reasignarTareaBusiness.getRoles(roles);
    }

    @Override
    public void back() {
        getFlash().put(VistaConstantes.DATOS_ADMINUSUARIO_DTO, getAdminUsuariosDTO());
        getFlash().put("empleado", getDatosEmpleadoFlash());
        getFlash().put("datosEmpleado", getDatosEmpleadoFlash());

        getFlash().put("accionBotonCancelar", getBotonCancelar());
        getFlash().put("rolesUsuario", getRolesUsuario());
        super.back();
    }

    public void onRowSelect(SelectEvent event) {
        personaReasignar = (PersonaReasignacionDTO) event.getObject();
        if (personaReasignar == null) {
            botonReasignar = false;
            return;
        }
        else {
            botonReasignar = true;
        }

    }

    /**
     * @return the dataTableFil
     */
    public DataTable getDataTableFil() {
        return dataTableFil;
    }

    /**
     * @param dataTableFil
     *            the dataTableFil to set
     */
    public void setDataTableFil(DataTable dataTableFil) {
        this.dataTableFil = dataTableFil;
    }

    /**
     * @return the filtroPersonaTareaDTO
     */
    public FiltroPersonaTareaDTO getFiltroPersonaTareaDTO() {
        return filtroPersonaTareaDTO;
    }

    /**
     * @param filtroPersonaTareaDTO
     *            the filtroPersonaTareaDTO to set
     */
    public void setFiltroPersonaTareaDTO(FiltroPersonaTareaDTO filtroPersonaTareaDTO) {
        this.filtroPersonaTareaDTO = filtroPersonaTareaDTO;
    }

    /**
     * @return the data
     */
    public Map<String, String> getData() {
        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData(Map<String, String> data) {
        this.data = data;
    }

    /**
     * @return the listDatosPersona
     */
    public List<DatosPersonaTareaDTO> getListDatosPersona() {
        return listDatosPersona;
    }

    /**
     * @param listDatosPersona
     *            the listDatosPersona to set
     */
    public void setListDatosPersona(List<DatosPersonaTareaDTO> listDatosPersona) {
        this.listDatosPersona = listDatosPersona;
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
     * @return the mensajePersona
     */
    public String getMensajePersona() {
        return mensajePersona;
    }

    /**
     * @param mensajePersona
     *            the mensajePersona to set
     */
    public void setMensajePersona(String mensajePersona) {
        this.mensajePersona = mensajePersona;
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

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(Long numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public ReasignarTareaBusiness getReasignarTareaBusiness() {
        return reasignarTareaBusiness;
    }

    public void setReasignarTareaBusiness(ReasignarTareaBusiness reasignarTareaBusiness) {
        this.reasignarTareaBusiness = reasignarTareaBusiness;
    }

    public PersonaReasignacionLazyList getPersonaReasignacionLazyList() {
        return personaReasignacionLazyList;
    }

    public void setPersonaReasignacionLazyList(PersonaReasignacionLazyList personaReasignacionLazyList) {
        this.personaReasignacionLazyList = personaReasignacionLazyList;
    }

    public Boolean getShowMessages() {
        return showMessages;
    }

    public void setShowMessages(Boolean showMessages) {
        this.showMessages = showMessages;
    }

    public List<CatalogoDTO> getListaDeTramites() {
        return listaDeTramites;
    }

    public void setListaDeTramites(List<CatalogoDTO> listaDeTramites) {
        this.listaDeTramites = listaDeTramites;
    }

    public String getValorTramite() {
        return valorTramite;
    }

    public void setValorTramite(String valorTramite) {
        this.valorTramite = valorTramite;
    }

    public List<CatalogoDTO> getListaDeModalidades() {
        return listaDeModalidades;
    }

    public void setListaDeModalidades(List<CatalogoDTO> listaDeModalidades) {
        this.listaDeModalidades = listaDeModalidades;
    }

    public CatalogoDTO getTramiteSelected() {
        return tramiteSelected;
    }

    public void setTramiteSelected(CatalogoDTO tramiteSelected) {
        this.tramiteSelected = tramiteSelected;
    }

    public CatalogoDTO getModalidadSelected() {
        return modalidadSelected;
    }

    public void setModalidadSelected(CatalogoDTO modalidadSelected) {
        this.modalidadSelected = modalidadSelected;
    }

    public CatalogoDTO getPermisoSelected() {
        return permisoSelected;
    }

    public void setPermisoSelected(CatalogoDTO permisoSelected) {
        this.permisoSelected = permisoSelected;
    }

    public List<CatalogoDTO> getListaDeRoles() {
        return listaDeRoles;
    }

    public void setListaDeRoles(List<CatalogoDTO> listaDeRoles) {
        this.listaDeRoles = listaDeRoles;
    }

    public List<PersonaReasignacionDTO> getPersonaReasignacionDTO() {
        return personaReasignacionDTO;
    }

    public void setPersonaReasignacionDTO(List<PersonaReasignacionDTO> personaReasignacionDTO) {
        this.personaReasignacionDTO = personaReasignacionDTO;
    }

    public PersonaReasignacionDTO getPersonaReasignar() {
        return personaReasignar;
    }

    public void setPersonaReasignar(PersonaReasignacionDTO personaReasignar) {
        this.personaReasignar = personaReasignar;
    }

    public boolean isBotonBuscar() {
        return botonBuscar;
    }

    public void setBotonBuscar(boolean botonBuscar) {
        this.botonBuscar = botonBuscar;
    }

    public boolean isBotonReasignar() {
        return botonReasignar;
    }

    public void setBotonReasignar(boolean botonReasignar) {
        this.botonReasignar = botonReasignar;
    }

    public DatosPersonaTareaDTO getDatosPersonaTareaDTOPruba() {
        return datosPersonaTareaDTOPruba;
    }

    public void setDatosPersonaTareaDTOPruba(DatosPersonaTareaDTO datosPersonaTareaDTOPruba) {
        this.datosPersonaTareaDTOPruba = datosPersonaTareaDTOPruba;
    }

    public DatosPersonaTareaDTO[] getListaPersonasSelecionadas() {
        if (listaPersonasSelecionadas != null) {
            DatosPersonaTareaDTO[] arreglo = new DatosPersonaTareaDTO[listaPersonasSelecionadas.length];
            for (int i = 0; i < listaPersonasSelecionadas.length; i++) {
                DatosPersonaTareaDTO doc = listaPersonasSelecionadas[i];
                arreglo[i] = doc;
            }
            return arreglo;
        }
        else {
            return null;
        }
    }

    public void setListaPersonasSelecionadas(DatosPersonaTareaDTO[] listaPersonasSelecionadasArray) {
        if (listaPersonasSelecionadasArray != null) {
            DatosPersonaTareaDTO[] listaPersonasSelecionadasLocal = listaPersonasSelecionadasArray.clone();
            DatosPersonaTareaDTO[] listaPersonasSelecionadasArreglo =
                    new DatosPersonaTareaDTO[listaPersonasSelecionadasLocal.length];
            for (int i = 0; i < listaPersonasSelecionadasLocal.length; i++) {
                DatosPersonaTareaDTO o = listaPersonasSelecionadasLocal[i];
                listaPersonasSelecionadasArreglo[i] = o;
            }
            this.listaPersonasSelecionadas = listaPersonasSelecionadasArreglo;
        }
        else {
            this.listaPersonasSelecionadas = null;
        }
    }

    public AdminUsuariosDTO getAdmin() {
        return admin;
    }

    public void setAdmin(AdminUsuariosDTO admin) {
        this.admin = admin;
    }

    public boolean isBotonBuscarEmpleado() {
        return botonBuscarEmpleado;
    }

    public void setBotonBuscarEmpleado(boolean botonBuscarEmpleado) {
        this.botonBuscarEmpleado = botonBuscarEmpleado;
    }

    public Integer getNumDatosTareasBP() {
        return numDatosTareasBP;
    }

    public void setNumDatosTareasBP(Integer numDatosTareasBP) {
        this.numDatosTareasBP = numDatosTareasBP;
    }

    public String getNomTramiteTareas() {
        return nomTramiteTareas;
    }

    public void setNomTramiteTareas(String nomTramiteTareas) {
        this.nomTramiteTareas = nomTramiteTareas;
    }

    public boolean isDesactivaBusquedaEmp() {
        return desactivaBusquedaEmp;
    }

    public void setDesactivaBusquedaEmp(boolean desactivaBusquedaEmp) {
        this.desactivaBusquedaEmp = desactivaBusquedaEmp;
    }

    public String getMsgConfirmReasignar() {
        return msgConfirmReasignar;
    }

    public void setMsgConfirmReasignar(String msgConfirmReasignar) {
        this.msgConfirmReasignar = msgConfirmReasignar;
    }

    public PersonaInternaDTO getDatosEmpleadoFlash() {
        return datosEmpleadoFlash;
    }

    public void setDatosEmpleadoFlash(PersonaInternaDTO datosEmpleadoFlash) {
        this.datosEmpleadoFlash = datosEmpleadoFlash;
    }

    public int getBotonCancelar() {
        return botonCancelar;
    }

    public void setBotonCancelar(int botonCancelar) {
        this.botonCancelar = botonCancelar;
    }

    public List<InformacionUsuarioDTO> getRolesUsuario() {
        return rolesUsuario;
    }

    public void setRolesUsuario(List<InformacionUsuarioDTO> rolesUsuario) {
        this.rolesUsuario = rolesUsuario;
    }

}
