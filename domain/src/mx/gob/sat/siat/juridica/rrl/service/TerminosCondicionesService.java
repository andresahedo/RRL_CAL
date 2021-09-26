package mx.gob.sat.siat.juridica.rrl.service;

import mx.gob.sat.siat.juridica.base.dao.domain.model.TerminosCondiciones;

import java.io.Serializable;

public interface TerminosCondicionesService extends Serializable {

    TerminosCondiciones obtenerTerminos(int numPantalla);

}
