package mx.gob.sat.siat.juridica.base.util.helper;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import mx.gob.sat.siat.juridica.base.dao.domain.model.Firma;
import mx.gob.sat.siat.juridica.ca.util.constants.CadenasConstants;
import mx.gob.sat.siat.selladora.ClienteSelladora;
import mx.gob.sat.siat.selladora.SelladoraException;

@Component
public class SelladoraHelper implements Serializable {

    private static final long serialVersionUID = -2776689818392089605L;
    private final transient Logger logger = LoggerFactory.getLogger(getClass());

    public Firma getSello(String cadenaOriginal) throws SelladoraException {
        ClienteSelladora cs = new ClienteSelladora();
        String sello = "";
        String idSelladora = null;
        try {
            sello = cs.getSelloDigital(cadenaOriginal);
            idSelladora = cs.getIdSelladora();
        }
        catch (Exception e) {
            throw new SelladoraException(e);
        }
        Firma firmaSelladora = new Firma();
        firmaSelladora.setCertificado(idSelladora);
        firmaSelladora.setFirmaElectronica(sello);
        firmaSelladora.setCadenaOriginal(cadenaOriginal);
        return firmaSelladora;
    }

    public Firma getSelloDefault(String cadenaOriginal) {
        Firma firmaSelladora = new Firma();
        firmaSelladora.setCadenaOriginal(cadenaOriginal);
        firmaSelladora.setFirmaElectronica(CadenasConstants.SELLO_DEFAULT);
        return firmaSelladora;
    }
    
    
    
    public String getSelloCadena(String cadenaOriginal) {
        String sello = "";
        try {
            ClienteSelladora cs = new ClienteSelladora();
            sello = cs.getSelloDigital(cadenaOriginal);
            logger.debug("Obtiene el sello:"+sello);
        }
        catch (SelladoraException e) {
            logger.error("Falla la selladora:"+e.getMessage());
            sello = CadenasConstants.SELLO_DEFAULT;
        }
        return sello;
    }
}
