/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.AccionesBitacoraConstants;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.BitacoraDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.FiltroBitacoraAGDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.business.ConsultarBitacoraBusiness;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import java.text.MessageFormat;
import java.util.*;

/**
 * Clase Controller para la consulta de la Bitacora
 * 
 * @author Softtek - EQG
 * @since 30/09/2014
 */
@ViewScoped
@ManagedBean(name = "bitacoraController")
public class BitacoraController extends AdministracionUsuariosBaseController {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -7432544485566399001L;

    /**
     * DTO tipo FiltroBitacoraAGDTO
     */
    private FiltroBitacoraAGDTO filtroBitacora;

    /**
     * DTO tipo BitacoraDTO
     */
    private List<BitacoraDTO> listaBitacora;

    /**
     * Lista tipo UnidadAdministrativa
     */
    private List<CatalogoDTO> listaUnidadesAdministrativas;

    /**
     * CatalogoDTO
     */
    private List<CatalogoDTO> tipoSeleccionRealiza;

    /**
     * CatalogoDTO
     */
    private List<CatalogoDTO> tipoSeleccionAplica;

    /**
     * Propiedades que representan un mensaje.
     */
    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;
    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;

    /**
     * Mensaje para la bandeja vacia tipo String
     */
    private String mensajeBandejaVacia;

    /**
     * Atributos para mensajes de validacion de las fechas
     */
    private String mensageFechaFin;
    private Date minFechaFin;
    private String mensageFechas;

    /**
     * Variables tipo Boolean para habilitar/deshabilitar campos
     */
    private boolean activaCampos;
    private boolean activaCampos2;
    private boolean requerido;

    public boolean isRequerido() {
        return requerido;
    }

    public void setRequerido(boolean requerido) {
        this.requerido = requerido;
    }

    private int tamanioPanel;

    @ManagedProperty(value = "#{consultarBitacoraBusiness}")
    private ConsultarBitacoraBusiness consultarBitacoraBusiness;

    /**
     * Metodo que incializa datos principales
     */
    @Override
    public void init() {
        super.init();
        inicializaDatosComunes();
        listaBitacora = new ArrayList<BitacoraDTO>();
        setTipoSeleccionRealiza(getConsultarBitacoraBusiness().obtenerComboSeleccion());
        setTipoSeleccionAplica(getConsultarBitacoraBusiness().obtenerComboSeleccionAplica());
        filtroBitacora.setIdUniAdmin(getAdminUsuariosDTO().getIdUnidadAmin());
    }

    /**
     * Metodo para limpiar el filtro de busqueda.
     * 
     * @param actionEvent
     */
    public void limpiarBandeja(ActionEvent actionEvent) {
        inicializaDatosComunes();
        listaBitacora.clear();
        setMensajeBandejaVacia("");
    }

    /**
     * Metodo que inicializa datos comunes del metodo "init" y del
     * metodo "limpiarBandeja"
     */
    public void inicializaDatosComunes() {
        filtroBitacora = new FiltroBitacoraAGDTO();
        listaUnidadesAdministrativas();
        setActivaCampos(false);
        setActivaCampos2(false);
        setRequerido(true);
        setTamanioPanel(NumerosConstantes.TREINTA_TRES);
        if (getAdminUsuariosDTO().getIdUnidadAmin() != null) {
            this.getAdminUsuariosDTO().setBlnIsAdminGlobal(false);
        }
        else {
            this.getAdminUsuariosDTO().setBlnIsAdminGlobal(true);
        }
    }

    /**
     * Metodo que obtiene la lista de unidades administrativas de
     * pendiendo del rol del Administrador
     */
    public void listaUnidadesAdministrativas() {
        if (this.getAdminUsuariosDTO().isBlnIsAdminGlobal()) {
            setListaUnidadesAdministrativas(getConsultarBitacoraBusiness().obtenerUnidadesAdministrativas());
        }
        else {
            setListaUnidadesAdministrativas(getConsultarBitacoraBusiness().obtenerUnidadAdministrativa(
                    getAdminUsuariosDTO().getIdUnidadAmin()));
            this.getAdminUsuariosDTO().setUnidadAdministrativa(
                    getListaUnidadesAdministrativas().get(0).getDescripcion());
            getFiltroBitacora().setIdUniAdmin(getListaUnidadesAdministrativas().get(0).getClave());
        }
    }

    /**
     * @param datosEmpleado
     *            the datosEmpleado to set
     */
    public void back() {
        getFlash().put(VistaConstantes.DATOS_ADMINUSUARIO_DTO, getAdminUsuariosDTO());
        super.back();
    }

    /**
     * Metodo para la consulta de datos de la bitacora
     */
    public void consultarBitacora() {
        if (getFiltroBitacora().getFechaFinal().after(calcularFechaMaxima())) {
            String[] param = new String[1];
            param[0] = getConsultarBitacoraBusiness().obtenerRangoDeDias();

            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", MessageFormat.format(
                            messages.getString("administracion.usuarios.bitacora.rangoMaxFecha"), (Object[]) param)));
        }
        else {
            setListaBitacora(getConsultarBitacoraBusiness().obtenerDatosBitacora(getFiltroBitacora()));
            if (getListaBitacora() == null || getListaBitacora().isEmpty()) {
                setMensajeBandejaVacia(messages.getString("vuj.grid.busquedaVacia"));
            }
            if (getListaBitacora().size() <= NumerosConstantes.CINCO) {
                setTamanioPanel(NumerosConstantes.CIENTO_CINCUENTA);
            }
            else {
                setTamanioPanel(NumerosConstantes.TRESCIENTOS);
            }
        }
    }

    /**
     * Metodo para validar el rango de fechas
     */
    public void rangoMaxFechas() {
        Date minFechFin = new Date();
        minFechFin.setTime(filtroBitacora.getFechaInicial().getTime());
        setMinFechaFin(minFechFin);
    }

    /**
     * Metodo que calcula que el periodo entre las fechas sea el
     * correcto
     * 
     * @return fecha
     */
    public Date calcularFechaMaxima() {
        Calendar fecha = Calendar.getInstance();
        fecha.setTime(filtroBitacora.getFechaInicial());
        fecha.add(Calendar.MONTH, 1);
        return fecha.getTime();
    }

    public FiltroBitacoraAGDTO getFiltroBitacora() {
        return filtroBitacora;
    }

    public void setFiltroBitacora(FiltroBitacoraAGDTO filtroBitacora) {
        this.filtroBitacora = filtroBitacora;
    }

    public List<BitacoraDTO> getListaBitacora() {
        return listaBitacora;
    }

    public void setListaBitacora(List<BitacoraDTO> listaBitacora) {
        this.listaBitacora = listaBitacora;
    }

    public String getMensajeBandejaVacia() {
        return mensajeBandejaVacia;
    }

    public void setMensajeBandejaVacia(String mensajeBandejaVacia) {
        this.mensajeBandejaVacia = mensajeBandejaVacia;
    }

    public ResourceBundle getMessages() {
        return messages;
    }

    public void setMessages(ResourceBundle messages) {
        this.messages = messages;
    }

    public Date getMaxDate() {
        return new Date();
    }

    public ConsultarBitacoraBusiness getConsultarBitacoraBusiness() {
        return consultarBitacoraBusiness;
    }

    public void setConsultarBitacoraBusiness(ConsultarBitacoraBusiness consultarBitacoraBusiness) {
        this.consultarBitacoraBusiness = consultarBitacoraBusiness;
    }

    public List<CatalogoDTO> getListaUnidadesAdministrativas() {
        return listaUnidadesAdministrativas;
    }

    public void setListaUnidadesAdministrativas(List<CatalogoDTO> listaUnidadesAdministrativas) {
        this.listaUnidadesAdministrativas = listaUnidadesAdministrativas;
    }

    public String getMensageFechaFin() {
        mensageFechaFin = messages.getString("administracion.fechas.bitacora.validacion");
        return mensageFechaFin;
    }

    public String getMensageFechas() {
        mensageFechas = errorMsg.getString("validation.fechas");
        return mensageFechas;

    }

    public ResourceBundle getErrorMsg() {
        return errorMsg;
    }

    public void handleChange(ValueChangeEvent event) {
        if (AccionesBitacoraConstants.ACCION_REALIZA_EMPLEADO.equals(event.getNewValue())) {
            activaCampos = true;
            requerido = true;
        }
        else if (null == event.getNewValue()) {
            requerido = true;
        }
        else {
            activaCampos = false;
            requerido = false;
        }
    }

    public void handleChange2(ValueChangeEvent event) {
        if ((AccionesBitacoraConstants.ACCION_REALIZA_EMPLEADO_APLICA).equals(event.getNewValue())) {
            activaCampos2 = true;
        }
        else {
            activaCampos2 = false;
        }
    }

    public void setErrorMsg(ResourceBundle errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Date getMinFechaFin() {
        return (null == minFechaFin ? null : (Date) minFechaFin.clone());
    }

    public void setMinFechaFin(Date minFechaFin) {
        this.minFechaFin = (null == minFechaFin ? null : (Date) minFechaFin.clone());
    }

    public void setMensageFechaFin(String mensageFechaFin) {
        this.mensageFechaFin = mensageFechaFin;
    }

    public void setMensageFechas(String mensageFechas) {
        this.mensageFechas = mensageFechas;
    }

    public boolean isActivaCampos() {
        return activaCampos;
    }

    public void setActivaCampos(boolean activaCampos) {
        this.activaCampos = activaCampos;
    }

    public boolean isActivaCampos2() {
        return activaCampos2;
    }

    public void setActivaCampos2(boolean activaCampos2) {
        this.activaCampos2 = activaCampos2;
    }

    public List<CatalogoDTO> getTipoSeleccionRealiza() {
        return tipoSeleccionRealiza;
    }

    public void setTipoSeleccionRealiza(List<CatalogoDTO> tipoSeleccionRealiza) {
        this.tipoSeleccionRealiza = tipoSeleccionRealiza;
    }

    public List<CatalogoDTO> getTipoSeleccionAplica() {
        return tipoSeleccionAplica;
    }

    public void setTipoSeleccionAplica(List<CatalogoDTO> tipoSeleccionAplica) {
        this.tipoSeleccionAplica = tipoSeleccionAplica;
    }

    public int getTamanioPanel() {
        return tamanioPanel;
    }

    public void setTamanioPanel(int tamanioPanel) {
        this.tamanioPanel = tamanioPanel;
    }

}
