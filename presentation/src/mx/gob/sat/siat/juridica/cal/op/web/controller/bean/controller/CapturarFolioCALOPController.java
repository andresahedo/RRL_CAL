package mx.gob.sat.siat.juridica.cal.op.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.web.controller.bean.controller.CapturarFolioOPController;
import mx.gob.sat.siat.juridica.cal.dto.SolicitudCALDTO;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.Date;

@ViewScoped
@ManagedBean
public class CapturarFolioCALOPController extends CapturarFolioOPController<SolicitudCALDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private SolicitudCALDTO solicitud;

    private String modalidad;
    private String unidadAdmin;
    private String idModulo;
    private String asignacion;
    private Date fechaMax;

    @PostConstruct
    public void iniciar() {
        super.iniciar();
        fechaMax = new Date();
        setSolicitudDTO(new SolicitudCALDTO());
        getSolicitudDTO().setSolicitante(getPersonaSolicitudDTO());
        setModalidad((String) getFlash().get("modalidad"));
        setUnidadAdmin((String) getFlash().get("unidadAdmin"));
        setIdModulo((String) getFlash().get("idModulo"));
        setAsignacion((String) getFlash().get("asignacion"));

    }

    @Override
    public SolicitudCALDTO getSolicitudDTO() {
        return solicitud;
    }

    @Override
    public void setSolicitudDTO(SolicitudCALDTO solicitudDTO) {
        this.solicitud = solicitudDTO;

    }

    @Override
    public String getUrl() {
        getFlash().put("modalidad", getModalidad());
        getFlash().put("unidadAdmin", getUnidadAdmin());
        getFlash().put("idModulo", getIdModulo());
        getFlash().put("asignacion", getAsignacion());
        return "/resources/pages/cal/op/registroSolicitud/seleccionModalidadCA.xhtml";
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getUnidadAdmin() {
        return unidadAdmin;
    }

    public void setUnidadAdmin(String unidadAdmin) {
        this.unidadAdmin = unidadAdmin;
    }

    public String getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(String idModulo) {
        this.idModulo = idModulo;
    }

    public String getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(String asignacion) {
        this.asignacion = asignacion;
    }

    public Date getFechaMax() {
        return fechaMax != null ? (Date) fechaMax.clone() : null;
    }

    public void setFechaMax(Date fechaMax) {
        if (fechaMax != null) {
            this.fechaMax = (Date) fechaMax.clone();
        }
        else {
            this.fechaMax = null;
        }
    }
}
