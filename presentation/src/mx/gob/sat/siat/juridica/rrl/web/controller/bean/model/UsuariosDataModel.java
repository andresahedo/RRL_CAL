package mx.gob.sat.siat.juridica.rrl.web.controller.bean.model;

/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */

import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.PermisosDTO;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.io.Serializable;
import java.util.List;

/**
 * Clase Controller para la administracion de empleados
 * 
 * @author Softtek - EQG
 * @since 1/11/2014
 */
public class UsuariosDataModel extends ListDataModel<PermisosDTO> implements SelectableDataModel<PermisosDTO>,
        Serializable {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -1280920121400030457L;

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public PermisosDTO getRowData(String arg0) {
        List<PermisosDTO> permisos = (List<PermisosDTO>) getWrappedData();

        for (PermisosDTO permiso : permisos) {
            if (permiso.getIdPermiso().equals(new Long(arg0))) {
                return permiso;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(PermisosDTO arg0) {
        if (arg0 != null && arg0.getIdPermiso() != null) {
            return arg0.getIdPermiso().toString();
        }
        else {
            return "";
        }
    }

    public UsuariosDataModel() {}

    public UsuariosDataModel(List<PermisosDTO> permisosList) {
        super(permisosList);
    }
}
