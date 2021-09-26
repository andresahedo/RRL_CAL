package mx.gob.sat.siat.juridica.base.dto;

public class PersonaSolicitudDTO extends BaseDataTransferObject {

    private static final long serialVersionUID = 5016646641004098481L;
    private String rfc;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private DomicilioSolicitudDTO domicilio;
    private String razonSocial;
    private String representanteLegal;
    private Boolean blnExtranjero;
    private Boolean blnTieneCorreo;
    private Boolean amparado;
    private boolean personaMoral;
    private String estadoContribuyente;
    private String tipoRolContribuyente;
    public Boolean getAmparado() {
        return amparado;
    }

    public void setAmparado(Boolean amparado) {
        this.amparado = amparado;
    }

    public boolean isPersonaMoral() {
        return personaMoral;
    }

    public void setPersonaMoral(boolean personaMoral) {
        this.personaMoral = personaMoral;
    }

    public PersonaSolicitudDTO() {}

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

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

    public DomicilioSolicitudDTO getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(DomicilioSolicitudDTO domicilio) {
        this.domicilio = domicilio;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Boolean getBlnExtranjero() {
        return blnExtranjero;
    }

    public void setBlnExtranjero(Boolean blnExtranjero) {
        this.blnExtranjero = blnExtranjero;
    }

    public Boolean getBlnTieneCorreo() {
        return blnTieneCorreo;
    }

    public void setBlnTieneCorreo(Boolean blnTieneCorreo) {
        this.blnTieneCorreo = blnTieneCorreo;
    }

    public String getRepresentanteLegal() {
        return representanteLegal;
    }

    public void setRepresentanteLegal(String representanteLegal) {
        this.representanteLegal = representanteLegal;
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
     * @param tipoRolContribuyente the estadoContribuyente to set
     */
    public String getTipoRolContribuyente() {
        return tipoRolContribuyente;
    }

    public void setTipoRolContribuyente(String tipoRolContribuyente) {
        this.tipoRolContribuyente = tipoRolContribuyente;
    }

}
