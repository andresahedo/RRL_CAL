package mx.gob.sat.siat.juridica.base.dao.domain.model;

public class ResultadoAdminResponsable {

    private boolean administradorDefault;

    private String rfcAdministrador;

    private String unidadAdmin;

    /**
     * @return the administradorDefault
     */
    public boolean isAdministradorDefault() {
        return administradorDefault;
    }

    /**
     * @param administradorDefault
     *            the administradorDefault to set
     */
    public void setAdministradorDefault(boolean administradorDefault) {
        this.administradorDefault = administradorDefault;
    }

    /**
     * @return the rfcAdministrador
     */
    public String getRfcAdministrador() {
        return rfcAdministrador;
    }

    /**
     * @param rfcAdministrador
     *            the rfcAdministrador to set
     */
    public void setRfcAdministrador(String rfcAdministrador) {
        this.rfcAdministrador = rfcAdministrador;
    }

    /**
     * @return the unidadAdmin
     */
    public String getUnidadAdmin() {
        return unidadAdmin;
    }

    /**
     * @param unidadAdmin
     *            the unidadAdmin to set
     */
    public void setUnidadAdmin(String unidadAdmin) {
        this.unidadAdmin = unidadAdmin;
    }

}
