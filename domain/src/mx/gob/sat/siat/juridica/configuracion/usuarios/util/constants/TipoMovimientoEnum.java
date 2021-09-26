package mx.gob.sat.siat.juridica.configuracion.usuarios.util.constants;

import java.io.Serializable;

public enum TipoMovimientoEnum implements Serializable {

    ALTA_EMPLEADO("ETNVL.AE", "Alta de Empleado"), ALTA_ROL("ETNVL.AR", "Alta de Rol"), BAJA_ROL("ETNVL.BR",
            "Baja de Rol al Empleado"), BAJA_TEMPORAL("ETNVL.BT", "Baja temporal del Empleado"),
    BAJA_DEFINITIVA("ETNVL.BD", "Baja definitiva del Empleado"), MODIFICACION("ETNVL.MD",
            "Modificaci&oacute;n datos del empleado"), ERROR("ERROR", "Movimiento no valido");

    private String clave;
    private String descripcion;

    private TipoMovimientoEnum(String clave, String descripcion) {
        this.clave = clave;
        this.descripcion = descripcion;
    }

    public static TipoMovimientoEnum parse(String clave) {
        TipoMovimientoEnum tmEnum = null;
        for (TipoMovimientoEnum item : TipoMovimientoEnum.values()) {
            if (item.getClave().equals(clave)) {
                tmEnum = item;
            }
        }
        return tmEnum;
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
