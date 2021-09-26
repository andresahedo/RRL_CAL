package mx.gob.sat.siat.juridica.cal.op.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.constantes.RolesConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoClasificacionArancelaria;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoRol;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.TipoTramiteDTO;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.base.web.controller.bean.controller.AtenderObservacionController;
import mx.gob.sat.siat.juridica.cal.dto.FraccionArancelariaDudaDTO;
import mx.gob.sat.siat.juridica.cal.dto.PersonaInvolucradaDTO;
import mx.gob.sat.siat.juridica.cal.dto.PersonaOirNotificacionesDTO;
import mx.gob.sat.siat.juridica.cal.dto.SolicitudCALDTO;
import mx.gob.sat.siat.juridica.cal.op.web.controller.bean.business.AtenderObservacionCALOPBusiness;
import mx.gob.sat.siat.juridica.cal.web.controller.bean.constantes.ConsultasConstantesController;
import mx.gob.sat.siat.juridica.cal.web.controller.bean.model.FraccionArancelariaDudaDataModel;
import mx.gob.sat.siat.juridica.cal.web.controller.bean.model.PersonaInvolucradaDataModel;
import mx.gob.sat.siat.juridica.cal.web.controller.bean.model.PersonaOirNotificacionesDataModel;
import mx.gob.sat.siat.juridica.rrl.dto.DatosSolicitudDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.UrlFirma;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.DocumentoDataModel;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ViewScoped
@ManagedBean(name = "atenderObservacionCALOPController")
public class AtenderObservacionCALOPController extends AtenderObservacionController<SolicitudCALDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{atenderObservacionCALOPBusiness}")
    private AtenderObservacionCALOPBusiness atenderObservacionCALOPBusiness;

    private SolicitudCALDTO solicitudCALDTO;

    private PersonaOirNotificacionesDTO personaOirNotificacionesDTO = new PersonaOirNotificacionesDTO();

    private PersonaOirNotificacionesDTO personaOirNotificacionesDTOSelect = new PersonaOirNotificacionesDTO();

    private List<PersonaOirNotificacionesDTO> listaPersonasOirNot;

    private PersonaOirNotificacionesDataModel personaOirNotificacionesDataModel;

    private PersonaInvolucradaDTO personaInvolucradaDTO = new PersonaInvolucradaDTO();

    private FraccionArancelariaDudaDTO fraccionArancelariaDudaDTO = new FraccionArancelariaDudaDTO();

    private List<FraccionArancelariaDudaDTO> listaFraccionAraDuda;

    private FraccionArancelariaDudaDataModel fraccionArancelariaDudaDataModel;

    private List<PersonaInvolucradaDTO> listaPersonaInvolucrada;

    private PersonaInvolucradaDataModel personaInvolucradaDataModel;

    /** Lista de que representa los valores de gran continuyente */
    private List<CatalogoDTO> listaGranContribuyente = new ArrayList<CatalogoDTO>();
    /** Lista de autoridad */
    private List<CatalogoDTO> listaAutoridad = new ArrayList<CatalogoDTO>();
    /** Lista de medios de defensa */
    private List<CatalogoDTO> listaMediosDefensa = new ArrayList<CatalogoDTO>();
    /** Lista de sentido de resoluci&oacute;n */
    private List<CatalogoDTO> listaSentidosResolucion = new ArrayList<CatalogoDTO>();

    private List<CatalogoDTO> listaTipoPersona = new ArrayList<CatalogoDTO>();
    /**
     * Lista que representa si se encuentra dentro del plazo del
     * art&iacute;culo 50
     */
    private List<CatalogoDTO> listaPlazo = new ArrayList<CatalogoDTO>();
    /**
     * Lista con los tipos de clasificacion arancelaria
     */
    private List<CatalogoDTO> listaTipoClasificacion = new ArrayList<CatalogoDTO>();
    /** Representa la modalidad a procesar */
    private String modalidad;
    /** Bean para obtener los mensajes */
    /** Mensaje entre pantallas */
    private String messagesRedirect;
    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;
    /** Bean para obtener los mensajes de error */
    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;
    /** Lista de documentos opcionales */
    private List<DocumentoDTO> listaDocumentosOpcionales = new ArrayList<DocumentoDTO>();
    /** Lista de documentos requeridos */
    private List<DocumentoDTO> listaDocumentosRequeridos = new ArrayList<DocumentoDTO>();
    /** Data model de documentos opcionales */
    private DocumentoDataModel documentoSeleccionDataModel;
    /** Documentos opcionales seleccionados */
    private DocumentoDTO[] documentosSeleccionados;
    /** Lista de documentos adjuntados */
    private List<DocumentoDTO> listaDocumentos;
    /** Lista de tipos de documentos seleccionados para ser adjuntados */
    private List<DocumentoDTO> documentosCombo = new ArrayList<DocumentoDTO>();;
    /** Data model de documentos adjuntados */
    private DocumentoDataModel documentoAdjuntarDataModel;
    /** Id documento adjuntar */
    private String documentoSelected;
    /** Archivo para descarga */
    private DefaultStreamedContent archivoDescarga;
    /** Arreglo de documentos a eliminar */
    private DocumentoDTO[] registrosEliminar;

    private PersonaOirNotificacionesDTO[] personasONEliminar;

    private PersonaInvolucradaDTO[] personasInvolucradasEliminar;

    private FraccionArancelariaDudaDTO[] fraccionesDudaEliminar;

    private Date maxDate;
    /** DTO que representa lo firma de la solicitud */
    private FirmaDTO firma;

    private String cadenaOriginal;

    private String firmaDigital;

    private boolean tipoPer;

    private boolean tipoPerMor;

    private boolean tipoClasificacionArancelariaCombo;

    private boolean hechosCirc;

    private boolean mediosDefensa;

    private boolean sujetoEjercicio;

    private boolean clasificacionArancelaria;

    private boolean requerido = false;

    private boolean requeridoPrincipal = true;

    private boolean eliminarVisible;

    private boolean eliminarVisiblePerOir;

    private boolean eliminarVisiblePerInv;

    private boolean eliminarVisibleFraccDuda;

    private String mensajeAviso;

    private boolean agregarVisible;

    private DatosSolicitudDTO datosSolicitud;

    private String rowsCorreo;

    @Override
    public void iniciar() {
        super.iniciar();
        cargarCombosCaptura();
        maxDate = new Date();
        listaPersonasOirNot = new ArrayList<PersonaOirNotificacionesDTO>();
        listaPersonaInvolucrada = new ArrayList<PersonaInvolucradaDTO>();
        listaFraccionAraDuda = new ArrayList<FraccionArancelariaDudaDTO>();
        personaOirNotificacionesDataModel = new PersonaOirNotificacionesDataModel(listaPersonasOirNot);
        personaInvolucradaDataModel = new PersonaInvolucradaDataModel(listaPersonaInvolucrada);
        fraccionArancelariaDudaDataModel = new FraccionArancelariaDudaDataModel(listaFraccionAraDuda);
        inicializaTipoPersonaInvolucrada();

        listaPersonasOirNot.addAll(getSolicitud().getListaPersonasNotificaciones());
        listaPersonaInvolucrada.addAll(getSolicitud().getListaPersonasInvolucradas());
        if (getSolicitud().getListaFraccionDuda() != null) {
            listaFraccionAraDuda.addAll(getSolicitud().getListaFraccionDuda());
        }
        getSolicitud().setIdSolicitud(getDatosBandejaTareaDTO().getIdSolicitud());
        validaHechosCircParcial();
        validaMediosDefensaParcial();
        validaSujetoEjercicioParcial();
        validaClasificacionArancelaria();
        TipoTramiteDTO tipoTramite =
                getAtenderObservacionCALOPBusiness().obtenerTipoTramitePorId(getSolicitud().getTipoTramite());
        if (tipoTramite.getAsignado()) {
            setMensajeAviso(messages.getString("cal.mensaje.aviso"));
        }
        if (tipoTramite.getClaveModulo() != null && tipoTramite.getClaveModulo() == 1) {
            clasificacionArancelaria = true;
        }
        setListaSentidosResolucion(getAtenderObservacionCALOPBusiness().obtenerSentidosResolucion(
                getSolicitud().getClaveMedioDefensa()));
        if (getDatosBandejaTareaDTO().getIdSolicitud() != null) {
            setDatosSolicitud(getAtenderObservacionCALOPBusiness().buscarSolicitante(
                    getDatosBandejaTareaDTO().getIdSolicitud()));
        }
        if (getDatosSolicitud().getRazonSocial() != null) {
            setBanderaRazonSocial(true);
        }
        else {
            setBanderaRazonSocial(false);
        }
        if (getDatosSolicitud().getCorreoElectronico() != null) {
            String[] correos = getDatosSolicitud().getCorreoElectronico().split("\n");
            setRowsCorreo(String.valueOf(correos.length));
            if (getRowsCorreo().equals("1") || getRowsCorreo().equals("2")) {
                setRowsCorreo("3");
            }
        }

    }

    @Override
    protected SolicitudCALDTO obtenerSolicitud() {
        return getAtenderObservacionCALOPBusiness().obtenerSolicitud(getDatosBandejaTareaDTO().getIdSolicitud());
    }

    @Override
    public void guardar() throws SolicitudNoGuardadaException {
        getSolicitud().setListaPersonasInvolucradas(listaPersonaInvolucrada);
        getSolicitud().setListaPersonasNotificaciones(listaPersonasOirNot);
        getSolicitud().setListaFraccionDuda(listaFraccionAraDuda);
        getSolicitud().setCveRolCapturista(TipoRol.OFICIAL_PARTES.getClave());
        setSolicitud(getAtenderObservacionCALOPBusiness().guardarSolicitud(getSolicitud(), getUserProfile().getRfc()));

        String guardado = messages.getString("cal.captura.solicitud.promocion.guardadoSolicitud");

        String[] param = new String[1];
        param[0] = getSolicitud().getIdSolicitud().toString();

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "", MessageFormat.format(guardado, (Object[]) param)));
    }

    /**
     * Metodo que carga valores en los combos.
     */
    private void cargarCombosCaptura() {
        setListaGranContribuyente(getAtenderObservacionCALOPBusiness().obtenerListaSiNo());
        setListaMediosDefensa(getAtenderObservacionCALOPBusiness().obtenerMediosDeDefensa());
        setListaAutoridad(getAtenderObservacionCALOPBusiness().obtenerAutoridadesEmisoras());
        setListaPlazo(getAtenderObservacionCALOPBusiness().obtenerListaSiNo());
        setListaTipoPersona(getAtenderObservacionCALOPBusiness().obtenerListaTipoPersona());
        setListaTipoClasificacion(getAtenderObservacionCALOPBusiness().obtenerListaTipoClasificacion());
    }

    public void cargaSentidosResol() {
        setListaSentidosResolucion(getAtenderObservacionCALOPBusiness().obtenerSentidosResolucion(
                getSolicitud().getClaveMedioDefensa()));
    }

    @Override
    public SolicitudCALDTO getSolicitud() {
        return solicitudCALDTO;
    }

    @Override
    public void setSolicitud(SolicitudCALDTO solicitud) {
        this.solicitudCALDTO = solicitud;
    }

    @Override
    public String getDireccionFirma() {
        return UrlFirma.PAGINA_FIRMA_OBSERVACION_CAL.toString();
    }

    private void validaHechosCircParcial() {
        hechosCirc = false;
        if (getSolicitud().getHechosCircunstanciasPrevPlanteadas() != null
                && getSolicitud().getHechosCircunstanciasPrevPlanteadas().equals("1")) {
            hechosCirc = true;
        }
    }

    private void validaMediosDefensaParcial() {
        mediosDefensa = false;
        if (getSolicitud().getHechosCircunstanciasMatMedios() != null
                && getSolicitud().getHechosCircunstanciasMatMedios().equals("1")) {
            mediosDefensa = true;
        }
    }

    private void validaSujetoEjercicioParcial() {
        setSujetoEjercicio(false);
        if (getSolicitud().getContribuyenteSujetoEjercicio() != null
                && getSolicitud().getContribuyenteSujetoEjercicio().equals("1")) {
            setSujetoEjercicio(true);
        }

    }

    public void agregarPersonaOirNotificacion(ActionEvent event) {
        if (listaPersonasOirNot.size() < NumerosConstantes.CINCO) {
            if (validaPersonaOirNotificacion()) {
                if (validaRFC()) {
                    Date fechaActual = new Date();
                    PersonaOirNotificacionesDTO personaOirNotifDTO = new PersonaOirNotificacionesDTO();
                    personaOirNotifDTO.setIdPersona(fechaActual.getTime());
                    personaOirNotifDTO.setNombre(getPersonaOirNotificacionesDTO().getNombre());
                    personaOirNotifDTO.setPaterno(getPersonaOirNotificacionesDTO().getPaterno());
                    personaOirNotifDTO.setMaterno(getPersonaOirNotificacionesDTO().getMaterno());
                    personaOirNotifDTO.setRfc(getPersonaOirNotificacionesDTO().getRfc());
                    personaOirNotifDTO.setTelefono(getPersonaOirNotificacionesDTO().getTelefono());
                    personaOirNotifDTO.setNuevo(true);
                    personaOirNotifDTO.setDireccion(getPersonaOirNotificacionesDTO().getDireccion() != null
                            ? getPersonaOirNotificacionesDTO().getDireccion() : null);
                    listaPersonasOirNot.add(personaOirNotifDTO);
                    setPersonaOirNotificacionesDTO(new PersonaOirNotificacionesDTO());
                }
                else {
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                                    .getString("ca.solicitud.promocion.RFCFormato")));
                }
            }
            else {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                                .getString("ca.solicitud.promocion.datosPersonaIncompletos")));
            }
        }
        else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                            .getString("ca.solicitud.promocion.maxPersonasOirNot")));
        }
    }

    /**
     * M&eacute;todo para validar el formato del RFC al agregar una
     * persona involucrada
     * 
     * @return boolean true si el formato es correcto, false si no
     *         cumple con el formato
     * @author antonio.flores Softtek
     * @since 24/04/2014
     */
    private boolean validaRFC() {
        boolean isFormatOk = false;
        // Persona fisica "[A-Z]{4}[0-9]{6}[A-Z0-9]{3}"
        Pattern pat = Pattern.compile("^[a-zA-Z]{3,4}[0-9]{6}[A-Z0-9]{3}$");
        Matcher mat = pat.matcher(getPersonaOirNotificacionesDTO().getRfc());
        if (mat.matches()) {
            isFormatOk = true;
        }
        return isFormatOk;
    }

    public boolean validaPersonaOirNotificacion() {
        boolean bNom = false;
        boolean bPaterno = false;
        boolean bMaterno = false;
        boolean nomCompleto = false;
        boolean bRfc = false;
        boolean bTelefono = false;
        boolean valido = false;
        if (getPersonaOirNotificacionesDTO().getNombre() != null
                && !getPersonaOirNotificacionesDTO().getNombre().isEmpty()) {
            bNom = true;
        }
        if (getPersonaOirNotificacionesDTO().getPaterno() != null
                && !getPersonaOirNotificacionesDTO().getPaterno().isEmpty()) {
            bPaterno = true;
        }
        if (getPersonaOirNotificacionesDTO().getMaterno() != null
                && !getPersonaOirNotificacionesDTO().getMaterno().isEmpty()) {
            bMaterno = true;
        }
        if (bNom && bPaterno && bMaterno) {
            nomCompleto = true;
        }
        if (getPersonaOirNotificacionesDTO().getRfc() != null && !getPersonaOirNotificacionesDTO().getRfc().isEmpty()) {
            bRfc = true;
        }
        if (getPersonaOirNotificacionesDTO().getTelefono() != null
                && !getPersonaOirNotificacionesDTO().getTelefono().isEmpty()) {
            bTelefono = true;
        }
        if (nomCompleto && bRfc && bTelefono) {
            valido = true;
        }
        return valido;
    }

    private void validaClasificacionArancelaria() {
        setClasificacionArancelaria(false);
        setTipoClasificacionArancelariaCombo(false);
        if (getSolicitud().getTipoClasificacion() != null) {
            setClasificacionArancelaria(true);
            if (getSolicitud().getTipoClasificacion()
                    .equals(TipoClasificacionArancelaria.CLASIFICACION_CLAS.getClave())) {
                setTipoClasificacionArancelariaCombo(true);
            }
        }
    }

    public void eliminarPersonaOirNotificacion(ActionEvent event) {
        for (PersonaOirNotificacionesDTO personaON : getPersonasONEliminar()) {
            getListaPersonasOirNot().remove(personaON);
            if (!personaON.isNuevo()) {
                getAtenderObservacionCALOPBusiness().eliminaPersona(personaON.getIdPersona());
            }
        }
        setEliminarVisiblePerOir(false);
    }

    public void validaPersona() {
        tipoPerMor = false;
        tipoPer = false;
        if (getPersonaInvolucradaDTO().getTipoPersona() != null) {
            if (getPersonaInvolucradaDTO().getTipoPersona().equals("1")) {
                                                                           // Persona
                                                                           // moral
                tipoPerMor = true;
                tipoPer = false;
            }
            else { 
                  // Persona fisica
                tipoPerMor = false;
                tipoPer = true;
            }
        }

    }

    public void agregarPersonaInvolucrada(ActionEvent event) {
        // La validacion de campo requerido para "Tipo Persona" se
        // hace aqui en vez
        // de en la JSF, ya que de hacerse requerido en la JSF, cuando
        // el combo
        // cambia a "Seleccione" (sin valor), al ser un campo
        // requerido, no llama
        // el evento asociado validaPersona() para mostrar u ocultar
        // campos de captura
        if (listaPersonaInvolucrada.size() < NumerosConstantes.CINCO) {
            if (validaPersonaInvolucrada()) {
                if (getPersonaInvolucradaDTO().getTipoPersona() == null
                        || getPersonaInvolucradaDTO().getTipoPersona().isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMsg
                                    .getString("ca.solicitud.promocion.required.tipoPersona"), ""));
                }
                else {
                    PersonaInvolucradaDTO persInvolucradaDTO = new PersonaInvolucradaDTO();
                    persInvolucradaDTO.setIdPersona(new Date().getTime());
                    persInvolucradaDTO.setNombre(getPersonaInvolucradaDTO().getNombre());
                    persInvolucradaDTO.setPaterno(getPersonaInvolucradaDTO().getPaterno());
                    persInvolucradaDTO.setMaterno(getPersonaInvolucradaDTO().getMaterno());
                    persInvolucradaDTO.setRfc(getPersonaInvolucradaDTO().getRfc());
                    persInvolucradaDTO.setRazonSocial(getPersonaInvolucradaDTO().getRazonSocial());
                    persInvolucradaDTO.setDireccion(getPersonaInvolucradaDTO().getDireccion());
                    persInvolucradaDTO.setTipoPersona(getPersonaInvolucradaDTO().getTipoPersona());
                    persInvolucradaDTO.setNuevo(true);

                    listaPersonaInvolucrada.add(persInvolucradaDTO);
                    PersonaInvolucradaDTO nuevaPersona = new PersonaInvolucradaDTO();
                    setPersonaInvolucradaDTO(nuevaPersona);
                    inicializaTipoPersonaInvolucrada();
                }
            }
            else {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                                .getString("ca.solicitud.promocion.datosPersonaIncompletos")));
            }
        }
        else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                            .getString("ca.solicitud.promocion.maxPersonasInv")));
        }
    }

    public void validaTipoClasificacion() {
        if (getSolicitud().getTipoClasificacion().equals(TipoClasificacionArancelaria.CLASIFICACION_CLAS.getClave())) {
            tipoClasificacionArancelariaCombo = true;
        }
        else {
            tipoClasificacionArancelariaCombo = false;
            // Limpia el grid de fracciones en las que existe duda
            listaFraccionAraDuda = new ArrayList<FraccionArancelariaDudaDTO>();
            fraccionArancelariaDudaDataModel = new FraccionArancelariaDudaDataModel(listaFraccionAraDuda);
        }
    }

    public void validaHechosCirc() {
        if (getSolicitud().getHechosCircunstanciasPrevPlanteadas().equals("1")) {
            hechosCirc = true;
        }
        else {
            hechosCirc = false;
        }
    }

    public void validaMediosDefensa() {
        if (getSolicitud().getHechosCircunstanciasMatMedios().equals("1")) {
            mediosDefensa = true;
        }
        else {
            mediosDefensa = false;
        }
    }

    public void validaSujetoEjercicio() {
        if (getSolicitud().getContribuyenteSujetoEjercicio().equals("1")) {
            sujetoEjercicio = true;
        }
        else {
            sujetoEjercicio = false;
        }
    }

    public boolean validaPersonaInvolucrada() {
        boolean personaValida = false;
        boolean nombre = false;
        boolean paterno = false;
        if (getPersonaInvolucradaDTO().getNombre() != null && !getPersonaInvolucradaDTO().getNombre().isEmpty()) {
            nombre = true;
        }
        if (getPersonaInvolucradaDTO().getPaterno() != null && !getPersonaInvolucradaDTO().getPaterno().isEmpty()) {
            paterno = true;
        }
        if (getPersonaInvolucradaDTO().getRfc() != null && !getPersonaInvolucradaDTO().getRfc().isEmpty()
                && getPersonaInvolucradaDTO().getDireccion() != null
                && !getPersonaInvolucradaDTO().getDireccion().isEmpty()) {
            if (getPersonaInvolucradaDTO().getTipoPersona().equals("0") && nombre && paterno) {
                personaValida = true;
            }
            else if (getPersonaInvolucradaDTO().getRazonSocial() != null
                    && !getPersonaInvolucradaDTO().getRazonSocial().isEmpty()) {
                personaValida = true;
            }
        }
        return personaValida;
    }

    public void eliminarPersonaInvolucrada(ActionEvent event) {
        for (PersonaInvolucradaDTO personaInv : getPersonasInvolucradasEliminar()) {
            getListaPersonaInvolucrada().remove(personaInv);
            if (!personaInv.isNuevo()) {
                getAtenderObservacionCALOPBusiness().eliminaPersona(personaInv.getIdPersona());
            }
        }
        setEliminarVisiblePerInv(false);
    }

    public void agregarFraccionDuda(ActionEvent event) {
        if (getListaFraccionAraDuda() != null && getListaFraccionAraDuda().size() < NumerosConstantes.TRES) {
            FraccionArancelariaDudaDTO fraccionDuda = new FraccionArancelariaDudaDTO();
            fraccionDuda.setIdFraccionArancelaria(new Date().getTime()); 
                                                                         // para
                                                                         // tomarse
                                                                         // como
                                                                         // rowkey,
                                                                         // la
                                                                         // fecha
                                                                         // actual
                                                                         // en
                                                                         // milisegundos
            fraccionDuda.setFraccionArancelaria(getFraccionArancelariaDudaDTO().getFraccionArancelaria());
            fraccionDuda.setNuevo(true);
            listaFraccionAraDuda.add(fraccionDuda);
        }
        else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMsg
                            .getString("ca.solicitud.promocion.maxFraccionesDuda")));
        }
        setFraccionArancelariaDudaDTO(new FraccionArancelariaDudaDTO());
    }

    /**
     * Metodo que devuelve los datos del solicitante.
     * 
     * @return consultaDatosSolicitante
     */
    public String getTabSolicitante() {
        return ConsultasConstantesController.CONSULTA_DATOS_SOLICITANTE_OP;
    }

    public void eliminarFraccionDuda(ActionEvent event) {
        for (FraccionArancelariaDudaDTO fraccionDuda : getFraccionesDudaEliminar()) {
            getListaFraccionAraDuda().remove(fraccionDuda);
            if (!fraccionDuda.isNuevo()) {
                getAtenderObservacionCALOPBusiness().eliminaFraccion(fraccionDuda.getIdFraccionArancelaria());
            }
        }
        setEliminarVisibleFraccDuda(false);
    }

    private void inicializaTipoPersonaInvolucrada() {
        tipoPer = true; 
        // Persona fisica, por default
        tipoPerMor = false;
        getPersonaInvolucradaDTO().setTipoPersona("0");
                                                        // Persona
                                                        // fisica
    }

    public void rowSelectCheckboxPerInv(SelectEvent event) {
        setEliminarVisiblePerInv(true);
    }

    public void rowUnselectCheckboxPerInv(UnselectEvent event) {
        if (getPersonasInvolucradasEliminar().length < 1) {
            setEliminarVisiblePerInv(false);
        }
    }

    public void rowSelectCheckbox(SelectEvent event) {
        setEliminarVisiblePerOir(true);
    }

    public void rowUnselectCheckbox(UnselectEvent event) {
        if (getPersonasONEliminar().length < 1) {
            setEliminarVisiblePerOir(false);
        }
    }

    public void cambiaRequerido() {
        requerido = true;
    }

    public void cambiaNoReq() {
        requerido = false;
    }

    public void cambiaNoReqPrincipal() {
        requeridoPrincipal = false;
    }

    public void cambiaRequeridoPrincipal() {
        requeridoPrincipal = true;
    }

    /**
     * @return the atenderObservacionCALOPBusiness
     */
    public AtenderObservacionCALOPBusiness getAtenderObservacionCALOPBusiness() {
        return atenderObservacionCALOPBusiness;
    }

    /**
     * @param atenderObservacionCALOPBusiness
     *            the atenderObservacionCALOPBusiness to set
     */
    public void setAtenderObservacionCALOPBusiness(AtenderObservacionCALOPBusiness atenderObservacionCALOPBusiness) {
        this.atenderObservacionCALOPBusiness = atenderObservacionCALOPBusiness;
    }

    public PersonaOirNotificacionesDTO getPersonaOirNotificacionesDTO() {
        return personaOirNotificacionesDTO;
    }

    public void setPersonaOirNotificacionesDTO(PersonaOirNotificacionesDTO personaOirNotificacionesDTO) {
        this.personaOirNotificacionesDTO = personaOirNotificacionesDTO;
    }

    public PersonaOirNotificacionesDTO getPersonaOirNotificacionesDTOSelect() {
        return personaOirNotificacionesDTOSelect;
    }

    public void setPersonaOirNotificacionesDTOSelect(PersonaOirNotificacionesDTO personaOirNotificacionesDTOSelect) {
        this.personaOirNotificacionesDTOSelect = personaOirNotificacionesDTOSelect;
    }

    public List<PersonaOirNotificacionesDTO> getListaPersonasOirNot() {
        return listaPersonasOirNot;
    }

    public void setListaPersonasOirNot(List<PersonaOirNotificacionesDTO> listaPersonasOirNot) {
        this.listaPersonasOirNot = listaPersonasOirNot;
    }

    public PersonaOirNotificacionesDataModel getPersonaOirNotificacionesDataModel() {
        return personaOirNotificacionesDataModel;
    }

    public void
            setPersonaOirNotificacionesDataModel(PersonaOirNotificacionesDataModel personaOirNotificacionesDataModel) {
        this.personaOirNotificacionesDataModel = personaOirNotificacionesDataModel;
    }

    public PersonaInvolucradaDTO getPersonaInvolucradaDTO() {
        return personaInvolucradaDTO;
    }

    public void setPersonaInvolucradaDTO(PersonaInvolucradaDTO personaInvolucradaDTO) {
        this.personaInvolucradaDTO = personaInvolucradaDTO;
    }

    public FraccionArancelariaDudaDTO getFraccionArancelariaDudaDTO() {
        return fraccionArancelariaDudaDTO;
    }

    public void setFraccionArancelariaDudaDTO(FraccionArancelariaDudaDTO fraccionArancelariaDudaDTO) {
        this.fraccionArancelariaDudaDTO = fraccionArancelariaDudaDTO;
    }

    public List<FraccionArancelariaDudaDTO> getListaFraccionAraDuda() {
        return listaFraccionAraDuda;
    }

    public void setListaFraccionAraDuda(List<FraccionArancelariaDudaDTO> listaFraccionAraDuda) {
        this.listaFraccionAraDuda = listaFraccionAraDuda;
    }

    public FraccionArancelariaDudaDataModel getFraccionArancelariaDudaDataModel() {
        return fraccionArancelariaDudaDataModel;
    }

    public void setFraccionArancelariaDudaDataModel(FraccionArancelariaDudaDataModel fraccionArancelariaDudaDataModel) {
        this.fraccionArancelariaDudaDataModel = fraccionArancelariaDudaDataModel;
    }

    public List<PersonaInvolucradaDTO> getListaPersonaInvolucrada() {
        return listaPersonaInvolucrada;
    }

    public void setListaPersonaInvolucrada(List<PersonaInvolucradaDTO> listaPersonaInvolucrada) {
        this.listaPersonaInvolucrada = listaPersonaInvolucrada;
    }

    public PersonaInvolucradaDataModel getPersonaInvolucradaDataModel() {
        return personaInvolucradaDataModel;
    }

    public void setPersonaInvolucradaDataModel(PersonaInvolucradaDataModel personaInvolucradaDataModel) {
        this.personaInvolucradaDataModel = personaInvolucradaDataModel;
    }

    public List<CatalogoDTO> getListaGranContribuyente() {
        return listaGranContribuyente;
    }

    public void setListaGranContribuyente(List<CatalogoDTO> listaGranContribuyente) {
        this.listaGranContribuyente = listaGranContribuyente;
    }

    public List<CatalogoDTO> getListaAutoridad() {
        return listaAutoridad;
    }

    public void setListaAutoridad(List<CatalogoDTO> listaAutoridad) {
        this.listaAutoridad = listaAutoridad;
    }

    public List<CatalogoDTO> getListaMediosDefensa() {
        return listaMediosDefensa;
    }

    public void setListaMediosDefensa(List<CatalogoDTO> listaMediosDefensa) {
        this.listaMediosDefensa = listaMediosDefensa;
    }

    public List<CatalogoDTO> getListaSentidosResolucion() {
        return listaSentidosResolucion;
    }

    public void setListaSentidosResolucion(List<CatalogoDTO> listaSentidosResolucion) {
        this.listaSentidosResolucion = listaSentidosResolucion;
    }

    public List<CatalogoDTO> getListaTipoPersona() {
        return listaTipoPersona;
    }

    public void setListaTipoPersona(List<CatalogoDTO> listaTipoPersona) {
        this.listaTipoPersona = listaTipoPersona;
    }

    public List<CatalogoDTO> getListaPlazo() {
        return listaPlazo;
    }

    public void setListaPlazo(List<CatalogoDTO> listaPlazo) {
        this.listaPlazo = listaPlazo;
    }

    public List<CatalogoDTO> getListaTipoClasificacion() {
        return listaTipoClasificacion;
    }

    public void setListaTipoClasificacion(List<CatalogoDTO> listaTipoClasificacion) {
        this.listaTipoClasificacion = listaTipoClasificacion;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getMessagesRedirect() {
        return messagesRedirect;
    }

    public void setMessagesRedirect(String messagesRedirect) {
        this.messagesRedirect = messagesRedirect;
    }

    public ResourceBundle getMessages() {
        return messages;
    }

    public void setMessages(ResourceBundle messages) {
        this.messages = messages;
    }

    public ResourceBundle getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(ResourceBundle errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<DocumentoDTO> getListaDocumentosOpcionales() {
        return listaDocumentosOpcionales;
    }

    public void setListaDocumentosOpcionales(List<DocumentoDTO> listaDocumentosOpcionales) {
        this.listaDocumentosOpcionales = listaDocumentosOpcionales;
    }

    public List<DocumentoDTO> getListaDocumentosRequeridos() {
        return listaDocumentosRequeridos;
    }

    public void setListaDocumentosRequeridos(List<DocumentoDTO> listaDocumentosRequeridos) {
        this.listaDocumentosRequeridos = listaDocumentosRequeridos;
    }

    public DocumentoDataModel getDocumentoSeleccionDataModel() {
        return documentoSeleccionDataModel;
    }

    public void setDocumentoSeleccionDataModel(DocumentoDataModel documentoSeleccionDataModel) {
        this.documentoSeleccionDataModel = documentoSeleccionDataModel;
    }

    public DocumentoDTO[] getDocumentosSeleccionados() {
        return documentosSeleccionados != null ? (DocumentoDTO[]) documentosSeleccionados.clone() : null;
    }

    public void setDocumentosSeleccionados(DocumentoDTO[] docsSeleccionados) {
        if (docsSeleccionados != null) {
            this.documentosSeleccionados = (DocumentoDTO[]) docsSeleccionados.clone();
        }
        else {
            this.documentosSeleccionados = null;
        }
    }

    public List<DocumentoDTO> getListaDocumentos() {
        return listaDocumentos;
    }

    public void setListaDocumentos(List<DocumentoDTO> listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }

    public List<DocumentoDTO> getDocumentosCombo() {
        return documentosCombo;
    }

    public void setDocumentosCombo(List<DocumentoDTO> documentosCombo) {
        this.documentosCombo = documentosCombo;
    }

    public DocumentoDataModel getDocumentoAdjuntarDataModel() {
        return documentoAdjuntarDataModel;
    }

    public void setDocumentoAdjuntarDataModel(DocumentoDataModel documentoAdjuntarDataModel) {
        this.documentoAdjuntarDataModel = documentoAdjuntarDataModel;
    }

    public String getDocumentoSelected() {
        return documentoSelected;
    }

    public void setDocumentoSelected(String documentoSelected) {
        this.documentoSelected = documentoSelected;
    }

    public DefaultStreamedContent getArchivoDescarga() {
        return archivoDescarga;
    }

    public void setArchivoDescarga(DefaultStreamedContent archivoDescarga) {
        this.archivoDescarga = archivoDescarga;
    }

    public DocumentoDTO[] getRegistrosEliminar() {
        return registrosEliminar != null ? (DocumentoDTO[]) registrosEliminar.clone() : null;
    }

    public void setRegistrosEliminar(DocumentoDTO[] regsEliminar) {
        if (regsEliminar != null) {
            this.registrosEliminar = (DocumentoDTO[]) regsEliminar.clone();
        }
        else {
            this.registrosEliminar = null;
        }
    }

    public PersonaOirNotificacionesDTO[] getPersonasONEliminar() {
        return personasONEliminar != null ? (PersonaOirNotificacionesDTO[]) personasONEliminar.clone() : null;
    }

    public void setPersonasONEliminar(PersonaOirNotificacionesDTO[] persONEliminar) {
        if (persONEliminar != null) {
            this.personasONEliminar = persONEliminar.clone();
        }
        else {
            this.personasONEliminar = null;
        }
    }

    public PersonaInvolucradaDTO[] getPersonasInvolucradasEliminar() {
        return personasInvolucradasEliminar != null ? (PersonaInvolucradaDTO[]) personasInvolucradasEliminar.clone()
                : null;
    }

    public void setPersonasInvolucradasEliminar(PersonaInvolucradaDTO[] persInvolEliminar) {
        if (persInvolEliminar != null) {
            this.personasInvolucradasEliminar = persInvolEliminar.clone();
        }
        else {
            this.personasInvolucradasEliminar = null;
        }
    }

    public FraccionArancelariaDudaDTO[] getFraccionesDudaEliminar() {
        return fraccionesDudaEliminar != null ? (FraccionArancelariaDudaDTO[]) fraccionesDudaEliminar.clone() : null;
    }

    public void setFraccionesDudaEliminar(FraccionArancelariaDudaDTO[] fraccionesDudaEliminarArray) {
        if (fraccionesDudaEliminarArray != null) {
            this.fraccionesDudaEliminar = fraccionesDudaEliminarArray.clone();
        }
        else {
            this.fraccionesDudaEliminar = null;
        }
    }

    public void rowSelectCheckboxFraccDuda(SelectEvent event) {
        setEliminarVisibleFraccDuda(true);
    }

    public void rowUnselectCheckboxFraccDuda(UnselectEvent event) {
        if (getFraccionesDudaEliminar().length < 1) {
            setEliminarVisibleFraccDuda(false);
        }
    }

    public Date getMaxDate() {
        return maxDate != null ? (Date) maxDate.clone() : null;
    }

    public void setMaxDate(Date maxDate) {
        if (maxDate != null) {
            this.maxDate = (Date) maxDate.clone();
        }
        else {
            this.maxDate = null;
        }
    }

    public FirmaDTO getFirma() {
        return firma;
    }

    public void setFirma(FirmaDTO firma) {
        this.firma = firma;
    }

    public String getCadenaOriginal() {
        return cadenaOriginal;
    }

    public void setCadenaOriginal(String cadenaOriginal) {
        this.cadenaOriginal = cadenaOriginal;
    }

    public String getFirmaDigital() {
        return firmaDigital;
    }

    public void setFirmaDigital(String firmaDigital) {
        this.firmaDigital = firmaDigital;
    }

    public boolean isTipoPer() {
        return tipoPer;
    }

    public void setTipoPer(boolean tipoPer) {
        this.tipoPer = tipoPer;
    }

    public boolean isTipoPerMor() {
        return tipoPerMor;
    }

    public void setTipoPerMor(boolean tipoPerMor) {
        this.tipoPerMor = tipoPerMor;
    }

    public boolean isTipoClasificacionArancelariaCombo() {
        return tipoClasificacionArancelariaCombo;
    }

    public void setTipoClasificacionArancelariaCombo(boolean tipoClasificacionArancelariaCombo) {
        this.tipoClasificacionArancelariaCombo = tipoClasificacionArancelariaCombo;
    }

    public boolean isHechosCirc() {
        return hechosCirc;
    }

    public void setHechosCirc(boolean hechosCirc) {
        this.hechosCirc = hechosCirc;
    }

    public boolean isMediosDefensa() {
        return mediosDefensa;
    }

    public void setMediosDefensa(boolean mediosDefensa) {
        this.mediosDefensa = mediosDefensa;
    }

    public boolean isSujetoEjercicio() {
        return sujetoEjercicio;
    }

    public void setSujetoEjercicio(boolean sujetoEjercicio) {
        this.sujetoEjercicio = sujetoEjercicio;
    }

    public boolean isClasificacionArancelaria() {
        return clasificacionArancelaria;
    }

    public void setClasificacionArancelaria(boolean clasificacionArancelaria) {
        this.clasificacionArancelaria = clasificacionArancelaria;
    }

    public boolean isRequerido() {
        return requerido;
    }

    public void setRequerido(boolean requerido) {
        this.requerido = requerido;
    }

    public boolean isRequeridoPrincipal() {
        return requeridoPrincipal;
    }

    public void setRequeridoPrincipal(boolean requeridoPrincipal) {
        this.requeridoPrincipal = requeridoPrincipal;
    }

    public boolean isEliminarVisible() {
        return eliminarVisible;
    }

    public void setEliminarVisible(boolean eliminarVisible) {
        this.eliminarVisible = eliminarVisible;
    }

    public boolean isEliminarVisiblePerOir() {
        return eliminarVisiblePerOir;
    }

    public void setEliminarVisiblePerOir(boolean eliminarVisiblePerOir) {
        this.eliminarVisiblePerOir = eliminarVisiblePerOir;
    }

    public boolean isEliminarVisiblePerInv() {
        return eliminarVisiblePerInv;
    }

    public void setEliminarVisiblePerInv(boolean eliminarVisiblePerInv) {
        this.eliminarVisiblePerInv = eliminarVisiblePerInv;
    }

    public boolean isEliminarVisibleFraccDuda() {
        return eliminarVisibleFraccDuda;
    }

    public void setEliminarVisibleFraccDuda(boolean eliminarVisibleFraccDuda) {
        this.eliminarVisibleFraccDuda = eliminarVisibleFraccDuda;
    }

    public String getMensajeAviso() {
        return mensajeAviso;
    }

    public void setMensajeAviso(String mensajeAviso) {
        this.mensajeAviso = mensajeAviso;
    }

    public boolean isAgregarVisible() {
        return agregarVisible;
    }

    public void setAgregarVisible(boolean agregarVisible) {
        this.agregarVisible = agregarVisible;
    }

    public DatosSolicitudDTO getDatosSolicitud() {
        return datosSolicitud;
    }

    public void setDatosSolicitud(DatosSolicitudDTO datosSolicitud) {
        this.datosSolicitud = datosSolicitud;
    }

    public String getRowsCorreo() {
        return rowsCorreo;
    }

    public void setRowsCorreo(String rowsCorreo) {
        this.rowsCorreo = rowsCorreo;
    }
    
    public void validaAcceso() {
        // Validacion de acceso a la funcionalidad
        this.validaAccesoRolEmpleado(RolesConstantes.ROL_EMPLEADO_OFICIAL_PARTES);
    }    

}
