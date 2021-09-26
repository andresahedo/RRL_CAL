package mx.gob.sat.siat.juridica.base.service;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.*;

import java.io.Serializable;

public interface DomicilioServices extends Serializable {
    EntidadFederativa buscarEntidadByClave(String clave);

    Colonia buscarColoniaByClave(String clave);

    Pais buscarPaisByClave(String clave);

    DelegacionMunicipio buscarDelegacionByClave(String clave);
    
    Localidad buscarLocalidadByClave(String clave);
}
