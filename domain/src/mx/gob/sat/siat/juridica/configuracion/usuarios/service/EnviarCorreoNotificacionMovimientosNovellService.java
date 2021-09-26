package mx.gob.sat.siat.juridica.configuracion.usuarios.service;

import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.domain.model.DatosCorreoNotificacion;

import java.io.Serializable;

public interface EnviarCorreoNotificacionMovimientosNovellService extends Serializable {

    void enviarCorreoNotificacionActualizarMovimiento(DatosCorreoNotificacion datosCorreo);

}
