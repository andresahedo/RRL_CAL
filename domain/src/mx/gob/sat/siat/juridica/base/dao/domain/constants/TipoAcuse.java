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
public enum TipoAcuse {
    /**
     * Estados de un documento
     */

    RECPROM("TACUSE.RP", "Recepcion de Promocion"), ATREQ("TACUSE.AR", "Recepcion de Atencion al Requerimiento"),
    RDOCAD("TACUSE.RDA", "Recepcion de Documentacion Adicional");

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
    private TipoAcuse(final String clave, final String descripcion) {
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
    public static TipoAcuse parse(String clave) {
        TipoAcuse right = null;
        // Default

        for (TipoAcuse item : TipoAcuse.values()) {
            if (item.getClave().equals(clave)) {
                right = item;
                break;
            }
        }

        return right;
    }
}
