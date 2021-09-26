/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service.impl;

import mx.gob.sat.siat.juridica.base.dao.TramiteReprocesarDAO;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoReproceso;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoErrorEnvioBPM;
import mx.gob.sat.siat.juridica.base.dao.domain.model.TramiteReprocesar;
import mx.gob.sat.siat.juridica.base.service.TramiteReprocesarServices;
import mx.gob.sat.siat.juridica.bpm.constante.ParametrosBPM;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;



/**
 * 
 * @author softtek
 * 
 */
@Service("tramiteReprocesarServices")
public class TramiteReprocesarServicesImpl extends BaseBusinessServices implements TramiteReprocesarServices {

    /**
     * Id de la version
     */
    private static final long serialVersionUID = 1L;
   
    /**
     * Atributo privado "tramiteReprocesarDAO" de tipo TramiteReprocesarDAO
     */
    @Autowired
    private TramiteReprocesarDAO tramiteReprocesarDAO;
    
    @Override
    public void guardarTramiteAReprocesar(JSONObject json, String bpdId, String processAppId, TipoErrorEnvioBPM tipoErrorEnvioBPM) {

        if (json.has(ParametrosBPM.STR_TRAMITE)){
            JSONObject jTramite = (JSONObject)json.get(ParametrosBPM.STR_TRAMITE);
            String asunto = jTramite.getString("folioTramite");

            TramiteReprocesar tramiteReprocesar = new TramiteReprocesar(asunto);

            tramiteReprocesar.setReintentos(0);
            tramiteReprocesar.setFechaRegistro(Calendar.getInstance().getTime());
            tramiteReprocesar.setEstatusEnvio(EstadoReproceso.PENDIENTE.getClave());
            tramiteReprocesar.setTipoError(tipoErrorEnvioBPM.getClave());

            JSONObject reproceso = new JSONObject();
            reproceso.put(ParametrosBPM.STR_JSON, json.toString());
            reproceso.put(ParametrosBPM.STR_BPDID, bpdId);
            reproceso.put(ParametrosBPM.STR_PROCESSAPPID, processAppId);
            reproceso.put(ParametrosBPM.STR_TIPOREPROCESO, "iniciarTramite");

            tramiteReprocesar.setParametros(reproceso.toString());

            tramiteReprocesarDAO.guardarTramite(tramiteReprocesar);

        }

    }

    @Override
    public void guardarTramiteAReprocesar(String numeroAsunto, String parametros, TipoErrorEnvioBPM tipoErrorEnvioBPM) {
        TramiteReprocesar tramiteReprocesar = new TramiteReprocesar(numeroAsunto);

        tramiteReprocesar.setReintentos(0);
        tramiteReprocesar.setFechaRegistro(Calendar.getInstance().getTime());
        tramiteReprocesar.setEstatusEnvio(EstadoReproceso.PENDIENTE.getClave());
        tramiteReprocesar.setTipoError(tipoErrorEnvioBPM.getClave());
        tramiteReprocesar.setParametros(parametros);

        tramiteReprocesarDAO.guardarTramite(tramiteReprocesar);

    }

    @Override
    public void actualizarTramiteAReprocesar(String numeroAsunto,
            EstadoReproceso estatusInstancia) {

        TramiteReprocesar tramiteReprocesar = tramiteReprocesarDAO.obtenerTramitePorId(numeroAsunto);
        tramiteReprocesar.setEstatusEnvio(estatusInstancia.getClave());

        if (estatusInstancia.equals(EstadoReproceso.ERROR)) {
            tramiteReprocesar.setReintentos(tramiteReprocesar.getReintentos() + 1);
        }


        tramiteReprocesarDAO.modificarTramite(tramiteReprocesar);
    }

}
