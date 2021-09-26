package mx.gob.sat.siat.juridica.base.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.model.UsuarioSincronizar;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface UsuarioSincronizarDAO extends Serializable {

    UsuarioSincronizar obtenerUsuarioSincronizar(String rfcCorto);

    void guardarUsuarioSincronizar(UsuarioSincronizar usuarioSincronizar);

    void actualizarUsuarioSincronizar(UsuarioSincronizar usuarioSincronizar);

    List<UsuarioSincronizar> obtenerUsuariosSincronizarPorFecha(Date date);

    List<UsuarioSincronizar> obtenerUsuariosSincronizarNoActivos();

}
