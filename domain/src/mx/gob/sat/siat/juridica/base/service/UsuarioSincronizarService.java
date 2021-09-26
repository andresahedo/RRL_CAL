package mx.gob.sat.siat.juridica.base.service;

import mx.gob.sat.siat.juridica.base.dao.domain.model.UsuarioSincronizar;

import java.util.Date;
import java.util.List;

public interface UsuarioSincronizarService {


    List<UsuarioSincronizar> obtenerUsuariosASincronizar();

    void setPermitirEjecucion(boolean permitirEjecucion);

    boolean getPermitirEjecucion();

    Date obtenerUltimaFechaSincronizacion();

}
