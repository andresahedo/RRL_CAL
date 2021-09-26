package mx.gob.sat.siat.juridica.configuracion.usuarios.dao;

import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.domain.model.Movimiento;

import java.util.List;

public interface MovimientosDao {

    List<Movimiento> obtenerMovtosNoNotificados();

    void modificarMovimiento(Movimiento movimiento);

}
