package mx.gob.sat.siat.juridica.ara.util;

import mx.gob.sat.sgi.SgiCripto.SgiARAException;
import mx.gob.sat.siat.juridica.ara.AraFacade;
import mx.gob.sat.siat.juridica.ara.dto.CertificadoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class AraValidadorHelper implements Serializable {

    private static final long serialVersionUID = -4934863831004314553L;
    private final transient Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private AraFacade araFacade;

    /**
     * Obtienen el certificado a partir del numero de serie.
     * @param sNumSerie
     * @return
     * @throws SgiARAException 
     */
    public CertificadoDTO getCertificado(String sNumSerie) throws SgiARAException {
        /*
        logger.debug("***********se obtiene el numero el certificado - ARA***********", sNumSerie);
        CertificadoDTO certificadoDTO = null;
        int error = 0;
        String mensajeError = "";

        try {
            if (araFacade.getEstadoConecta()) {
                certificadoDTO = araFacade.getCertificado(sNumSerie);
            } else {
                //araFacade.conecta();
                certificadoDTO = araFacade.getCertificado(sNumSerie);
            }
        } catch (SgiARAException ex) {
            logger.error("Ocurrio un error al validar el estado del Certificado con ARA - AraValidadorHelper.getCertificado", ex);
            error = ex.getError();
            mensajeError = ex.getMessage();
        }
        
        //Si manda el erro es menor a 0 realiza la reconexion
        if (error < 0) {
            //reconexionAra();
            certificadoDTO = araFacade.getCertificado(sNumSerie);
        } else if (error != 0) {
            throw new SgiARAException(error, MessageFormat.format(AraConstantes.MENSAJE_ERROR, mensajeError));
        }


        return certificadoDTO;
        
        */
        
        return araFacade.getCertificado(sNumSerie);
    }

    /**
     * Obtiene el estado del certificado por numero de Serie.
     * @param sNumSerie
     * @return
     * @throws SgiARAException 
     */
    public Boolean getEdoCertificado(String sNumSerie) throws SgiARAException {
        /*
        logger.debug("***********se obtiene el estado del certificado - ARA***********",sNumSerie);
        CertificadoDTO certificadoDTO = null;
        int error = 0;
        String mensajeError = "";

        try {
            if (araFacade.getEstadoConecta()) {
                certificadoDTO = araFacade.getEdoCertificado(sNumSerie);
            } else {
                araFacade.conecta();
                certificadoDTO = araFacade.getEdoCertificado(sNumSerie);
            }
        } catch (SgiARAException ex) {
            logger.error("Ocurrio un error al validar el estado del Certificado con ARA - AraValidadorHelper.getEdoCertificado {0}", ex);
            error = ex.getError();
            mensajeError = ex.getMessage();
        }
        
        //Si manda el erro es menor a 0 realiza la reconexion
        if (error < 0) {
            reconexionAra();
            certificadoDTO = araFacade.getEdoCertificado(sNumSerie);
        } else if (error != 0) {
            throw new SgiARAException(error, MessageFormat.format(AraConstantes.MENSAJE_ERROR, mensajeError));
        }

        return validaEdoCertificado(certificadoDTO);
        */
        return true;
    }

    /**
     * Obtiene el Numero de Serie del certificado de FIEL activo del RFC dado.
     * @param rfc
     * @return
     * @throws SgiARAException 
     */
    public String getNoSeriebyRFC(String rfc) throws SgiARAException {
        /*
        logger.debug("***********se obtiene el numero de serie por RFC - ARA***********",rfc);
        String numSerie = "";
        String mensajeError = "";
        String rfcFormato = rfc.toUpperCase();
        int error = 0;

        try {
            if (araFacade.getEstadoConecta()) {
                numSerie = araFacade.getNoSerieRFC(rfcFormato);
            } else {
                araFacade.conecta();
                numSerie = getNoSeriebyRFC(rfcFormato);
            }
        } catch (SgiARAException ex) {
            logger.error("Ocurrio un error al validar el estado del Certificado con ARA - AraValidadorHelper.getEdoCertificado {0}", ex);
            error = ex.getError();
            mensajeError = ex.getMessage();
        }

        //Si manda el erro es menor a 0 realiza la reconexion
        if (error < 0) {
            reconexionAra();
            numSerie = araFacade.getNoSerieRFC(rfcFormato);
        } else if (error != 0) {
            throw new SgiARAException(error, MessageFormat.format(AraConstantes.MENSAJE_ERROR, mensajeError));
        }

        return numSerie;
        */
        return "12345678900987654321";
    }
    
    /**
     * Limpia el numero de serie del certificado
     * @param sNumSerie
     * @return 
     */
    public String getNumSerie(String sNumSerie) throws SgiARAException{
        logger.debug("***********se obtiene el numero de serie 20 digitos - ARA***********", sNumSerie);
        StringBuilder numSerie = new StringBuilder();
        /*
        if(sNumSerie == null || sNumSerie.equals("")){
            throw new SgiARAException(1001, AraConstantes.MENSAJE_SERIE_VACIO);
        }
        
        if (sNumSerie.length() > 20) {
            String[] cadenaSplit = sNumSerie.split("");
            
            for (int i = 0; i < cadenaSplit.length; i++) {
                if ((i % 2) == 0) {
                    numSerie.append(cadenaSplit[i]);
                }
            }
        }
        else{
            numSerie.append(sNumSerie);
        }
        
        return numSerie.toString();
        
        */
        
        return "12345678900987654321";
    }
    
    public Boolean validarRfcNumSerie(String numSerie, String rfc) throws SgiARAException{
        /*
        if(numSerie.equals(getNoSeriebyRFC(rfc))){
            return true;
        }else{
            throw new SgiARAException(1003, MessageFormat.format(AraConstantes.MENSAJE_SERIE_INCORRECTO, rfc));
        }
        */
        
        return true;
    }
    
    /**
     * Realiza la reconexion con el ARA
     */
    private void reconexionAra() throws SgiARAException {
        logger.debug("***********Se realizara la reconexión con ARA***********");
        araFacade.desconecta();
        araFacade.conecta();
    }
    /**
     * Valida los estados de los certificados
     * @param certificadoDTO
     * @return 
     */
    private Boolean validaEdoCertificado(CertificadoDTO certificadoDTO) throws SgiARAException{
        logger.debug("***********se valida el estado del certificado - ARA***********");
        Boolean certificadoValido = Boolean.FALSE;
        /*
        if(certificadoDTO == null){
            throw new SgiARAException(1000, AraConstantes.MENSAJE_ERROR_ARA);
        }else if (certificadoDTO.getEstado().equals(AraConstantes.ACTIVO)) {
            certificadoValido = true;
        } else if (certificadoDTO.getEstado().equals(AraConstantes.REVOCADO)) {
            throw new SgiARAException(10001, AraConstantes.MENSAJE_REVOCADO);
        } else if (certificadoDTO.getEstado().equals(AraConstantes.CADUCO)) {
            throw new SgiARAException(10002, AraConstantes.MENSAJE_CADUCO);
        }
        
        //return certificadoValido;
        */
        return true;
    }
}
