package mx.gob.sat.siat.juridica.rrl.api;

import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.facade.BaseFacade;

import java.util.Date;

public interface FirmarAutorizacionResolucionFacade extends BaseFacade {

    void guardarFirma(FirmaDTO firmaDTO, Long idResol);

    void guardarFirmaRemitir(FirmaDTO firmaDTO, String numAsunto, Long idTarea, String rfcUsuario);

    void guardarFirmaNotificacion(FirmaDTO firmaDTO, Long idNotificacion);

    String generaCadenaOriginal(long idSolicitud, Date fechaFirma);

    String generaCadenaOriginalAutorizarRemitir(Long idSolicitud, String rfcAutorizador);

}
