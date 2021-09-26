package mx.gob.sat.siat.juridica.base.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.*;

public interface DomicilioDAO {

    EntidadFederativa buscarEntidadByClave(String clave);

    Colonia buscarColoniaByClave(String clave);

    Pais buscarPaisByClave(String clave);

    DelegacionMunicipio buscarDelegacionByClave(String clave);
    
    Localidad buscarLocalidadByClave(String clave);
    
    EntidadFederativa buscarEntidadByClaveIDC(String claveIDC);

    Colonia buscarColoniaByClaveIDC(String claveIDC);

    
    Localidad buscarLocalidadByClaveIDC(String claveIDC);

    DelegacionMunicipio buscarDelegacionByClaveIDC(String claveIDC,
            String claveEntidad);
}
