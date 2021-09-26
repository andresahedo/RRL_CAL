package mx.gob.sat.siat.juridica.configuracion.usuarios.util.constants;

import java.io.Serializable;

public enum ExcepcionesEnum implements Serializable {

    EX_MOV_INDICADOR("EXMVTO.UCTMIE", "Usuario con combinaci&oacute;n de tipo de movimiento e indicador err&oacute;nea"),
    EX_SIN_ROL_OBLIGATORIO("EXMVTO.UTMSPO", "Usuario con tipo de movimiento sin permiso (rol) obligatorio"),
    EX_ROL_NO_RECONOCIDO("EXMVTO.UPNRS", "Usuario con permiso (rol) no reconocido en SIAT-Jur&iacute;dico"),
    EX_USUARIO_NO_ACTIVO_REGISTRADO("EXMVTO.UNRS", "Usuario no registrado o no activo en SIAT-Jur&iacute;dico"),
    EX_ROL_DUPLICADO("EXMVTO.UPD", "Usuario con permiso (rol) duplicado."), EX_TAREAS_PENDIENTES("EXMVTO.UTP",
            "Usuario con tareas pendientes"), EX_INFO_NO_PERMITIDA("EXMVTO.IUMNP",
            "Informaci&oacute;n del usuario modificada no permitida"), EX_ROL_NO_ASOCIADO("EXMVTO.UNASJ",
            "Usuario con permiso (rol) no asociado en SIAT-Jur&iacute;dico"), EX_USUARIO_REGISTRADO("EXMVTO.UAERA",
            "Usuario con tipo de movimiento 'Alta de Empleado' registrado anteriormente en SIAT-Jur&iacute;dico"),
    EX_DETERMINAR_TAREAS("EXMVTO.NDTP", "No fue posible determinar si el usuario tiene tareas pendientes");

    private String clave;
    private String descripcion;

    private ExcepcionesEnum(String clave, String descripcion) {
        this.clave = clave;
        this.descripcion = descripcion;
    }

    public static ExcepcionesEnum getExcepcion(String clave) {
        ExcepcionesEnum excepcion = null;
        for (ExcepcionesEnum item : ExcepcionesEnum.values()) {
            if (item.getClave().equals(clave)) {
                excepcion = item;
            }
        }
        return excepcion;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
