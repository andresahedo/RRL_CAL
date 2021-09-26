package mx.gob.sat.siat.juridica.buzon.service;

import mx.gob.sat.siat.juridica.buzon.exception.BuzonNoDisponibleException;
import mx.gob.sat.siat.juridica.buzon.exception.CorreoNoRegistradoException;

public interface BuzonTributarioService {

    String obtenerCorreos(String rfc) throws BuzonNoDisponibleException, CorreoNoRegistradoException;

    String obtenerNotificaciones(String info);

}
