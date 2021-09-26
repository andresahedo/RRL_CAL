/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.util.constante;

/**
 * Enumeracion apra los posibles sentidos de una resolu&oacute;n
 * impugnada.
 * 
 * @author Softtek
 * 
 */
public enum SentidoResolucion {

    ACEPTADA("Aceptada", "SERE.AC"), RECHAZADA("Rechazada", "SERE.RZ");

    /**
     * Nombre del sentido.
     */
    private String nombre;

    /**
     * Clave del sentido.
     */
    private String clave;

    /**
     * Constructtor que recibe el nombre y la clave para un sentido de
     * resolucion impuganada.
     * 
     * @param nombre
     *            nombre del sentido.
     * @param clave
     *            clave del sentido.
     */
    private SentidoResolucion(String nombre, String clave) {
        this.nombre = nombre;
        this.clave = clave;
    }

    /**
     * @return el(la) nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre
     *            el(la) nombre a fijar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return el(la) clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * @param clave
     *            el(la) clave a fijar
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    public static SentidoResolucion parse(String clave) {
        SentidoResolucion right = null;
        for (SentidoResolucion item : SentidoResolucion.values()) {
            if (item.getClave().equals(clave)) {
                right = item;
                break;
            }
        }
        return right;
    }
}
