package mx.gob.sat.siat.juridica.configuracion.usuarios.dto;

import mx.gob.sat.siat.juridica.base.dto.BaseDataTransferObject;

import java.util.Date;

public class BitacoraDTO extends BaseDataTransferObject {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 3552929800939765134L;

    private Long idBitacora;
    private Integer idTipoTramite;
    private String idTramite;
    private String idRol;
    private String idUniAdmin;
    private String idAccion;
    private String accion;
    private Date fechaAccion;
    private String nomUniAdmin;
    private String idRealizadoPor;
    private String numEmpRealiza;
    private String rfcEmpleadoRealiza;
    private String nomEmpRealiza;
    private String idAplicadoA;
    private String numEmpAplicadoA;
    private String rfcAplicadoA;
    private String nomEmpAplicadoA;
    private String descripcion;
    private Long modalidad;

    public Long getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(Long idBitacora) {
        this.idBitacora = idBitacora;
    }

    public String getIdRol() {
        return idRol;
    }

    public void setIdRol(String idRol) {
        this.idRol = idRol;
    }

    public String getIdUniAdmin() {
        return idUniAdmin;
    }

    public void setIdUniAdmin(String idUniAdmin) {
        this.idUniAdmin = idUniAdmin;
    }

    public String getIdAccion() {
        return idAccion;
    }

    public void setIdAccion(String idAccion) {
        this.idAccion = idAccion;
    }

    public Date getFechaAccion() {
        return (null == fechaAccion ? null : (Date) fechaAccion.clone());
    }

    public void setFechaAccion(Date fechaAccion) {
        this.fechaAccion = (null == fechaAccion ? null : (Date) fechaAccion.clone());
    }

    public String getNomUniAdmin() {
        return nomUniAdmin;
    }

    public void setNomUniAdmin(String nomUniAdmin) {
        this.nomUniAdmin = nomUniAdmin;
    }

    public String getIdRealizadoPor() {
        return idRealizadoPor;
    }

    public void setIdRealizadoPor(String idRealizadoPor) {
        this.idRealizadoPor = idRealizadoPor;
    }

    public String getNumEmpRealiza() {
        return numEmpRealiza;
    }

    public void setNumEmpRealiza(String numEmpRealiza) {
        this.numEmpRealiza = numEmpRealiza;
    }

    public String getRfcEmpleadoRealiza() {
        return rfcEmpleadoRealiza;
    }

    public void setRfcEmpleadoRealiza(String rfcEmpleadoRealiza) {
        this.rfcEmpleadoRealiza = rfcEmpleadoRealiza;
    }

    public String getNomEmpRealiza() {
        return nomEmpRealiza;
    }

    public void setNomEmpRealiza(String nomEmpRealiza) {
        this.nomEmpRealiza = nomEmpRealiza;
    }

    public String getIdAplicadoA() {
        return idAplicadoA;
    }

    public void setIdAplicadoA(String idAplicadoA) {
        this.idAplicadoA = idAplicadoA;
    }

    public String getNumEmpAplicadoA() {
        return numEmpAplicadoA;
    }

    public void setNumEmpAplicadoA(String numEmpAplicadoA) {
        this.numEmpAplicadoA = numEmpAplicadoA;
    }

    public String getRfcAplicadoA() {
        return rfcAplicadoA;
    }

    public void setRfcAplicadoA(String rfcAplicadoA) {
        this.rfcAplicadoA = rfcAplicadoA;
    }

    public String getNomEmpAplicadoA() {
        return nomEmpAplicadoA;
    }

    public void setNomEmpAplicadoA(String nomEmpAplicadoA) {
        this.nomEmpAplicadoA = nomEmpAplicadoA;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getModalidad() {
        return modalidad;
    }

    public void setModalidad(Long modalidad) {
        this.modalidad = modalidad;
    }

    public String getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(String idTramite) {
        this.idTramite = idTramite;
    }

    public void setIdTipoTramite(Integer idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    public Integer getIdTipoTramite() {
        return idTipoTramite;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

}
