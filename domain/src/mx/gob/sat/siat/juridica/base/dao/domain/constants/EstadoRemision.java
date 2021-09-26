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
 * ESTTR.IN Iniciado ESTTR.TR Turnado ESTTR.RS Resuelto ESTTR.CN
 * Concluido
 * 
 * @author softtek
 */

public enum EstadoRemision {
    /**
     * Estados de una Remision NO AGREGAR MAS ESTADOS
     */
    SOLICITADA("ESTREM.GN", "Generada"), AUTORIZADA("ESTREM.AU", "Autorizada"), RECHAZADA("ESTREM.RZ", "Rechazada");

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
    private EstadoRemision(final String clave, final String descripcion) {
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
     * Metodo que parsea el Estado de una Remision
     * 
     * @param clave
     * @return right
     */
    public static EstadoRemision parse(String clave) {
        EstadoRemision right = null; 
        // Default

        for (EstadoRemision item : EstadoRemision.values()) {
            if (item.getClave().equals(clave)) {
                right = item;
                break;
            }
        }

        return right;
    }

}
