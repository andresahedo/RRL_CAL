/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.bpm.constante;

/**
 * Enumerador que reepresenta la lista de participantes
 * 
 * @author softtek
 * 
 */
public enum Participantes {

    SISTEMA(1L, "Sistema"), CONTRIBUYENTE(2L, "Contribuyente"), DICTAMINADOR(3L, "Dictaminador"), AUTORIZADOR(4L,
            "Autorizador");

    /**
     * Clave del participante
     */
    private Long clave;
    /**
     * Descripcion del participante
     */
    private String descripcion;

    /**
     * Constructor del enumerador
     * 
     * @param clave
     * @param descripcion
     */
    private Participantes(final Long clave, final String descripcion) {
        this.clave = clave;
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la clave del participante
     * 
     * @return
     */
    public Long getClave() {
        return clave;
    }

    /**
     * Obtiene la descripcion del participante
     * 
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Metodo que busca y devuelve el tipo de participante
     * 
     * @param clave
     * @return right
     */
 // Default
    public static Participantes parse(Long clave) {
        Participantes right = null; 
        for (Participantes item : Participantes.values()) {
            if (item.getClave().equals(clave)) {
                right = item;
                break;
            }
        }
        return right;
    }

}
