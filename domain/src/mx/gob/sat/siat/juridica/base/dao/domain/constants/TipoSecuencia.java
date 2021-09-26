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
 */
public enum TipoSecuencia {

    /**
     * Estados de un tipo de secuencia
     */
    OFICIO("TISEC.OFI", "Oficio"), SOLICITUD("TISEC.SOL", "Solicitud"), AUTORIDAD("TISEC.AUT", "Autoridad"),
    FRACCION_DUDA("TISEC.FRA", "FraccionAutoridad");

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
    private TipoSecuencia(final String clave, final String descripcion) {
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
     * Metodo que parsea un Tipo de secuencia
     * 
     * @param clave
     * @return
     */
    public static TipoSecuencia parse(String clave) {
        TipoSecuencia right = null;

        for (TipoSecuencia item : TipoSecuencia.values()) {
            if (item.getClave().equals(clave)) {
                right = item;
                break;
            }
        }

        return right;
    }
}
