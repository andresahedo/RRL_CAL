/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service.impl;

import mx.gob.sat.siat.juridica.base.dao.BitacoraDao;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Bitacora;
import mx.gob.sat.siat.juridica.base.dao.domain.model.BitacoraAU;
import mx.gob.sat.siat.juridica.base.service.BitacoraTramiteServices;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.BitacoraAUDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio de bitacora
 * 
 * @author softtek
 * 
 */
@Service("bitacoraTramiteServices")
public class BitacoraTramiteServicesImpl extends BaseSerializableBusinessServices implements BitacoraTramiteServices {

    /**
     * 
     */
    private static final long serialVersionUID = -5398895554781382104L;

    @Autowired
    private BitacoraDao bitacoraDao;
    
    @Autowired
    private BitacoraAUDAO bitacoraAUDAO;

    @Override
    public void insertarBitacora(Bitacora bitacora) {

        bitacoraDao.insertarBitacora(bitacora);
    }

	@Override
	public void guardarBitacora(BitacoraAU bitacora) {
		bitacoraAUDAO.guardarDatosBitacora(bitacora);	
	}
    
}
