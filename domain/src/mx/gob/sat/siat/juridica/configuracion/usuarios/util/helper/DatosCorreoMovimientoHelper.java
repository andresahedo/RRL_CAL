package mx.gob.sat.siat.juridica.configuracion.usuarios.util.helper;

import mx.gob.sat.siat.juridica.base.dao.domain.constants.ActualizarMovimientosConstants;
import mx.gob.sat.siat.juridica.base.helper.BaseHelper;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.domain.model.DatosCorreoNotificacion;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.domain.model.Movimiento;
import mx.gob.sat.siat.juridica.configuracion.usuarios.util.constants.ExcepcionesEnum;
import mx.gob.sat.siat.juridica.configuracion.usuarios.util.constants.TipoMovimientoEnum;
import org.springframework.stereotype.Component;

@Component
public class DatosCorreoMovimientoHelper extends BaseHelper {

    /**
     * 
     */
    private static final long serialVersionUID = 4080510364827982939L;

    public DatosCorreoNotificacion llenarObjetoCorreo(Movimiento movimiento) {
        DatosCorreoNotificacion datos = new DatosCorreoNotificacion();
        datos.setNombre(movimiento.getNombre());
        datos.setApPaterno(movimiento.getPaterno());
        datos.setApMaterno(movimiento.getMaterno());
        datos.setNumeroEmpleado(movimiento.getNumEmpleado());
        datos.setMail(movimiento.getCorreo());
        datos.setRfc(movimiento.getRfc());
        datos.setRfcCorto(movimiento.getRfcCorto());
        if (TipoMovimientoEnum.parse(movimiento.getIdeTipoMovto()) != null) {
            datos.setTipoMovimiento(TipoMovimientoEnum.parse(movimiento.getIdeTipoMovto()));
        }
        else {
            datos.setTipoMovimiento(TipoMovimientoEnum.ERROR);
        }
        if (movimiento.getIdeEstadoMovto().equals(ActualizarMovimientosConstants.EDO_INSERTADO)) {
            datos.setExcepcion(null);
        }
        else {
            datos.setExcepcion(ExcepcionesEnum.getExcepcion(movimiento.getIdeExMovto()));
        }
        return datos;
    }

}
