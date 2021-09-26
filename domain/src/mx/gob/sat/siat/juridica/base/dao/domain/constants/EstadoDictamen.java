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
 * ESTDI.RG Registrado ESTDI.GN Generado ESTDI.AT Autorizado ESTDI.VR
 * Verificado ESTDI.EL Eliminado
 * 
 * @author softtek
 */
public enum EstadoDictamen {
    /**
     * Estados NO AGREGAR MAS ESTADOS
     */
    REGISTRADO("ESTDI.RG", "Registrado"), GENERADO("ESTDI.GN", "Generado"), AUTORIZADO("ESTDI.AU", "Autorizado"),
    VERIFICADO("ESTDI.VR", "Verificado"), ELIMINADO("ESTDI.EL", "Eliminado");

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
    private EstadoDictamen(final String clave, final String descripcion) {
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
     * Metodo que parsea el estado de dictamen
     * 
     * @param clave
     * @return right
     */
    public static EstadoDictamen parse(String clave) {
        EstadoDictamen right = null; 
        // Default
        for (EstadoDictamen item : EstadoDictamen.values()) {
            if (item.getClave().equals(clave)) {
                right = item;
                break;
            }
        }
        return right;
    }

}
