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
 * @author softtek - EQG
 * @since 05/12/2014
 */
public enum AccionesBitacora {

    /**
     * Acciones de la bitacora
     */

    ALTA_EMPLEADO("EACBT.AE", "Alta de Empleado "), BAJA_DEFINITIVA_EMPLEADO("EACBT.BDE",
            "Baja definitiva  del Empleado"), MODIFICACION_DATOS_EMPLEADO("EACBT.MDE",
            "Modificaci\u00F3n Datos del Empleado"), ASIGNAR_PERMISO_ADMINISTRADOR_UA("EACBT.APAUA",
            "Asignar permiso administrador UA"), REVOCAR_PERMISO_ADMINISTRADOR_UA("EACBT.RPAUA",
            "Revocar permiso administrador UA"), ASIGNAR_PERMISO_TRAMITE("EACBT.APT", "Asignar permiso tr\u00E1mite"),
    REVOCAR_PERMISO_TRAMITE("EACBT.RPT", "Revocar permiso tr\u00E1mite"), REASIGNAR_TAREAS("EACBT.RT",
            "Reasignar tareas"), ASIGNAR_PERMISO_ADMINISTRADOR_GLOBAL("EACBT.APAG",
            "Asignar permiso administrador Global"), REVOCAR_PERMISO_ADMINISTRADOR_GLOBAL("EACBT.RPAG",
            "Revocar permiso administrador Global"), ASIGNAR_PERMISO_EMPLEADO("EACBT.APE", "Asignar permiso Empleado"),
    ACTUALIZAR_TRAMITES_UA("EACBT.ATUA", "Actualizar Tr\u00E1mites a la Unidad Administrativa"),
    REVOCAR_PERMISO_EMPLEADO("EACBT.RPE", "Revocar permiso Empleado"), BAJA_TEMPORAL_EMPLEADO("EACBT.BTE",
            "Baja temporal del Empleado"), BAJA_ROL_EMPLEADO("EACBT.BRE", "Baja de Rol al Empleado"),
    ALTA_ROL("EACBT.AR", "Alta de Rol"),ERROR_ACUSES("ERROR.ACU","Error al generar acuses"), ERROR_TAREA("ERROR.TAR","Error al crear tarea");

    /**
     * Atributo privado clave" tipo String
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
    private AccionesBitacora(final String clave, final String descripcion) {
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
     * Metodo que parsea el AccionesBitacora
     * 
     * @param clave
     * @return right
     */
    public static AccionesBitacora parse(String clave) {
        AccionesBitacora right = null;
        for (AccionesBitacora item : AccionesBitacora.values()) {
            if (item.getClave().equals(clave)) {
                right = item;
                break;
            }
        }
        return right;
    }

}
