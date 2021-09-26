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
public enum EnumeracionBitacora {

    /**
     * Enumeracion de tipo de TAREA
     */
    TURNAR_ASUNTO("TITAR.TAS", "Turnar asunto"),
    ATENDER_ASUNTO("TITAR.ATR", "Atender asunto"),
    FIRMAR_REQUERIMIENTO("TITAR.ERQ", "Emitir y firmar requerimiento"),
    FIRMAR_REMISION("TITAR.ERM", "Emitir y firmar remisi\u00F3n"),
    FIRMAR_RESOLUCION("TITAR.ERS","Emitir y firmar resoluci\u00F3n"),
    NOTIFICACION_FECHA("TITAR.RFNO","Registrar fecha notificaci\u00F3n"),
    NOTIFICACION_REQUERIMIENTO("TITAR.RNOQ","Registrar notificaci\u00F3n requerimiento"),
    NOTIFICACION_RESOLUCION("TITAR.RNOR","Registrar notificaci\u00F3n resoluci\u00F3n"),
    ATENDER_REQ("TITAR.ARE","Atender requerimiento"),
    /**
     * Enumeracion de tipo de BITACORA
     */
    EN_ESTUDIO("TITAR.EE", "En Estudio"),
    REGISTRO("TITAR.RPR", "Registrar Promoci\u00F3n de requerimiento"),
    REQUERIMIENTO("TITAR.RIN", "Requerir Informaci\u00F3n"),
    REASIGNAR("TITAR.RAS", "Reasignar Asunto"),
    GENERAR_RESOL("TITAR.GRS", "Generar Resoluci\u00F3n"),
    NOTIFICACION("TITAR.RNOT","Registrar Notificaci\u00F3n"),
    CONFIRMAR("TITAR.CON", "Confirmar"),
    ATENDER_OBSERVACION("TITAR.ATO","Atender observaci\u00F3n"),
    CONFIRMAR_NOTIFICACION_REQ("TITAR.CONR","Confirmar notificaci\u00F3n de requerimiento"),
    ATENDER_REQ_AUTORIDAD("TITAR.ARA","Atender requerimiento por autoridad");
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
    private EnumeracionBitacora(final String clave, final String descripcion) {
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
     * Metodo para parsear tipo EnumeracionParamtro
     * 
     * @param clave
     * @return right
     */
    public static EnumeracionBitacora parse(String clave) {
        EnumeracionBitacora right = null;
        // Default

        for (EnumeracionBitacora item : EnumeracionBitacora.values()) {
            if (item.getClave().equals(clave)) {
                right = item;
                break;
            }
        }

        return right;
    }
}
