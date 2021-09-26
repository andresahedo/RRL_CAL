/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.juridica.ara.impl;

import mx.gob.sat.sgi.SgiCripto.SgiARAException;
import mx.gob.sat.sgi.SgiCripto.ara.util.Certificado;
import mx.gob.sat.siat.juridica.ara.AraFacade;
import mx.gob.sat.siat.juridica.ara.dto.CertificadoDTO;
import mx.gob.sat.siat.juridica.ara.service.AraService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("araFacade")
public class AraFacadeImpl implements AraFacade{
    
    @Autowired
    private AraService araService;
    
    public Boolean getEstadoConecta(){
        return araService.getEstadoConecta();
    }

    /**
     * Se conecta a la SgiARA.
     * @return
     * @throws SgiARAException 
     */
    @Override
    public void conecta() throws SgiARAException {
        araService.conecta();
    }
    
    /**
     * Se conecta a la SgiARA.
     */
    @Override
    public void desconecta() {
        araService.desconecta();
    }

    /**
     * Obtienen el certificado a partir del numero de serie.
     * @param sNumSerie
     * @return
     * @throws SgiARAException 
     */
    @Override
    public CertificadoDTO getCertificado(String sNumSerie) throws SgiARAException {
        CertificadoDTO certificadoDTO = new CertificadoDTO();
        Certificado certificado = araService.getCertificado(sNumSerie);
        BeanUtils.copyProperties(certificado, certificadoDTO);
        
        return certificadoDTO;
    }

    /**
     * Obtiene el estado del certificado por numero de Serie.
     * @param sNumSerie
     * @return
     * @throws SgiARAException 
     */
    @Override
    public CertificadoDTO getEdoCertificado(String sNumSerie) throws SgiARAException {
        CertificadoDTO certificadoDTO = new CertificadoDTO();
        Certificado certificado = araService.getEdoCertificado(sNumSerie);
        BeanUtils.copyProperties(certificado, certificadoDTO);
        
        return certificadoDTO;
    }

    /**
     * Obtiene el Numero de Serie del certificado de FIEL activo del RFC dado.
     * @param rfc
     * @return
     * @throws SgiARAException 
     */
    @Override
    public String getNoSerieRFC(String rfc) throws SgiARAException {
        return araService.getNoSerieRFC(rfc);
    }

    /**
     * Mantiene viva la conexion con la SgiARA.
     * @return
     * @throws SgiARAException 
     */
    @Override
    public boolean keepAliveARA() throws SgiARAException {
        return araService.keepAliveARA();
    }
    
}
