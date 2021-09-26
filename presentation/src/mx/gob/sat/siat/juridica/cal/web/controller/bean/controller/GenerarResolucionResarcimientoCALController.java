/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.cal.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.web.util.validador.GenerarResolucionValidator;
import mx.gob.sat.siat.juridica.cal.dto.BienResarcimientoDTO;
import mx.gob.sat.siat.juridica.cal.web.controller.bean.business.GeneracionResolucionCALBussines;
import mx.gob.sat.siat.juridica.cal.web.controller.bean.model.BienResarcimientoDataModel;
import mx.gob.sat.siat.juridica.rrl.dto.FiltroBandejaTareaDTO;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller Class to handle atender promocion flow from juridica.
 * 
 * @author antonio.flores Softtek
 * 
 */
@ViewScoped
@ManagedBean(name = "generarResolucionResarcimientoCALController")
public class GenerarResolucionResarcimientoCALController extends BaseControllerBean {

    /**
     * 
     */
    private static final long serialVersionUID = 7829486868843606210L;
    /**
     * Bussines.
     */
    @ManagedProperty(value = "#{generacionResolucionBussines}")
    private transient GeneracionResolucionCALBussines generacionResolucionBussines;
    /**
     * Validator
     */
    @ManagedProperty(value = "#{generarResolucionValidator}")
    private GenerarResolucionValidator generarResolucionValidator;

    /**
     * Object used to get information from bandeja de tarea.
     */
    private FiltroBandejaTareaDTO filtroBandejaTareaDTO;
    /**
     * 
     */
    private Integer modalidadType;
    /**
     * 
     */
    private List<CatalogoDTO> listaResolucion = new ArrayList<CatalogoDTO>();
    /**
     * 
     */
    private List<CatalogoDTO> mediosDefensa = new ArrayList<CatalogoDTO>();
    /**
     * 
     */
    private List<CatalogoDTO> mediosTransporte = new ArrayList<CatalogoDTO>();
    /**
     * 
     */
    private List<CatalogoDTO> listaUsuarios = new ArrayList<CatalogoDTO>();
    /**
     * 
     */
    private BienResarcimientoDataModel bienResarcimientoDataModel;
    /**
     * 
     */
    private BienResarcimientoDTO selectedBienResarcimiento;

    /**
     * Variable para el panel de clasificac&iacute;n
     */
    private Date fechaSesion;
    /**
     * Variable para el panel de clasificac&iacute;n
     */
    private String fraccion;
    /**
     * Variable para el panel de clasificac&iacute;n
     */
    private String minuta;
    /**
     * 
     */
    private CatalogoDTO resolucion;
    /**
     * 
     */
    private CatalogoDTO nombreFirma;
    /**
     * 
     */
    private CatalogoDTO fraccionArancel;
    /**
     * 
     */
    private String numeroAsuntoResarcimiento;
    /**
     * 
     */
    private Integer montoSentencia;
    /**
     * 
     */
    private String medioDefensa;
    /**
     * 
     */
    private String autoridadEmbarga;
    /**
     * 
     */
    private String cumplimientoSentencia;
    /**
     * 
     */
    private Integer montoTotalAutorizado;
    /**
     * 
     */
    private Date fechaEmbargo;
    /**
     * 
     */
    private String medioTransporte;

    /**
     * 
     */
    private Integer valorBien;
    /**
     * 
     */
    private String descripcionBien;
    /**
     * 
     */
    private String clave;
    /**
     * 
     */
    private String firmante;
    /**
     * 
     */
    private Integer montoCalculado;

    /**
     * Lista de resoluciones a guardar.
     */
    private List<BienResarcimientoDTO> bienResarcimientoDTOList = new ArrayList<BienResarcimientoDTO>();

    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;
    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;

    @PostConstruct
    public void initAtenderPromocion() {
        bienResarcimientoDTOList = new ArrayList<BienResarcimientoDTO>();
        bienResarcimientoDataModel = new BienResarcimientoDataModel(bienResarcimientoDTOList);
        filtroBandejaTareaDTO = new FiltroBandejaTareaDTO();
        filtroBandejaTareaDTO.setFechaFin(new Date());
        filtroBandejaTareaDTO.setFechaInicio(new Date());
        filtroBandejaTareaDTO.setNumeroAsunto("RRL2014000598");
        CatalogoDTO estadoProcesal = new CatalogoDTO();
        estadoProcesal.setClave("TEST");
        estadoProcesal.setDescripcion("TEST");
        filtroBandejaTareaDTO.setEstadoProcesal(estadoProcesal);
        listaResolucion.addAll(getListaResolucion("CONSEN"));
        listaUsuarios.addAll(getListaAutorizadores(filtroBandejaTareaDTO.getNumeroAsunto()));
        mediosDefensa.addAll(getListMediosDefensa());
        mediosTransporte.addAll(getMediosDeTransporte());
        bienResarcimientoDTOList.addAll(getBienesResarcimiento());
    }

    /**
     * 
     * @return
     */
    private List<BienResarcimientoDTO> getBienesResarcimiento() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 
     * @return
     */
    private List<CatalogoDTO> getListMediosDefensa() {
        return generacionResolucionBussines.obtenerMediosDeDefensa();
    }

    /**
     * 
     * @return
     */
    private List<CatalogoDTO> getMediosDeTransporte() {
        return generacionResolucionBussines.obtenerMediosDeTransporte();
    }

    /**
     * Method to execute the following action into juridica resolucion
     * flow, first validate required data. if required field are
     * missing shows error otherwise sends the next step into flow.
     * 
     * @return String Action to perform.
     */
    public String siguienteAction() {
        String action = "";
        if (getListaResolucion().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                            .getString("vuj.resol.adjuntar.documentos")));
        }

        if (!getGenerarResolucionValidator().validateDatosResolucion(null, false)) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                            .getString("vuj.resol.adjuntar.documentos")));
            action = "";
        }

        return action;
    }

    /**
     * Method to get all available Autorizadores from data base. needs
     * the numero de asunto to perform the search.
     * 
     * @param string
     *            numeroAsunto
     * @return
     */
    private List<CatalogoDTO> getListaAutorizadores(String numeroAsunto) {
        return generacionResolucionBussines.getAutorizadores(numeroAsunto);
    }

    /**
     * Metodo para obtener la lista que llena el combo de resultados
     * filtra por tipo de consulta.
     * 
     * @param string
     * @return
     */
    private List<CatalogoDTO> getListaResolucion(String type) {
        return generacionResolucionBussines.obtenerResolucionCatalog(type);
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
     * @return the filtroBandejaTareaDTO
     */
    public FiltroBandejaTareaDTO getFiltroBandejaTareaDTO() {
        return filtroBandejaTareaDTO;
    }

    /**
     * @param filtroBandejaTareaDTO
     *            the filtroBandejaTareaDTO to set
     */
    public void setFiltroBandejaTareaDTO(FiltroBandejaTareaDTO filtroBandejaTareaDTO) {
        this.filtroBandejaTareaDTO = filtroBandejaTareaDTO;
    }

    /**
     * @return the modalidadType
     */
    public Integer getModalidadType() {
        return modalidadType;
    }

    /**
     * @param modalidadType
     *            the modalidadType to set
     */
    public void setModalidadType(Integer modalidadType) {
        this.modalidadType = modalidadType;
    }

    /**
     * @return the listaResolucion
     */
    public List<CatalogoDTO> getListaResolucion() {
        return listaResolucion;
    }

    /**
     * @param listaResolucion
     *            the listaResolucion to set
     */
    public void setListaResolucion(List<CatalogoDTO> listaResolucion) {
        this.listaResolucion = listaResolucion;
    }

    /**
     * @return the listaUsuarios
     */
    public List<CatalogoDTO> getListaUsuarios() {
        return listaUsuarios;
    }

    /**
     * @param listaUsuarios
     *            the listaUsuarios to set
     */
    public void setListaUsuarios(List<CatalogoDTO> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    /**
     * @return the generacionResolucionBussines
     */
    public GeneracionResolucionCALBussines getGeneracionResolucionBussines() {
        return generacionResolucionBussines;
    }

    /**
     * @param generacionResolucionBussines
     *            the generacionResolucionBussines to set
     */
    public void setGeneracionResolucionBussines(GeneracionResolucionCALBussines generacionResolucionBussines) {
        this.generacionResolucionBussines = generacionResolucionBussines;
    }

    /**
     * @return the fechaSesion
     */
    public Date getFechaSesion() {
        return fechaSesion != null ? (Date) fechaSesion.clone() : null;
    }

    /**
     * @param fechaSesion
     *            the fechaSesion to set
     */
    public void setFechaSesion(Date fechaSesion) {
        if (fechaSesion != null) {
            this.fechaSesion = (Date) fechaSesion.clone();
        }
        else {
            this.fechaSesion = null;
        }
    }

    /**
     * @return the fraccion
     */
    public String getFraccion() {
        return fraccion;
    }

    /**
     * @param fraccion
     *            the fraccion to set
     */
    public void setFraccion(String fraccion) {
        this.fraccion = fraccion;
    }

    /**
     * @return the minuta
     */
    public String getMinuta() {
        return minuta;
    }

    /**
     * @param minuta
     *            the minuta to set
     */
    public void setMinuta(String minuta) {
        this.minuta = minuta;
    }

    /**
     * @return the resolucion
     */
    public CatalogoDTO getResolucion() {
        return resolucion;
    }

    /**
     * @param resolucion
     *            the resolucion to set
     */
    public void setResolucion(CatalogoDTO resolucion) {
        this.resolucion = resolucion;
    }

    /**
     * @return the nombreFirma
     */
    public CatalogoDTO getNombreFirma() {
        return nombreFirma;
    }

    /**
     * @param nombreFirma
     *            the nombreFirma to set
     */
    public void setNombreFirma(CatalogoDTO nombreFirma) {
        this.nombreFirma = nombreFirma;
    }

    /**
     * @return the fraccionArancel
     */
    public CatalogoDTO getFraccionArancel() {
        return fraccionArancel;
    }

    /**
     * @param fraccionArancel
     *            the fraccionArancel to set
     */
    public void setFraccionArancel(CatalogoDTO fraccionArancel) {
        this.fraccionArancel = fraccionArancel;
    }

    /**
     * @return the generarResolucionValidator
     */
    public GenerarResolucionValidator getGenerarResolucionValidator() {
        return generarResolucionValidator;
    }

    /**
     * @param generarResolucionValidator
     *            the generarResolucionValidator to set
     */
    public void setGenerarResolucionValidator(GenerarResolucionValidator generarResolucionValidator) {
        this.generarResolucionValidator = generarResolucionValidator;
    }

    /**
     * @return the numeroAsuntoResarcimiento
     */
    public String getNumeroAsuntoResarcimiento() {
        return numeroAsuntoResarcimiento;
    }

    /**
     * @param numeroAsuntoResarcimiento
     *            the numeroAsuntoResarcimiento to set
     */
    public void setNumeroAsuntoResarcimiento(String numeroAsuntoResarcimiento) {
        this.numeroAsuntoResarcimiento = numeroAsuntoResarcimiento;
    }

    /**
     * @return the montoSentencia
     */
    public Integer getMontoSentencia() {
        return montoSentencia;
    }

    /**
     * @param montoSentencia
     *            the montoSentencia to set
     */
    public void setMontoSentencia(Integer montoSentencia) {
        this.montoSentencia = montoSentencia;
    }

    /**
     * @return the medioDefensa
     */
    public String getMedioDefensa() {
        return medioDefensa;
    }

    /**
     * @param medioDefensa
     *            the medioDefensa to set
     */
    public void setMedioDefensa(String medioDefensa) {
        this.medioDefensa = medioDefensa;
    }

    /**
     * @return the autoridadEmbarga
     */
    public String getAutoridadEmbarga() {
        return autoridadEmbarga;
    }

    /**
     * @param autoridadEmbarga
     *            the autoridadEmbarga to set
     */
    public void setAutoridadEmbarga(String autoridadEmbarga) {
        this.autoridadEmbarga = autoridadEmbarga;
    }

    /**
     * @return the cumplimientoSentencia
     */
    public String getCumplimientoSentencia() {
        return cumplimientoSentencia;
    }

    /**
     * @param cumplimientoSentencia
     *            the cumplimientoSentencia to set
     */
    public void setCumplimientoSentencia(String cumplimientoSentencia) {
        this.cumplimientoSentencia = cumplimientoSentencia;
    }

    /**
     * @return the montoTotalAutorizado
     */
    public Integer getMontoTotalAutorizado() {
        return montoTotalAutorizado;
    }

    /**
     * @param montoTotalAutorizado
     *            the montoTotalAutorizado to set
     */
    public void setMontoTotalAutorizado(Integer montoTotalAutorizado) {
        this.montoTotalAutorizado = montoTotalAutorizado;
    }

    /**
     * @return the fechaEmbargo
     */
    public Date getFechaEmbargo() {
        return fechaEmbargo != null ? (Date) fechaEmbargo.clone() : null;
    }

    /**
     * @param fechaEmbargo
     *            the fechaEmbargo to set
     */
    public void setFechaEmbargo(Date fechaEmbargo) {
        if (fechaEmbargo != null) {
            this.fechaEmbargo = (Date) fechaEmbargo.clone();
        }
        else {
            this.fechaEmbargo = null;
        }
    }

    /**
     * @return the valorBien
     */
    public Integer getValorBien() {
        return valorBien;
    }

    /**
     * @param valorBien
     *            the valorBien to set
     */
    public void setValorBien(Integer valorBien) {
        this.valorBien = valorBien;
    }

    /**
     * @return the mediosDefensa
     */
    public List<CatalogoDTO> getMediosDefensa() {
        return mediosDefensa;
    }

    /**
     * @param mediosDefensa
     *            the mediosDefensa to set
     */
    public void setMediosDefensa(List<CatalogoDTO> mediosDefensa) {
        this.mediosDefensa = mediosDefensa;
    }

    /**
     * @return the mediosTransporte
     */
    public List<CatalogoDTO> getMediosTransporte() {
        return mediosTransporte;
    }

    /**
     * @param mediosTransporte
     *            the mediosTransporte to set
     */
    public void setMediosTransporte(List<CatalogoDTO> mediosTransporte) {
        this.mediosTransporte = mediosTransporte;
    }

    /**
     * @return the bienResarcimientoDataModel
     */
    public BienResarcimientoDataModel getBienResarcimientoDataModel() {
        return bienResarcimientoDataModel;
    }

    /**
     * @param bienResarcimientoDataModel
     *            the bienResarcimientoDataModel to set
     */
    public void setBienResarcimientoDataModel(BienResarcimientoDataModel bienResarcimientoDataModel) {
        this.bienResarcimientoDataModel = bienResarcimientoDataModel;
    }

    /**
     * @return the selectedResarcimiento
     */
    public BienResarcimientoDTO getSelectedBienResarcimiento() {
        return selectedBienResarcimiento;
    }

    /**
     * @param selectedResarcimiento
     *            the selectedResarcimiento to set
     */
    public void setSelectedBienResarcimiento(BienResarcimientoDTO selectedResarcimiento) {
        this.selectedBienResarcimiento = selectedResarcimiento;
    }

    /**
     * @return the medioTransporte
     */
    public String getMedioTransporte() {
        return medioTransporte;
    }

    /**
     * @param medioTransporte
     *            the medioTransporte to set
     */
    public void setMedioTransporte(String medioTransporte) {
        this.medioTransporte = medioTransporte;
    }

    /**
     * @return the descripcionBien
     */
    public String getDescripcionBien() {
        return descripcionBien;
    }

    /**
     * @param descripcionBien
     *            the descripcionBien to set
     */
    public void setDescripcionBien(String descripcionBien) {
        this.descripcionBien = descripcionBien;
    }

    /**
     * @return the bienResarcimientoDTOList
     */
    public List<BienResarcimientoDTO> getBienResarcimientoDTOList() {
        return bienResarcimientoDTOList;
    }

    /**
     * @param bienResarcimientoDTOList
     *            the bienResarcimientoDTOList to set
     */
    public void setBienResarcimientoDTOList(List<BienResarcimientoDTO> bienResarcimientoDTOList) {
        this.bienResarcimientoDTOList = bienResarcimientoDTOList;
    }

    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * @param clave
     *            the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * @return the firmante
     */
    public String getFirmante() {
        return firmante;
    }

    /**
     * @param firmante
     *            the firmante to set
     */
    public void setFirmante(String firmante) {
        this.firmante = firmante;
    }

    /**
     * @return the montoCalculado
     */
    public Integer getMontoCalculado() {
        return montoCalculado;
    }

    /**
     * @param montoCalculado
     *            the montoCalculado to set
     */
    public void setMontoCalculado(Integer montoCalculado) {
        this.montoCalculado = montoCalculado;
    }

}
