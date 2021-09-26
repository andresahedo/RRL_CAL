package mx.gob.sat.siat.juridica.base.dto;

public class DomicilioSolicitudDTO extends BaseDataTransferObject {

    private static final long serialVersionUID = 7091041572159420173L;
    private String calle;
    private String numeroExterior;
    private String numeroInterior;
    private String colonia;
    private String delegacionMunicipio;
    private String estado;
    private String codigoPostal;
    private String telefono;
    private String correoElectronico;
    private String representanteLegal;
    private String administracionLocal;
    private String nombreAdministracionLocal;
    private String cvePais;
    private String nombrePais;
    private String cveEstado;
    private String cveDelegacion;
    private String cveColonia;
    private String cveLocalidad;

    public DomicilioSolicitudDTO() {}

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumeroExterior() {
        return numeroExterior;
    }

    public void setNumeroExterior(String numeroExterior) {
        this.numeroExterior = numeroExterior;
    }

    public String getNumeroInterior() {
        return numeroInterior;
    }

    public void setNumeroInterior(String numeroInterior) {
        this.numeroInterior = numeroInterior;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getDelegacionMunicipio() {
        return delegacionMunicipio;
    }

    public void setDelegacionMunicipio(String delegacionMunicipio) {
        this.delegacionMunicipio = delegacionMunicipio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getRepresentanteLegal() {
        return representanteLegal;
    }

    public void setRepresentanteLegal(String representanteLegal) {
        this.representanteLegal = representanteLegal;
    }

    public String getAdministracionLocal() {
        return administracionLocal;
    }

    public void setAdministracionLocal(String administracionLocal) {
        this.administracionLocal = administracionLocal;
    }

    public String getNombreAdministracionLocal() {
        return nombreAdministracionLocal;
    }

    public void setNombreAdministracionLocal(String nombreAdministracionLocal) {
        this.nombreAdministracionLocal = nombreAdministracionLocal;
    }

    /**
     * @return the cvePais
     */
    public String getCvePais() {
        return cvePais;
    }

    /**
     * @param cvePais
     *            the cvePais to set
     */
    public void setCvePais(String cvePais) {
        this.cvePais = cvePais;
    }

    /**
     * @return the nombrePais
     */
    public String getNombrePais() {
        return nombrePais;
    }

    /**
     * @param nombrePais
     *            the nombrePais to set
     */
    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

    /**
     * @return the cveEstado
     */
    public String getCveEstado() {
        return cveEstado;
    }

    /**
     * @param cveEstado
     *            the cveEstado to set
     */
    public void setCveEstado(String cveEstado) {
        this.cveEstado = cveEstado;
    }

    /**
     * @return the cveDelegacion
     */
    public String getCveDelegacion() {
        return cveDelegacion;
    }

    /**
     * @param cveDelegacion
     *            the cveDelegacion to set
     */
    public void setCveDelegacion(String cveDelegacion) {
        this.cveDelegacion = cveDelegacion;
    }

    /**
     * @return the cveColonia
     */
    public String getCveColonia() {
        return cveColonia;
    }

    /**
     * @param cveColonia
     *            the cveColonia to set
     */
    public void setCveColonia(String cveColonia) {
        this.cveColonia = cveColonia;
    }

    /**
     * @return the cveLocalidad
     */
    public String getCveLocalidad() {
        return cveLocalidad;
    }

    /**
     * @param cveLocalidad
     *            the cveLocalidad to set
     */
    public void setCveLocalidad(String cveLocalidad) {
        this.cveLocalidad = cveLocalidad;
    }

}
