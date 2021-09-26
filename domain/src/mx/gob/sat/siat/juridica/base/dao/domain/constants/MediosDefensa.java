/*
 *  Todos los Derechos Reservados © 2013 SAT
 *  Servicio de Administracion Tributaria (SAT).
 *  
 *  Este software contiene informacion propiedad exclusiva del SAT considerada
 *  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 *  parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.constants;

/**
 * 
 * @author softtek
 * 
 */
public enum MediosDefensa {

    /**
     * Estados de Medios de Defensa
     */
    JUICIO("TIMD.JC", "Juicio"), AMPARO("TIMD.AM", "Amparo"), RECURSO_REVOCACION("TIMD.RR",
            "Recurso de Revocaci\u00F3n"), CONSULTA_AUTORIZACION("TIMD.CA", "Consulta o Autorizaci\u00F3n"),
    OTRO("TIMD.OTR", "Otro");

    /**
     * Atributo privado "clave" tipo String
     */
    private String clave;

    /**
     * Atributo privado "descripcion" tipo String
     */
    private String descripcion;

    /**
     * Constructor
     * 
     * @param clave
     * @param descripcion
     */
    private MediosDefensa(final String clave, final String descripcion) {
        this.clave = clave;
        this.descripcion = descripcion;
    }

    /**
     * 
     * @return clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * 
     * @param clave
     *            a fijar
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * 
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * 
     * @param descripcion
     *            a fijar
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Metodo para parsear los Medios de defensa
     * 
     * @param clave
     * @return right
     */
    public static MediosDefensa parse(String clave) {
        MediosDefensa right = null;
        for (MediosDefensa item : MediosDefensa.values()) {
            if (item.getClave().equals(clave)) {
                right = item;
                break;
            }
        }
        return right;
    }

}
