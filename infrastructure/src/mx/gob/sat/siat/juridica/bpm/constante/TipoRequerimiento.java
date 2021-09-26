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
 * Enumerador que represanta la lista de los tipos de requerimientos
 * 
 * @author softtek
 * 
 */
public enum TipoRequerimiento {

    CONTRIBUYENTE("REQ_CON"), AUTORIDAD("REA_AUT");

    /**
     * Constructor del enumerador
     * 
     * @param id
     */
    private TipoRequerimiento(String id) {
        this.id = id;
    }

    /**
     * Identificador del tipo de requerimiento
     */
    private String id;

    /**
     * Obtiene el identificador
     * 
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Metodo que busca y devuelve el tipo de requerimiento
     * 
     * @param id
     * @return tarea
     */
    public static TipoRequerimiento find(String id) {
        for (TipoRequerimiento tarea : TipoRequerimiento.values()) {
            if (tarea.getId().equals(id)) {
                return tarea;
            }
        }
        return null;
    }

}
