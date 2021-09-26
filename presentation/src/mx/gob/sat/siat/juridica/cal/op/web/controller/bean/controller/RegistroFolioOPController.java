package mx.gob.sat.siat.juridica.cal.op.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.cal.op.web.controller.bean.business.RegistroFolioOPBusiness;
import mx.gob.sat.siat.juridica.oficialia.web.controller.bean.constantes.OficialiaConstantesController;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.Date;
import java.util.ResourceBundle;

@ViewScoped
@ManagedBean(name = "registroFolioOPController")
public class RegistroFolioOPController extends BaseControllerBean {

    private static final long serialVersionUID = -7451812718090275728L;

    @ManagedProperty("#{registroFolioOPBusiness}")
    private RegistroFolioOPBusiness registroFolioOPBusiness;

    private Tramite tramite;

    private DatosBandejaTareaDTO dbdto;

    private DocumentoDTO documentoDTO = new DocumentoDTO();

    private Date fechaApertura;

    private Long folio;

    private Date fechaMax;

    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;

    @PostConstruct
    public void iniciar() {
        fechaMax = new Date();
        dbdto = (DatosBandejaTareaDTO) getFlash().get(VistaConstantes.DATOS_BANDEJA_DTO);
        tramite = getRegistroFolioOPBusiness().obtenerTramitePorId(dbdto.getNumeroAsunto());
    }

    public void adjuntarDocumento() {
        documentoDTO.setFolio(folio);
        documentoDTO.setFechaApertura(fechaApertura);
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDbdto());
        getFlash().put("documentoDTO", documentoDTO);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().setResponseCharacterEncoding("UTF-8");
        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) facesContext.getApplication().getNavigationHandler();
        configurableNavigationHandler
                .performNavigation(OficialiaConstantesController.ADJUNTAR_DOCTO_CUMPLIMENTACION_OFICIALIA);
    }

    public RegistroFolioOPBusiness getRegistroFolioOPBusiness() {
        return registroFolioOPBusiness;
    }

    public void setRegistroFolioOPBusiness(RegistroFolioOPBusiness registroFolioOPBusiness) {
        this.registroFolioOPBusiness = registroFolioOPBusiness;
    }

    public Tramite getTramite() {
        return tramite;
    }

    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }

    public DatosBandejaTareaDTO getDbdto() {
        return dbdto;
    }

    public void setDbdto(DatosBandejaTareaDTO dbdto) {
        this.dbdto = dbdto;
    }

    public Date getFechaApertura() {
        return fechaApertura != null ? (Date) fechaApertura.clone() : null;
    }

    public void setFechaApertura(Date fechaApertura) {
        if (fechaApertura != null) {
            this.fechaApertura = (Date) fechaApertura.clone();
        }
        else {
            this.fechaApertura = null;
        }
    }

    public Long getFolio() {
        return folio;
    }

    public void setFolio(Long folio) {
        this.folio = folio;
    }

    public DocumentoDTO getDocumentoDTO() {
        return documentoDTO;
    }

    public void setDocumentoDTO(DocumentoDTO documentoDTO) {
        this.documentoDTO = documentoDTO;
    }

    public ResourceBundle getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(ResourceBundle errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * @return the fechaMax
     */
    public Date getFechaMax() {
        return fechaMax != null ? (Date) fechaMax.clone() : null;
    }

    /**
     * @param fechaMax
     *            the fechaMax to set
     */
    public void setFechaMax(Date fechaMax) {
        if (fechaMax != null) {
            this.fechaMax = (Date) fechaMax.clone();
        }
        else {
            this.fechaMax = null;
        }
    }

}
