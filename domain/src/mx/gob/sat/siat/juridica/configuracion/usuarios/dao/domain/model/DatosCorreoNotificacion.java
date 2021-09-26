package mx.gob.sat.siat.juridica.configuracion.usuarios.dao.domain.model;

import com.softtek.idc.client.dao.model.BaseModel;
import mx.gob.sat.siat.juridica.configuracion.usuarios.util.constants.ExcepcionesEnum;
import mx.gob.sat.siat.juridica.configuracion.usuarios.util.constants.TipoMovimientoEnum;

import java.util.List;

public class DatosCorreoNotificacion extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -2243748565788718763L;

    private Long numeroEmpleado;

    private String nombre;

    private String apPaterno;

    private String apMaterno;

    private String rfc;

    private String rfcCorto;

    private String mail;

    private String descripcionPermiso;

    private TipoMovimientoEnum tipoMovimiento;

    private String descripcionEdoActualizacion;

    private ExcepcionesEnum excepcion;

    private List<String> correos;

    public DatosCorreoNotificacion() {
        super();
    }

    public Long getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(Long numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApPaterno() {
        return apPaterno;
    }

    public void setApPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    public String getApMaterno() {
        return apMaterno;
    }

    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDescripcionPermiso() {
        return descripcionPermiso;
    }

    public void setDescripcionPermiso(String descripcionPermiso) {
        this.descripcionPermiso = descripcionPermiso;
    }

    public TipoMovimientoEnum getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(TipoMovimientoEnum tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getDescripcionEdoActualizacion() {
        return descripcionEdoActualizacion;
    }

    public void setDescripcionEdoActualizacion(String descripcionEdoActualizacion) {
        this.descripcionEdoActualizacion = descripcionEdoActualizacion;
    }

    public ExcepcionesEnum getExcepcion() {
        return excepcion;
    }

    public void setExcepcion(ExcepcionesEnum excepcion) {
        this.excepcion = excepcion;
    }

    public List<String> getCorreos() {
        return correos;
    }

    public void setCorreos(List<String> correos) {
        this.correos = correos;
    }

}
