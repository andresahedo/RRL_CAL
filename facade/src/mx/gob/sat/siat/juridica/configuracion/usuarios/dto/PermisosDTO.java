/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.dto;

import mx.gob.sat.siat.juridica.base.dto.BaseDataTransferObject;

/**
 * Clase Controller para la administracion de empleados
 * 
 * @author Softtek - EQG
 * @since 31/10/2014
 */
public class PermisosDTO extends BaseDataTransferObject {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 5253226185641889347L;

    private Long idPermiso;
    private String permiso;
    private boolean permisoActivo;
    private boolean permisoReplicar;

    public String getPermiso() {
        return permiso;
    }

    public void setPermiso(String permiso) {
        this.permiso = permiso;
    }

    public Boolean getPermisoActivo() {
        return permisoActivo;
    }

    public void setPermisoActivo(Boolean permisoActivo) {
        this.permisoActivo = permisoActivo;
    }

    public Boolean getPermisoReplicar() {
        return permisoReplicar;
    }

    public void setPermisoReplicar(Boolean permisoReplicar) {
        this.permisoReplicar = permisoReplicar;
    }

    public void setPermisoActivo(boolean permisoActivo) {
        this.permisoActivo = permisoActivo;
    }

    public void setPermisoReplicar(boolean permisoReplicar) {
        this.permisoReplicar = permisoReplicar;
    }

    public Long getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(Long idPermiso) {
        this.idPermiso = idPermiso;
    }

}
