/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.juridica.ara;

import mx.gob.sat.sgi.SgiCripto.SgiARAException;
import mx.gob.sat.siat.juridica.ara.dto.CertificadoDTO;

public interface AraFacade {
    Boolean getEstadoConecta();
    void conecta() throws SgiARAException;
    void desconecta();
    CertificadoDTO getCertificado(String sNumSerie) throws SgiARAException;
    CertificadoDTO getEdoCertificado(String sNumSerie) throws SgiARAException;
    String getNoSerieRFC(String rfc) throws SgiARAException;
    boolean keepAliveARA() throws SgiARAException;
}
