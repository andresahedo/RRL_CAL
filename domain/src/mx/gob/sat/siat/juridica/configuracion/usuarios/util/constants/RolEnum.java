package mx.gob.sat.siat.juridica.configuracion.usuarios.util.constants;

import java.io.Serializable;

public enum RolEnum implements Serializable {

    ABOGADO("abogado", "Abogado"), ADMINISTRADOR("administrador", "Administrador"), OFICIAL_PARTES("OficialDePartes",
            "Oficial de partes"), ADMINISTRADOR_UNIDAD_ADMINISTRATIVA("AdministradorUA",
            "Administrador Unidad Administrativa"), ADMINISTRADOR_GLOBAL("AdministradorGlobal", "Administrador Global");

    private String clave;
    private String descripcion;

    private RolEnum(String clave, String descripcion) {
        this.clave = clave;
        this.descripcion = descripcion;
    }

    public static RolEnum obtenerRolPorClave(String clave) {
        RolEnum rol = null;
        for (RolEnum item : RolEnum.values()) {
            if (item.clave.equals(clave)) {
                rol = item;
            }
        }
        return rol;
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
