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
public enum TipoProcesoFirma {
    /**
     * Estados de un documento
     */

    REG_PROM("TIPPRO.RGS", "Registrar promocion"), ADJ_DOC_ADI("TIPPRO.ADJ", "Adjutar documentos adicionales"),
    AT_REQ_SOL("TIPPRO.REQSL", "Atender requerimiento Solicitante"), AT_REQ_AUT("TIPPRO.REQAT",
            "Atender requerimiento Autoridad"), AUT_REQ("TIPPRO.AUTREQ", "Autorizar requerimiento");

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
    private TipoProcesoFirma(final String clave, final String descripcion) {
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
    public static TipoProcesoFirma parse(String clave) {
        TipoProcesoFirma right = null; 
        // Default

        for (TipoProcesoFirma item : TipoProcesoFirma.values()) {
            if (item.getClave().equals(clave)) {
                right = item;
                break;
            }
        }

        return right;
    }
}
