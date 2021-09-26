package mx.gob.sat.siat.juridica.configuracion.usuarios.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.model.BitacoraAU;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author softtek since 02/10/2014
 */
public interface BitacoraAUDAO {

    List<BitacoraAU> obtenerDatosBitacora(BitacoraAU modelBitacora, Date fechaInicial, Date fechaFinal);

    void guardarDatosBitacora(BitacoraAU bitacora);

    BitacoraAU insertarMovimientoBitacora(BitacoraAU bitacora);

}
