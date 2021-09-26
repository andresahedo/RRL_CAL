/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service.impl;

import mx.gob.sat.siat.juridica.base.dao.UnidadAdministrativaDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.model.ResultadoAdminResponsable;
import mx.gob.sat.siat.juridica.base.service.UnidadAdministrativaServices;
import mx.gob.sat.siat.juridica.rrl.dao.BalanceadorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Service
public class UnidadAdministrativaServicesImpl implements UnidadAdministrativaServices {

    /**
     * 
     */
    private static final long serialVersionUID = -1470377930302018419L;
    /**
     * Atributo privado "unidadAdministrativaDao" tipo
     * UnidadAdministrativaDao
     */
    @Autowired
    private UnidadAdministrativaDao unidadAdministrativaDao;

    @Autowired
    private BalanceadorDAO balanceadorDAO;
    
    /**
     * Metodo para obtener un catalogo de unidades administrativas
     * 
     * @return Lista de unidades administrativas
     */
    @Override
    public List<UnidadAdministrativa> obtenerCatalogo() {

        return unidadAdministrativaDao.obtenerUnidAdmvasTodas();
    }
    
    @Override
    public List<UnidadAdministrativa> obtenerCatalogoVig() {
        return unidadAdministrativaDao.obtenerUnidAdmvasVigentes();
    }

    /**
     * Metodo para obtener una unidad administrativa
     * 
     * @return unidad administrativa
     */
    @Override
    public UnidadAdministrativa obtenerUnidadPorClaveLocal(String claveLocal) {
        // TODO Auto-generated method stub
        return unidadAdministrativaDao.obtenerUnidadPorClaveLocal(claveLocal);
    }

    @Override
    public UnidadAdministrativa obtenerUnidadPorId(String idUniAdmin) {
        return unidadAdministrativaDao.obtenerUnidadPorId(idUniAdmin);
    }

    @Override
    public String obtenerResponsableDeUnidadAdministrativa(String unidadAdmin, String modalidad) {
        ResultadoAdminResponsable result =
                balanceadorDAO.getAdministradorResponsableUnidadLocalRecaudadora(unidadAdmin, modalidad);
        if (result != null) {
            return result.getRfcAdministrador();
        }
        else {
            return null;
        }
    }

    @Override
    public String obtenerUnidadPorTipoRol(String tipoTramite, String tipoRolContribuyente) {		
        return balanceadorDAO.getUnidadPorTipoRol(tipoTramite, tipoRolContribuyente);
    }

}
