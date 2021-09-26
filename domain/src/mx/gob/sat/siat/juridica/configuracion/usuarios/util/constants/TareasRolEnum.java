package mx.gob.sat.siat.juridica.configuracion.usuarios.util.constants;

import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoRol;

import java.io.Serializable;

public enum TareasRolEnum implements Serializable {

    TURNAR_ASUNTO("Turnar asunto", TipoRol.ADMINISTRADOR.getClave()),
    AUTORIZAR_REMISION("Emitir y firmar remisi\u00F3n", TipoRol.ADMINISTRADOR.getClave()),
    AUTORIZAR_RESOLUCION("Emitir y firmar resoluci\u00F3n", TipoRol.ADMINISTRADOR.getClave()),
    AUTORIZAR_REQUERIMIENTO("Emitir y firmar requerimiento", TipoRol.ADMINISTRADOR.getClave()),
    ATENDER_ASUNTO("Atender asunto", TipoRol.ABOGADO.getClave()),
    REG_NOTIFICACION_RESOLUCION("Registrar notificaci\u00F3n resoluci\u00F3n", TipoRol.ABOGADO.getClave()),
    REG_NOTIFICACION_REQUERIMIENTO("Registrar notificaci\u00F3n requerimiento", TipoRol.ABOGADO.getClave()),
    ATENDER_OBSERVACION("Atender observaci\u00F3n", TipoRol.OFICIAL_PARTES.getClave());

    private String rol;
    private String tarea;

    private TareasRolEnum(String tarea, String rol) {
        this.tarea = tarea;
        this.rol = rol;

    }

    public static TareasRolEnum obtenerRolPorTarea(String tarea) {
        TareasRolEnum rol = null;
        for (TareasRolEnum tareasRoles : TareasRolEnum.values()) {
            if (tareasRoles.getTarea().equals(tarea)) {
                rol = tareasRoles;
            }
        }
        return rol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

}
