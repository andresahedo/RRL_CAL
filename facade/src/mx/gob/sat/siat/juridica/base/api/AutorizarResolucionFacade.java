package mx.gob.sat.siat.juridica.base.api;

import mx.gob.sat.siat.juridica.base.facade.BaseFacade;

import java.util.Date;

public interface AutorizarResolucionFacade extends BaseFacade {

    /**
     * Genera la cadena original de la autorizaci&oacute;n de la
     * resoluci&oacute;n
     * 
     * @return Cadena original
     */
    String generaCadenaOriginalAutorizarResolucion(Long idSolicitud, Date fechaFirma, String rfcFuncionario);

}
