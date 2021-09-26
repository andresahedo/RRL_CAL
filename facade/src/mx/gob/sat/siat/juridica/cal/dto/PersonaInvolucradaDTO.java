package mx.gob.sat.siat.juridica.cal.dto;

import mx.gob.sat.siat.juridica.base.dto.BaseDataTransferObject;

public class PersonaInvolucradaDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long idPersona;

    private String nombre;

    private String paterno;

    private String materno;

    private String rfc;

    private String razonSocial;

    private String direccion;

    private String tipoPersona;

    private boolean nuevo;

    private boolean extranjero;
    
    private String estadoContribuyente;
 private String tipoRolContribuyente;

    public PersonaInvolucradaDTO() {

    }

    /**
     * @return the idPersona
     */
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     * @param idPersona
     *            the idPersona to set
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre
     *            the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the paterno
     */
    public String getPaterno() {
        return paterno;
    }

    /**
     * @param paterno
     *            the paterno to set
     */
    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    /**
     * @return the materno
     */
    public String getMaterno() {
        return materno;
    }

    /**
     * @param materno
     *            the materno to set
     */
    public void setMaterno(String materno) {
        this.materno = materno;
    }

    /**
     * @return the rfc
     */
    public String getRfc() {
        return rfc;
    }

    /**
     * @param rfc
     *            the rfc to set
     */
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    /**
     * @return the razonSocial
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * @param razonSocial
     *            the razonSocial to set
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion
     *            the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the tipoPersona
     */
    public String getTipoPersona() {
        return tipoPersona;
    }

    /**
     * @param tipoPersona
     *            the tipoPersona to set
     */
    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    /**
     * @return the nuevo
     */
    public boolean isNuevo() {
        return nuevo;
    }

    /**
     * @param nuevo
     *            the nuevo to set
     */
    public void setNuevo(boolean nuevo) {
        this.nuevo = nuevo;
    }

    /**
     * @return Nombre conpleto derivado de: 1. la concatenacion,
     *         separados por un espacio, de los atributos nombre,
     *         paterno y materno para personas f�sicas 2. La
     *         raz&oacute;n social
     */
    public String getNombreCompleto() {
        String nombreCompleto = "";
        if (razonSocial != null) {
            nombreCompleto = razonSocial;
        }
        else {
            nombreCompleto =
                    (nombre != null ? nombre : "") + " " + (paterno != null ? paterno : "") + " "
                            + (materno != null ? materno : "");
        }
        return nombreCompleto;
    }

    public boolean isExtranjero() {
        return extranjero;
    }

    public void setExtranjero(boolean extranjero) {
        this.extranjero = extranjero;
    }

    /**
     * @return the estadoContribuyente
     */
    public String getEstadoContribuyente() {
        return estadoContribuyente;
    }

    /**
     * @param estadoContribuyente the estadoContribuyente to set
     */
    public void setEstadoContribuyente(String estadoContribuyente) {
        this.estadoContribuyente = estadoContribuyente;
    }
    public String getTipoRolContribuyente() {
        return tipoRolContribuyente;
    }

    public void setTipoRolContribuyente(String tipoRolContribuyente) {
        this.tipoRolContribuyente = "HidrocarburosCAL1";
    }

}
