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

public enum EstadoTramite {
    /**
     * Estados de un tramita NO AGREGAR MAS ESTADOS
     */
    INICIADO("ESTTR.IN", "Iniciado"),
    DESISTIDO("ESTTR.DS", "Desistido"),
    // Estados que se encontraron en la enumeracion ENU_EST_TRAMITE
    CONCLUIDO("ESTTR.CN", "Concluido"), EN_ESTUDIO("ESTTR.EE", "En Estudio"), EN_FIRMA("ESTTR.EF", "En Firma"),
    PROMOVIDO("ESTTR.PR", "Promovido"), OBSERVADO("ESTTR.OB", "Observado"), REMITIDO("ESTTR.RM", "Remitido"),
    RESUELTO_NOTIFICADO("ESTTR.RN", "Resuelto Notificado"), REQUERIDO("ESTTR.RQ", "Requerido"), RESUELTO("ESTTR.RS",
            "Resuelto"), RECHAZADO("ESTTR.RZ", "Rechazado");

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
    private EstadoTramite(final String clave, final String descripcion) {
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
     * Metodo que parsea el estado de un tramite
     * 
     * @param clave
     * @return rigth
     */
    public static EstadoTramite parse(String clave) {
        EstadoTramite right = null;
        // Default

        for (EstadoTramite item : EstadoTramite.values()) {
            if (item.getClave().equals(clave)) {
                right = item;
                break;
            }
        }

        return right;
    }

}
