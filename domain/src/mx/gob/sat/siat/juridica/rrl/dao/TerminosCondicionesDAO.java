package mx.gob.sat.siat.juridica.rrl.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.model.TerminosCondiciones;

import java.io.Serializable;

public interface TerminosCondicionesDAO extends Serializable {

    TerminosCondiciones obtenerTerminosCondiciones(int numPantalla);

}
