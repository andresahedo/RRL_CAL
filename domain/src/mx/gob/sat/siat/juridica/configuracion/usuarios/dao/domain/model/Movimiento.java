package mx.gob.sat.siat.juridica.configuracion.usuarios.dao.domain.model;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "RVCT_MOVIMIENTO")
public class Movimiento extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -7184739507802262513L;

    @Id
    @Column(name = "IDMOVIMIENTO")
    private Long idMovimiento;

    @Column(name = "IDROL", nullable = true)
    private String rol;

    @Column(name = "IDPETICION")
    private Long idPeticion;

    @Column(name = "IDETIPOMOVTO")
    private String ideTipoMovto;

    @Column(name = "NUMEMPLEADO")
    private Long numEmpleado;

    @Column(name = "BLNEMPLEADOACTIVO", nullable = true)
    private boolean indicador;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "APELLIDOPATERNO")
    private String paterno;

    @Column(name = "APELLIDOMATERNO", nullable = true)
    private String materno;

    @Column(name = "RFC")
    private String rfc;

    @Column(name = "RFCCORTO")
    private String rfcCorto;

    @Column(name = "CORREOELECTRONICO")
    private String correo;

    @Column(name = "FECHAMOVTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaMovto;

    @Column(name = "IDEEXMOVTO", nullable = true)
    private String ideExMovto;

    @Column(name = "IDEESTADOMOVTO", nullable = true)
    private String ideEstadoMovto;

    @Column(name = "FECHAPROCESADOEX", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaProcesadoEx;

    @Column(name = "FECHANOTIFICADO", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNotificado;

    public Long getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(Long idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Long getIdPeticion() {
        return idPeticion;
    }

    public void setIdPeticion(Long idPeticion) {
        this.idPeticion = idPeticion;
    }

    public String getIdeTipoMovto() {
        return ideTipoMovto;
    }

    public void setIdeTipoMovto(String ideTipoMovto) {
        this.ideTipoMovto = ideTipoMovto;
    }

    public Long getNumEmpleado() {
        return numEmpleado;
    }

    public void setNumEmpleado(Long numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

    public boolean isIndicador() {
        return indicador;
    }

    public void setIndicador(boolean indicador) {
        this.indicador = indicador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRfcCorto() {
        return rfcCorto;
    }

    public void setRfcCorto(String rfcCorto) {
        this.rfcCorto = rfcCorto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Date getFechaMovto() {
        return (null == fechaMovto ? null : (Date) fechaMovto.clone());
    }

    public void setFechaMovto(Date fechaMovto) {
        this.fechaMovto = (null == fechaMovto ? null : (Date) fechaMovto.clone());
    }

    public String getIdeExMovto() {
        return ideExMovto;
    }

    public void setIdeExMovto(String ideExMovto) {
        this.ideExMovto = ideExMovto;
    }

    public String getIdeEstadoMovto() {
        return ideEstadoMovto;
    }

    public void setIdeEstadoMovto(String ideEstadoMovto) {
        this.ideEstadoMovto = ideEstadoMovto;
    }

    public Date getFechaProcesadoEx() {
        return (null == fechaProcesadoEx ? null : (Date) fechaProcesadoEx.clone());
    }

    public void setFechaProcesadoEx(Date fechaProcesadoEx) {
        this.fechaProcesadoEx = (null == fechaProcesadoEx ? null : (Date) fechaProcesadoEx.clone());
    }

    public Date getFechaNotificado() {
        return (null == fechaNotificado ? null : (Date) fechaNotificado.clone());
    }

    public void setFechaNotificado(Date fechaNotificado) {
        this.fechaNotificado = (null == fechaNotificado ? null : (Date) fechaNotificado.clone());
    }

}
