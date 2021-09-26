/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.dto;

import mx.gob.sat.siat.juridica.base.dto.BaseDataTransferObject;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.SolicitudDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author Softtek
 * 
 */
public class ResolucionAbogadoDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = -2839406471202603280L;

    private Long idResolucion;
    private String idTramite;
    private String idTramiteOrig;
    private Long idResolucionOriginal;
    private String ideSentidoResolucion;
    private Date fechaInicioVigencia;
    private Date fechaResolucion;
    private Date fechaFinVigencia;
    private String ideEstadoResolucion;
    private String numeroFolioResolucion;
    private Date fechaRenovacion;
    private BigDecimal montoTotalControvertido;
    private List<ResolucionImpugnadaDTO> resolucionesDTO;
    private List<CatalogoDTO> recursos;
    private String numeroOficio;
    private Date fechaEmision;

    // Validar que objeto van a utilizar en el registro de la
    // solicitud
    private SolicitudDTO solicitudDTO;

    private FuncionarioDTO funcionarioDTO;

    private TramiteDTO tramiteDTO;

    public Long getIdResolucion() {
        return idResolucion;
    }

    public void setIdResolucion(Long idResolucion) {
        this.idResolucion = idResolucion;
    }

    public String getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(String idTramite) {
        this.idTramite = idTramite;
    }

    public String getIdTramiteOrig() {
        return idTramiteOrig;
    }

    public void setIdTramiteOrig(String idTramiteOrig) {
        this.idTramiteOrig = idTramiteOrig;
    }

    public Long getIdResolucionOriginal() {
        return idResolucionOriginal;
    }

    public void setIdResolucionOriginal(Long idResolucionOriginal) {
        this.idResolucionOriginal = idResolucionOriginal;
    }

    public String getIdeSentidoResolucion() {
        return ideSentidoResolucion;
    }

    public void setIdeSentidoResolucion(String ideSentidoResolucion) {
        this.ideSentidoResolucion = ideSentidoResolucion;
    }

    public Date getFechaInicioVigencia() {
        return fechaInicioVigencia != null ? (Date) fechaInicioVigencia.clone() : null;
    }

    public void setFechaInicioVigencia(Date fechaInicioVigencia) {
        this.fechaInicioVigencia = fechaInicioVigencia != null ? (Date) fechaInicioVigencia.clone() : null;
    }

    public Date getFechaResolucion() {
        return fechaResolucion != null ? (Date) fechaResolucion.clone() : null;
    }

    public void setFechaResolucion(Date fechaResolucion) {
        this.fechaResolucion = fechaResolucion != null ? (Date) fechaResolucion.clone() : null;
    }

    public Date getFechaFinVigencia() {
        return fechaFinVigencia != null ? (Date) fechaFinVigencia.clone() : null;
    }

    public void setFechaFinVigencia(Date fechaFinVigencia) {
        this.fechaFinVigencia = fechaFinVigencia != null ? (Date) fechaFinVigencia.clone() : null;
    }

    public String getIdeEstadoResolucion() {
        return ideEstadoResolucion;
    }

    public void setIdeEstadoResolucion(String ideEstadoResolucion) {
        this.ideEstadoResolucion = ideEstadoResolucion;
    }

    public String getNumeroFolioResolucion() {
        return numeroFolioResolucion;
    }

    public void setNumeroFolioResolucion(String numeroFolioResolucion) {
        this.numeroFolioResolucion = numeroFolioResolucion;
    }

    public Date getFechaRenovacion() {
        return fechaRenovacion != null ? (Date) fechaRenovacion.clone() : null;
    }

    public void setFechaRenovacion(Date fechaRenovacion) {
        this.fechaRenovacion = fechaRenovacion != null ? (Date) fechaRenovacion.clone() : null;
    }

    public List<ResolucionImpugnadaDTO> getResolucionesDTO() {
        return resolucionesDTO;
    }

    public void setResolucionesDTO(List<ResolucionImpugnadaDTO> resolucionesDTO) {
        this.resolucionesDTO = resolucionesDTO;
    }

    public SolicitudDTO getSolicitudDTO() {
        return solicitudDTO;
    }

    public void setSolicitudDTO(SolicitudDTO solicitudDTO) {
        this.solicitudDTO = solicitudDTO;
    }

    public FuncionarioDTO getFuncionarioDTO() {
        return funcionarioDTO;
    }

    public void setFuncionarioDTO(FuncionarioDTO funcionarioDTO) {
        this.funcionarioDTO = funcionarioDTO;
    }

    public TramiteDTO getTramiteDTO() {
        return tramiteDTO;
    }

    public void setTramiteDTO(TramiteDTO tramiteDTO) {
        this.tramiteDTO = tramiteDTO;
    }

    public List<CatalogoDTO> getRecursos() {
        return recursos;
    }

    public void setRecursos(List<CatalogoDTO> recursos) {
        this.recursos = recursos;
    }

    public BigDecimal getMontoTotalControvertido() {
        return montoTotalControvertido;
    }

    public void setMontoTotalControvertido(BigDecimal montoTotalControvertido) {
        this.montoTotalControvertido = montoTotalControvertido;
    }

    /**
     * @return the numeroOficio
     */
    public String getNumeroOficio() {
        return numeroOficio;
    }

    /**
     * @param numeroOficio
     *            the numeroOficio to set
     */
    public void setNumeroOficio(String numeroOficio) {
        this.numeroOficio = numeroOficio;
    }

    /**
     * @return the fechaEmision
     */
    public Date getFechaEmision() {
        return fechaEmision != null ? (Date) fechaEmision.clone() : null;
    }

    /**
     * @param fechaEmision
     *            the fechaEmision to set
     */
    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision != null ? (Date) fechaEmision.clone() : null;
    }

}
