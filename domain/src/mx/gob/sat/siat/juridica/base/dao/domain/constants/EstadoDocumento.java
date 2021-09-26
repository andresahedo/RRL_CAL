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
public enum EstadoDocumento {

    /**
     * Estados de un documento
     */
    ANEXADO("ESTDOC.AN", "Anexado"), FIRMADO("ESTDOC.FIR", "Firmado"), ELIMINADO("ESTDOC.ELI", "Eliminado"),
    POR_APROBAR("ESTDOCS.PA", "Por Aprobar"), RECHAZADO("ESTDOCS.RZ", "Rechazado"), APROVADO("ESTDOCS.AP", "Aprobado"),
    GENERADO("ESTDOCS.GN", "Generado"),EXCLUSIVO_FONDO("ESTDOCS.EF","Exclusivo Fonfo");

    /**
     * Atributo privado "clave" tipo String
     */
    private String clave;

    /**
     * Atributo privado "clave" tipo String
     */
    private String descripcion;

    /**
     * Constructor
     * 
     * @param clave
     *            a fijar
     * @param descripcion
     *            a fijar
     */
    private EstadoDocumento(final String clave, final String descripcion) {
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
     * Metodo que parsea el estado del documento
     * 
     * @param clave
     * @return right
     */
    public static EstadoDocumento parse(String clave) {
        EstadoDocumento right = null; 
        // Default

        for (EstadoDocumento item : EstadoDocumento.values()) {
            if (item.getClave().equals(clave)) {
                right = item;
                break;
            }
        }

        return right;
    }
}
