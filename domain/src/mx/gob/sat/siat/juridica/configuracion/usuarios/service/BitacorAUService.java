package mx.gob.sat.siat.juridica.configuracion.usuarios.service;

import mx.gob.sat.siat.juridica.base.dao.domain.model.BitacoraAU;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface BitacorAUService extends Serializable {

    List<BitacoraAU> obtenerDatosBitacora(BitacoraAU modelBitacora, Date fechaInicial, Date fechaFinal);

    void guardarDatosBitacora(BitacoraAU bitacora);

}
