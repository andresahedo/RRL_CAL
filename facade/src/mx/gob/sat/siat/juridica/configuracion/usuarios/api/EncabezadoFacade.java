package mx.gob.sat.siat.juridica.configuracion.usuarios.api;

import mx.gob.sat.siat.juridica.base.facade.BaseFacade;

public interface EncabezadoFacade extends BaseFacade {

    String obtenerNombre(String rfc);

}
