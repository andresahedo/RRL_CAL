package mx.gob.sat.siat.juridica.rrl.api.impl;

import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.base.service.JuridicaSingletonClientServices;
import mx.gob.sat.siat.juridica.base.service.TramiteReprocesarServices;
import mx.gob.sat.siat.juridica.rrl.api.ReprocesarFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component("reprocesarFacade")
public class ReprocesarFacadeImpl extends BaseFacadeImpl implements ReprocesarFacade {

    /**
     * 
     */
    private static final long serialVersionUID = -446532122278743249L;
    private static final String PREFIJO_ID_REPROCESO = "Reprocesamiento";

    @Autowired
    private transient TramiteReprocesarServices tramiteReprocesarServices;

    @Autowired
    private transient JuridicaSingletonClientServices juridicaSingletonClientServices;

    @Override
    public void reprocesarTramites() {
        SimpleDateFormat dfReprocesa = new SimpleDateFormat("yyMMdd-HH");
        String idScheduler = PREFIJO_ID_REPROCESO + dfReprocesa.format(new Date());
        Boolean ejecuta = juridicaSingletonClientServices.permiteEjecucion(idScheduler);
        getLogger().info("Reprocesando scheduler .. " + idScheduler + " " + ejecuta);

    }

    public TramiteReprocesarServices getTramiteReprocesarServices() {
        return tramiteReprocesarServices;
    }

    public void setTramiteReprocesarServices(TramiteReprocesarServices tramiteReprocesarServices) {
        this.tramiteReprocesarServices = tramiteReprocesarServices;
    }

    public JuridicaSingletonClientServices getJuridicaSingletonClientServices() {
        return juridicaSingletonClientServices;
    }

    public void setJuridicaSingletonClientServices(JuridicaSingletonClientServices juridicaSingletonClientServices) {
        this.juridicaSingletonClientServices = juridicaSingletonClientServices;
    }

}
