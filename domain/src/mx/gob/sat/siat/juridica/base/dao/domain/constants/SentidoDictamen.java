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
public enum SentidoDictamen {

    /**
     * Estados del Sentido de Dictamen
     */
    ACEPTADA("SEDI.AC", "Aceptado"), RECHAZADA("SEDI.RZ", "Rechazado"), PARCIAL("SEDI.PA", "Parcial"),
    NEGATIVA_FICTA("SEDI.NF", "Negativa Ficta");

    /**
     * Atributo privado "clave" tipo String
     */
    private final String clave;

    /**
     * Atributo privado "descripcion" tipo String
     */
    private final String descripcion;

    /**
     * Constructor
     * 
     * @param clave
     *            a fijar
     * @param descripcion
     *            a fijar
     */
    private SentidoDictamen(final String clave, final String descripcion) {
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
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Metodo para parsear SentidoDictamen
     * 
     * @param clave
     * @return right
     */
    public static SentidoDictamen parse(String clave) {
        SentidoDictamen right = null; 
        // Default
        for (SentidoDictamen item : SentidoDictamen.values()) {
            if (item.getClave().equals(clave)) {
                right = item;
                break;
            }
        }
        return right;
    }

}
