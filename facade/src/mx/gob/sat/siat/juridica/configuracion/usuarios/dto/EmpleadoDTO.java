/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.dto;

import mx.gob.sat.siat.juridica.base.dto.BaseDataTransferObject;

/**
 * Clase Controller para la administracion de empleados
 * 
 * @author Softtek - EQG
 * @since 31/10/2014
 */
public class EmpleadoDTO extends BaseDataTransferObject {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -1222293887828650604L;

    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String rfcEmpleado;
    private String rfcCorto;
    private String correoElectronico;
    private String numeroEmpleado;
    private String unidadAdmin;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getRfcEmpleado() {
        return rfcEmpleado;
    }

    public void setRfcEmpleado(String rfcEmpleado) {
        this.rfcEmpleado = rfcEmpleado;
    }

    public String getRfcCorto() {
        return rfcCorto;
    }

    public void setRfcCorto(String rfcCorto) {
        this.rfcCorto = rfcCorto;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public String getUnidadAdmin() {
        return unidadAdmin;
    }

    public void setUnidadAdmin(String unidadAdmin) {
        this.unidadAdmin = unidadAdmin;
    }

}
