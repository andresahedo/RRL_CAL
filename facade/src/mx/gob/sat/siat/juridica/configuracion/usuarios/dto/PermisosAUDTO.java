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
 * FiltroUsuariosDTO
 * 
 * @author softtek - EQG 08/10/2014
 */
public class PermisosAUDTO extends BaseDataTransferObject {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 5398787319670470395L;

    private String permiso;

    private String idRol;

    private String permisoNovell;

    public String getPermiso() {
        return permiso;
    }

    public void setPermiso(String permiso) {
        this.permiso = permiso;
    }

    public String getIdRol() {
        return idRol;
    }

    public void setIdRol(String idRol) {
        this.idRol = idRol;
    }

    /**
     * @return the permisoNovell
     */
    public String getPermisoNovell() {
        return permisoNovell;
    }

    /**
     * @param permisoNovell
     *            the permisoNovell to set
     */
    public void setPermisoNovell(String permisoNovell) {
        this.permisoNovell = permisoNovell;
    }

}
