package mx.gob.sat.siat.juridica.cal.dto;

import mx.gob.sat.siat.juridica.base.dto.BaseDataTransferObject;

public class PersonaOirNotificacionesDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long idPersona;

    private String nombre;

    private String paterno;

    private String materno;

    private String rfc;

    private String telefono;

    private boolean nuevo;

    private String direccion;
    
    private String estadoContribuyente;
    private String tipoRolContribuyente;

    public PersonaOirNotificacionesDTO() {

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
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono
     *            the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
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
     * @return Nombre conpleto de la concatenacion, separados por un
     *         espacio, de los atributos nombre, paterno y materno,
     *         separados por un espacio. Si alguno de los atrbutos es
     *         nulo, se sustituye por la cadena vac&iacute;a.
     */
    public String getNombreCompleto() {

        return (nombre != null ? nombre : "") + " " + (paterno != null ? paterno : "") + " "
                + (materno != null ? materno : "");
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    
    /**
     * @param Tipo rol Contribuyente the estadoContribuyente to set
     */
    public String getTipoRolContribuyente() {
        return tipoRolContribuyente;
    }

    public void setTipoRolContribuyente(String tipoRolContribuyente) {
        this.tipoRolContribuyente = "HidrocarburosCAL";
    }


}
