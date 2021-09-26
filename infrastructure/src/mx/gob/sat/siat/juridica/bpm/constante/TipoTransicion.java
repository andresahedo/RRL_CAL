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
 * Enumerador que representa la lista de los tipos de transicion
 * 
 * @author softtek
 * 
 */
public enum TipoTransicion {

    PRINCIPAL("EST_TRANS_PRL"), TIMMER("EST_TRANS_TIM"), ALTERNATIVA1("EST_TRANS_AL1"), ALTERNATIVA2("EST_TRANS_AL2"),
    ALTERNATIVA3("EST_TRANS_AL3");

    /**
     * Identificador de la transicion
     * 
     * @param id
     */
    private TipoTransicion(String id) {
        this.id = id;
    }

    /**
     * Identificador
     */
    private String id;

    /**
     * Metodo para obtener el identificador
     * 
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Metodo que busca y devuelve el tipo de transicion
     * 
     * @param id
     * @return
     */
    public static TipoTransicion find(String id) {
        for (TipoTransicion tarea : TipoTransicion.values()) {
            if (tarea.getId().equals(id)) {
                return tarea;
            }
        }
        return null;
    }

}
