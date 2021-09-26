package mx.gob.sat.siat.juridica.configuracion.usuarios.service.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.model.BitacoraAU;
import mx.gob.sat.siat.juridica.base.service.impl.BaseBusinessServices;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.BitacoraAUDAO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.service.BitacorAUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BitacorAUServiceImpl extends BaseBusinessServices implements BitacorAUService {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 6604925770505839356L;

    /**
     * Atributo tipo DAO
     */
    @Autowired
    private BitacoraAUDAO bitacoraAUDAO;

    @Override
    public List<BitacoraAU> obtenerDatosBitacora(BitacoraAU modelBitacora, Date fechaInicial, Date fechaFinal) {
        return bitacoraAUDAO.obtenerDatosBitacora(modelBitacora, fechaInicial, fechaFinal);
    }

    @Override
    public void guardarDatosBitacora(BitacoraAU bitacora) {
        bitacoraAUDAO.guardarDatosBitacora(bitacora);
    }

}
