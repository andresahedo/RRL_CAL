package mx.gob.sat.siat.juridica.cal.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoClasificacionArancelaria;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.dto.UnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.base.web.controller.bean.controller.ConsultasController;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.ca.util.validador.TipoRolContribuyenteIDC;
import mx.gob.sat.siat.juridica.cal.dto.SolicitudCALDTO;
import mx.gob.sat.siat.juridica.cal.web.controller.bean.business.ConsultasAutorizacionesCALBusiness;
import mx.gob.sat.siat.juridica.cal.web.controller.bean.constantes.ConsultasConstantesController;
import mx.gob.sat.siat.juridica.cal.web.controller.bean.model.FraccionArancelariaDudaDataModel;
import mx.gob.sat.siat.juridica.cal.web.controller.bean.model.PersonaInvolucradaDataModel;
import mx.gob.sat.siat.juridica.cal.web.controller.bean.model.PersonaOirNotificacionesDataModel;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.ConsultasConstantes;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.DocumentoDataModel;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.DocumentoLazyModelCall;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.model.DocumentoLazyModelOpcionalCall;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.IOException;
import java.util.*;

@ViewScoped
@ManagedBean(name = "consultasAutorizacionesTramiteController")
public class ConsultasAutorizacionesTramiteController extends ConsultasController {

    /**
     * 
     */
    private static final long serialVersionUID = -5368206003216711042L;

    /** DTO que representa los datos de la promocion */
    private SolicitudCALDTO solicitud;

    /** DTO que representa los datos del asunto */
    private TramiteDTO tramiteDTO;

    private PersonaOirNotificacionesDataModel personaOirNotificacionesDataModel;
    private PersonaInvolucradaDataModel personaInvolucradaDataModel;
    private FraccionArancelariaDudaDataModel fraccionArancelariaDudaDataModel;

    private String modalidad;
    private String administracion;
    private List<UnidadAdministrativaDTO> unidadesAdministrativas;

    /** Lista de que representa los valores de gran continuyente */
    private List<CatalogoDTO> listaGranContribuyente;
    /** Lista de autoridad */
    private List<CatalogoDTO> listaAutoridad = new ArrayList<CatalogoDTO>();
    /** Lista de medios de defensa */
    private List<CatalogoDTO> listaMediosDefensa = new ArrayList<CatalogoDTO>();
    /** Lista de sentido de resoluci&oacute;n */
    private List<CatalogoDTO> listaSentidosResolucion = new ArrayList<CatalogoDTO>();

    /**
     * Propiedad que represeta un objeto de tipo DocumentoDataModel.
     */
    private DocumentoDataModel documentoOpcionalDataModel;

    /**
     * Lista que representa si se encuentra dentro del plazo del
     * art&iacute;culo 50
     */
    private List<CatalogoDTO> listaPlazo = new ArrayList<CatalogoDTO>();
    /**
     * Lista con los tipos de clasificacion arancelaria
     */
    private List<CatalogoDTO> listaTipoClasificacion = new ArrayList<CatalogoDTO>();

    /** Lista de documentos obligatorios */
    private List<DocumentoDTO> listaDocumentosObligatorios = new ArrayList<DocumentoDTO>();

    /** Lista de documentos opcionales */
    private List<DocumentoDTO> listaDocumentosOpcionales = new ArrayList<DocumentoDTO>();

    /**
     * Bussines que implenta la logica de negocio para las consultas a
     * autorizaciones.
     */
    @ManagedProperty("#{consultasAutorizacionesCALBusiness}")
    private transient ConsultasAutorizacionesCALBusiness consultasAutorizacionesCALBusiness;

    /**
     * Bandera para determinar si se muestran datos de la seccion
     * "Hechos planteados"
     */
    private boolean hechosCirc;
    /**
     * Bandera para determinar si se muestran datos de la seccion
     * "Medios planteados"
     */
    private boolean mediosDefensa;
    /**
     * Bandera para determinar si se muestran datos de la seccion
     * "Medios planteados"
     */
    private boolean sujetoEjercicio;

    /**
     * Bandera para determinar si se muestran datos de la seccion
     * "Clasificaci&oacute;n arancelaria"
     */
    private boolean clasificacionArancelaria;

    /**
     * Bandera para determinar si se muestran datos de las fracciones
     * en la seccion "Clasificaci&oacute;n arancelaria"
     */
    private boolean tipoClasificacionArancelariaCombo;

    /**
     * Bandera para verificar si se muestra Razon social en caso de
     * ser Persona moral
     */
    private boolean banderaRazonSocial;
    
    private boolean banderaManifiestos;

    private String nombrePersonaGenero;
    
    private String tipoContribuyente;
    @ManagedProperty(value = "#{tipoRolContribuyenteIDC}")
    private TipoRolContribuyenteIDC tipoRolContribuyente;

    private DocumentoLazyModelCall lazyModeDTOCal;
    private Map<String, String> dataDtoCal = new LinkedHashMap<String, String>();  
    private transient DataTable dataTableFil = new DataTable();
   
    private DocumentoLazyModelOpcionalCall lazyModeDTOOpcionCal;
    private Map<String, String> dataDtoOpcionalCal = new LinkedHashMap<String, String>();  
    private transient DataTable dataTableFilOpcional = new DataTable();
    
    private Map<String, String> dataDtoOficiallCal = new LinkedHashMap<String, String>();  
    private transient DataTable dataTableFilOficialCal = new DataTable();

    @PostConstruct
    public void inicio() {
        setFechaMaxima(new Date());
        setDatosBandejaTareaDTO((DatosBandejaTareaDTO) getFlash().get(VistaConstantes.DATOS_BANDEJA_DTO));
        setBanderaManifiestos(false);

        if (getDatosBandejaTareaDTO() != null) {
            setIdSolicitud(getDatosBandejaTareaDTO().getIdSolicitud());
            setIdTipoTramite(getDatosBandejaTareaDTO().getTipoTramite());
            setNumFolio(getDatosBandejaTareaDTO().getNumeroAsunto());
            setEsAbogado(getDatosBandejaTareaDTO().isEsAbogado());
            dataDtoCal.put("idSolicitud",getIdSolicitud().toString());     
            getDataTableFil().setFilters(dataDtoCal); 
            getDataTableFilOpcional().setFilters(dataDtoCal);
            
            dataDtoOficiallCal.put("numFolio", getNumFolio().toString());
            getDataTableFilOficialCal().setFilters(dataDtoOficiallCal);  
            
            setLazyModeDTOCal(getDocumentosConsultaLazyCal());     
            setLazyModeDTOOpcionCal(getDocumentosOpcionalesConsultaLazy());
            setLazyModelOficial(getDocumentoConsultaLazyOficialCal());
        }
        if (getIdSolicitud() != null) {
            setDatosSolicitud(consultasAutorizacionesCALBusiness.buscarSolicitante(getIdSolicitud()));
        }
        if (getDatosSolicitud().getRazonSocial() != null) {
            setBanderaRazonSocial(true);
        }
        else {
            setBanderaRazonSocial(false);
        }

        if (getDatosBandejaTareaDTO().getNumeroAsunto() != null
                && !getDatosBandejaTareaDTO().getNumeroAsunto().isEmpty()) {
            super.obtenerObservaciones();
        }
        try {
            this.setTipoContribuyente(tipoRolContribuyente.consultaHidrocarburos(getDatosSolicitud().getRfcContribuyente()));
        } catch (IOException e) {
            getLogger().error("Error al obtener el tipo de contribuyente:"+e.getMessage());
        }
        
    }

    @Override
    public String getTabSolicitante() {

        return ConsultasConstantesController.CONSULTA_CAL_DATOS_SOLICITANTE;
    }

    /**
     * Metodo que devuelve los datos de un promocion.
     */
    @Override
    public String getTabTramite() {
        setSolicitud(consultasAutorizacionesCALBusiness.obtenerDatos(getIdSolicitud()));
        setListaGranContribuyente(getConsultasAutorizacionesCALBusiness().obtenerListaSiNo());
        setListaAutoridad(getConsultasAutorizacionesCALBusiness().obtenerAutoridadesEmisoras());
        setListaMediosDefensa(getConsultasAutorizacionesCALBusiness().obtenerMediosDeDefensa());
        setListaSentidosResolucion(getConsultasAutorizacionesCALBusiness().obtenerSentidosResolucion(
                getSolicitud().getClaveMedioDefensa()));
        setListaPlazo(getConsultasAutorizacionesCALBusiness().obtenerListaSiNo());
        setListaTipoClasificacion(getConsultasAutorizacionesCALBusiness().obtenerListaTipoClasificacion());

        validaHechosCirc();
        validaMediosDefensa();
        validaSujetoEjercicio();
        validaClasificacionArancelaria();

        setPersonaOirNotificacionesDataModel(new PersonaOirNotificacionesDataModel(getSolicitud()
                .getListaPersonasNotificaciones()));
        setPersonaInvolucradaDataModel(new PersonaInvolucradaDataModel(getSolicitud().getListaPersonasInvolucradas()));
        setFraccionArancelariaDudaDataModel(new FraccionArancelariaDudaDataModel(getSolicitud().getListaFraccionDuda()));

        setTramiteDTO(getConsultasAutorizacionesCALBusiness().obtenerDatosTramite(getIdSolicitud()));
        setModalidad(getConsultasAutorizacionesCALBusiness().obtenerDescripcionModalidad(
                getSolicitud().getTipoTramite()));
        setAdministracion(getConsultasAutorizacionesCALBusiness().obtenerNombreAdministracion(getIdSolicitud()));
        
        if (getSolicitud().getManifiestos() != null && getSolicitud().getManifiestos().size() > 0) {
            setBanderaManifiestos(true);
        }
        
        return ConsultasConstantesController.CONSULTA_CAL_PROMOCION;
    }

    /**
     * Metodo que obtiene los documentos de una solicitud.
     */
    @Override
    public String getTabDocumentos() {
        if (this.getNumFolio() == null) {
            setNumFolio((String) getFlash().get("numAsunto"));
        }
        setDocumentoOficialDataModel(obtenerDocumentosOficiales());
        setDocumentoDataModel(getDocumentosConsulta()); 
        setDocumentoOpcionalDataModel(getDocumentosOpcionalesConsulta()); 
        return ConsultasConstantesController.CONSULTA_CAL_DOCUMENTOS;
    }
    
    /**
     * 
     * @return consultaDatosDocumentos
     */
    public String getTabDocumentosConcluidos() {
        if (this.getNumFolio() == null) {
            setNumFolio((String) getFlash().get("numAsunto"));             
        }
        setDocumentoOficialDataModel(obtenerDocumentosOficiales());
        setDocumentoDataModel(getDocumentosConsulta()); 
        setDocumentoOpcionalDataModel(getDocumentosOpcionalesConsulta());
        return ConsultasConstantes.CONSULTA_DATOS_DOCUMENTOS_CONCLUIDOS_CA;
    }
    
    public String getTabRequerimiento() {
        obtenerRequerimientos();
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
        return ConsultasConstantesController.CONSULTA_CAL_REQUERIMIENTOS;
    }
    
    public String getTabRequerimientoConcluidos() {
        obtenerRequerimientos();
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
        return ConsultasConstantesController.CONSULTA_CAL_REQUERIMIENTOS_CONCLUIDOS;
    }

    /*
     * Implementacion de Lazy documentoDTO
     */
    
    public DocumentoLazyModelCall getDocumentosConsultaLazyCal() {
        return new DocumentoLazyModelCall(consultasAutorizacionesCALBusiness);
    }
    
    /*
     * Lazy - DocumentoOpcionales -
     */
    
    public DocumentoLazyModelOpcionalCall getDocumentosOpcionalesConsultaLazy() {
       return new DocumentoLazyModelOpcionalCall(consultasAutorizacionesCALBusiness);
    }
    
    
    /**
     * 
     * @return getDocumentosConsulta()
     */
    public DocumentoDataModel getDocumentosConsulta() {
        setListaDocumentosObligatorios(getConsultasAutorizacionesCALBusiness().obtenerDocumentosObligatorios(
                getIdSolicitud().toString()));
        return new DocumentoDataModel(getListaDocumentosObligatorios());
    }

    /**
     * 
     * @return getDocumentosConsulta()
     */
    public DocumentoDataModel getDocumentosOpcionalesConsulta() {
        setListaDocumentosOpcionales(getConsultasAutorizacionesCALBusiness().obtenerDocumentosOpcionales(
                getIdSolicitud().toString()));
        return new DocumentoDataModel(getListaDocumentosOpcionales());
    }

    private void validaHechosCirc() {
        hechosCirc = false;
        if (getSolicitud().getHechosCircunstanciasPrevPlanteadas() != null
                && getSolicitud().getHechosCircunstanciasPrevPlanteadas().equals("1")) {
            hechosCirc = true;
        }
    }

    private void validaMediosDefensa() {
        mediosDefensa = false;
        if (getSolicitud().getHechosCircunstanciasMatMedios() != null
                && getSolicitud().getHechosCircunstanciasMatMedios().equals("1")) {
            mediosDefensa = true;
        }
    }

    private void validaSujetoEjercicio() {
        setSujetoEjercicio(false);
        if (getSolicitud().getContribuyenteSujetoEjercicio() != null
                && getSolicitud().getContribuyenteSujetoEjercicio().equals("1")) {
            setSujetoEjercicio(true);
        }
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
    
    public void paginaDescargarResueltosOblDTO() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("listaDocumentosObligatorios.clearSelection()");
        setBolDocDto(true);
    }
    
    public void paginaDescargarResueltosOpcDTO() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("listaDocumentosOpcionales.clearSelection()");
        setBolDocOpcDto(true);
    }
    
    public ConsultasAutorizacionesCALBusiness getConsultasAutorizacionesCALBusiness() {
        return consultasAutorizacionesCALBusiness;
    }

    public void setConsultasAutorizacionesCALBusiness(
            ConsultasAutorizacionesCALBusiness consultasAutorizacionesCALBusiness) {
        this.consultasAutorizacionesCALBusiness = consultasAutorizacionesCALBusiness;
    }

    public SolicitudCALDTO getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudCALDTO solicitud) {
        this.solicitud = solicitud;
    }

    public PersonaOirNotificacionesDataModel getPersonaOirNotificacionesDataModel() {
        return personaOirNotificacionesDataModel;
    }

    public void
            setPersonaOirNotificacionesDataModel(PersonaOirNotificacionesDataModel personaOirNotificacionesDataModel) {
        this.personaOirNotificacionesDataModel = personaOirNotificacionesDataModel;
    }

    public PersonaInvolucradaDataModel getPersonaInvolucradaDataModel() {
        return personaInvolucradaDataModel;
    }

    public void setPersonaInvolucradaDataModel(PersonaInvolucradaDataModel personaInvolucradaDataModel) {
        this.personaInvolucradaDataModel = personaInvolucradaDataModel;
    }

    public FraccionArancelariaDudaDataModel getFraccionArancelariaDudaDataModel() {
        return fraccionArancelariaDudaDataModel;
    }

    public void setFraccionArancelariaDudaDataModel(FraccionArancelariaDudaDataModel fraccionArancelariaDudaDataModel) {
        this.fraccionArancelariaDudaDataModel = fraccionArancelariaDudaDataModel;
    }

    public List<CatalogoDTO> getListaGranContribuyente() {
        return listaGranContribuyente;
    }

    public void setListaGranContribuyente(List<CatalogoDTO> listaGranContribuyente) {
        this.listaGranContribuyente = listaGranContribuyente;
    }

    public boolean isHechosCirc() {
        return hechosCirc;
    }

    public void setHechosCirc(boolean hechosCirc) {
        this.hechosCirc = hechosCirc;
    }

    public List<CatalogoDTO> getListaAutoridad() {
        return listaAutoridad;
    }

    public void setListaAutoridad(List<CatalogoDTO> listaAutoridad) {
        this.listaAutoridad = listaAutoridad;
    }

    public boolean isMediosDefensa() {
        return mediosDefensa;
    }

    public void setMediosDefensa(boolean mediosDefensa) {
        this.mediosDefensa = mediosDefensa;
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

    public boolean isSujetoEjercicio() {
        return sujetoEjercicio;
    }

    public void setSujetoEjercicio(boolean sujetoEjercicio) {
        this.sujetoEjercicio = sujetoEjercicio;
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

    public boolean isClasificacionArancelaria() {
        return clasificacionArancelaria;
    }

    public void setClasificacionArancelaria(boolean clasificacionArancelaria) {
        this.clasificacionArancelaria = clasificacionArancelaria;
    }

    public boolean isTipoClasificacionArancelariaCombo() {
        return tipoClasificacionArancelariaCombo;
    }

    public void setTipoClasificacionArancelariaCombo(boolean tipoClasificacionArancelariaCombo) {
        this.tipoClasificacionArancelariaCombo = tipoClasificacionArancelariaCombo;
    }

    public TramiteDTO getTramiteDTO() {
        return tramiteDTO;
    }

    public void setTramiteDTO(TramiteDTO tramiteDTO) {
        this.tramiteDTO = tramiteDTO;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public List<UnidadAdministrativaDTO> getUnidadesAdministrativas() {
        return unidadesAdministrativas;
    }

    public void setUnidadesAdministrativas(List<UnidadAdministrativaDTO> unidadesAdministrativas) {
        this.unidadesAdministrativas = unidadesAdministrativas;
    }

    public String getAdministracion() {
        return administracion;
    }

    public void setAdministracion(String administracion) {
        this.administracion = administracion;
    }

    public boolean isBanderaRazonSocial() {
        return banderaRazonSocial;
    }

    public void setBanderaRazonSocial(boolean banderaRazonSocial) {
        this.banderaRazonSocial = banderaRazonSocial;
    }

    public List<DocumentoDTO> getListaDocumentosOpcionales() {
        return listaDocumentosOpcionales;
    }

    public void setListaDocumentosOpcionales(List<DocumentoDTO> listaDocumentosOpcionales) {
        this.listaDocumentosOpcionales = listaDocumentosOpcionales;
    }

    public DocumentoDataModel getDocumentoOpcionalDataModel() {
        return documentoOpcionalDataModel;
    }

    public void setDocumentoOpcionalDataModel(DocumentoDataModel documentoOpcionalDataModel) {
        this.documentoOpcionalDataModel = documentoOpcionalDataModel;
    }

    public List<DocumentoDTO> getListaDocumentosObligatorios() {
        return listaDocumentosObligatorios;
    }

    public void setListaDocumentosObligatorios(List<DocumentoDTO> listaDocumentosObligatorios) {
        this.listaDocumentosObligatorios = listaDocumentosObligatorios;
    }

    public String getNombrePersonaGenero() {
        return nombrePersonaGenero;
    }

    public void setNombrePersonaGenero(String nombrePersonaGenero) {
        this.nombrePersonaGenero = nombrePersonaGenero;
    }

    public boolean isBanderaManifiestos() {
        return banderaManifiestos;
    }

    public void setBanderaManifiestos(boolean banderaManifiestos) {
        this.banderaManifiestos = banderaManifiestos;
    }

    public String getTipoContribuyente() {
        return tipoContribuyente;
    }

    public void setTipoContribuyente(String tipoContribuyente) {
        this.tipoContribuyente = tipoContribuyente;
    }

    public void setTipoRolContribuyente(TipoRolContribuyenteIDC tipoRolContribuyente) {
        this.tipoRolContribuyente = tipoRolContribuyente;
    }
    
    public DocumentoLazyModelCall getLazyModeDTOCal() {
        return lazyModeDTOCal;
    }

    public void setLazyModeDTOCal(DocumentoLazyModelCall lazyModeDTOCal) {
        this.lazyModeDTOCal = lazyModeDTOCal;
    }

    public Map<String, String> getDataDtoCal() {
        return dataDtoCal;
    }

    public void setDataDtoCal(Map<String, String> dataDtoCal) {
        this.dataDtoCal = dataDtoCal;
    }

    public DataTable getDataTableFil() {
        return dataTableFil;
    }

    public DocumentoLazyModelOpcionalCall getLazyModeDTOOpcionCal() {
        return lazyModeDTOOpcionCal;
    }

    public void setLazyModeDTOOpcionCal(DocumentoLazyModelOpcionalCall lazyModeDTOOpcionCal) {
        this.lazyModeDTOOpcionCal = lazyModeDTOOpcionCal;
    }

    public Map<String, String> getDataDtoOpcionalCal() {
        return dataDtoOpcionalCal;
    }

    public void setDataDtoOpcionalCal(Map<String, String> dataDtoOpcionalCal) {
        this.dataDtoOpcionalCal = dataDtoOpcionalCal;
    }

    public DataTable getDataTableFilOpcional() {
        return dataTableFilOpcional;
    }


    public void setDataTableFilOpcional(DataTable dataTableFilOpcional) {
        this.dataTableFilOpcional = dataTableFilOpcional;
    }

    public DataTable getDataTableFilOficialCal() {
        return dataTableFilOficialCal;
    }

    public void setDataTableFilOficialCal(DataTable dataTableFilOficialCal) {
        this.dataTableFilOficialCal = dataTableFilOficialCal;
    }
}
