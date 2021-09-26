/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.juridica.ara.service.impl;

import mx.gob.sat.sgi.SgiCripto.SgiARA;
import mx.gob.sat.sgi.SgiCripto.SgiARAException;
import mx.gob.sat.sgi.SgiCripto.ara.util.Certificado;
import mx.gob.sat.siat.juridica.ara.service.AraService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service("araService")
public class AraServiceImpl implements AraService {
    private final transient org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${configuracion.ara}")
    private String rutaArchivoCfg;
    @Autowired
    private SgiARA sgiARA;
    private Boolean estadoConecta;
    

    public AraServiceImpl() {
    }

    /**
     * @return the estadoConecta
     */
    @Override
    public Boolean getEstadoConecta() {
        return estadoConecta;
    }
    
    /**
     * Se conecta a la SgiARA.
     * @throws SgiARAException 
     */
    @PostConstruct
    @Override
    public void conecta() throws SgiARAException {
        /*
        logger.debug("***********Iniciando ARA***********");
        sgiARA.Inicia(this.rutaArchivoCfg);
        logger.debug("***********Conectando con ARA***********");
        this.estadoConecta = sgiARA.ConectaARA();
        if (!this.estadoConecta) {
            logger.debug("***********Segundo intento de conexión con ARA***********");
            sgiARA.ConectaARA();
        }
        */
    }
    /**
     * Se conecta a la SgiARA.
     * @param rutaArchivoCfg Ruta donde se encuentra el archivo ARA.cfg
     * @return 
     * @throws SgiARAException 
     */
    public boolean conecta(String rutaArchivoCfg) throws SgiARAException {
        /*
        Boolean conecta;
        logger.debug("***********Iniciando ARA***********");
        sgiARA.Inicia(rutaArchivoCfg);
        logger.debug("***********Conectando con ARA***********");
        conecta = sgiARA.ConectaARA();
        if (!conecta) {
            logger.debug("***********Segundo intento de conexión con ARA***********");
            sgiARA.ConectaARA();
        }

        return conecta;
        */
        return true;
    }
    
    /**
     * Se desconecta de la SgiARA.
     */
    @PreDestroy
    @Override
    public void desconecta(){
        /*
        logger.debug("***********Desconectando con ARA***********");
        try {
            sgiARA.DesconectaARA();
        } catch (SgiARAException ex) {
            Logger.getLogger(AraServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
    }
    
    /**
     * Obtienen el certificado a partir del numero de serie.
     * @param sNumSerie
     * @return
     * @throws SgiARAException 
     */
    @Override
    public Certificado getCertificado(String sNumSerie) throws SgiARAException{
        logger.debug("***********Obteniendo certificado ARA***********");
        
        //return sgiARA.SolCDxNS(sNumSerie);
        
        return new Certificado();
    }
    
    /**
     * Obtiene el estado del certificado por numero de Serie.
     * @param sNumSerie
     * @return
     * @throws SgiARAException 
     */
    @Override
    public Certificado getEdoCertificado(String sNumSerie) throws SgiARAException{
        logger.debug("***********Obteniendo estado del certificado ARA***********");
        //return sgiARA.SolEdoCDxNS(sNumSerie);
        Certificado cer = new Certificado();
        cer.setEstado("A");
        return cer;
    }
    
    /**
     * Obtiene el Numero de Serie del certificado de FIEL activo del RFC dado.
     * @param rfc
     * @return
     * @throws SgiARAException 
     */
    @Override
    public String getNoSerieRFC(String rfc) throws SgiARAException{
        logger.debug("***********Obteniendo no de serie ARA***********");
        //return sgiARA.ConsultaRFC(rfc);
        return "12345678900987654321";
    }
    
    /**
     * Mantiene viva la conexion con la SgiARA.
     * @return
     * @throws SgiARAException 
     */
    @Override
    public boolean keepAliveARA() throws SgiARAException{
        logger.debug("***********KeepaliveARA ARA***********");
        //return sgiARA.KeepaliveARA();
        return true;
    }
}
