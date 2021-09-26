/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.web.util.validator;

import mx.gob.sat.siat.juridica.base.dto.UnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.base.web.util.validador.BaseValidator;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.UsuarioRolUnidadAdministrativaDTO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@ManagedBean(name = "permisosValidator")
@NoneScoped
public class PermisosValidator extends BaseValidator {

    /**
     * 
     */
    private static final long serialVersionUID = 8102784348552988194L;

    /**
     * Metodo para validar si no se encuentra asignada una unidad
     * administrativa.
     * 
     * @param permisos
     * @param unidadSeleccionada
     * @return
     */
    public boolean asignarPermiso(List<UsuarioRolUnidadAdministrativaDTO> permisos,
            UnidadAdministrativaDTO unidadSeleccionada) {
        boolean resp = false;
        if (permisos != null) {

            for (int i = 0; i < permisos.size() && !resp; i++) {
                if (permisos.get(i).getUnidadAdministrativa().getClave().equals(unidadSeleccionada.getClave())) {
                    resp = true;
                }
                else {
                    resp = false;
                }
            }

        }
        return resp;

    }

}
