package mx.gob.sat.siat.juridica.buzon.service.impl;

import mx.gob.sat.siat.juridica.buzon.client.BuzonTributarioClient;
import mx.gob.sat.siat.juridica.buzon.exception.BuzonNoDisponibleException;
import mx.gob.sat.siat.juridica.buzon.exception.CorreoNoRegistradoException;
import mx.gob.sat.siat.juridica.buzon.service.BuzonTributarioService;
import mx.gob.sat.siat.juridica.buzon.wsdl.MedioComunicacion;
import mx.gob.sat.siat.juridica.buzon.wsdl.ObtieneMediosComunicacionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("buzonTributarioService")
public class BuzonTributarioServiceImpl implements BuzonTributarioService {

    private final transient Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BuzonTributarioClient buzonTributarioClient;

    @Override
    public String obtenerCorreos(String rfc) throws BuzonNoDisponibleException, CorreoNoRegistradoException {
        ObtieneMediosComunicacionResponse medios = null;
        try {
            medios = buzonTributarioClient.getMediosComunicacion(rfc);
        }
        catch (Exception e) {
            logger.error("problemas con el servicio de buzon", e);
            logger.debug("problemas con el servicio de buzon", e);
            throw new BuzonNoDisponibleException(e.getMessage(), e);
        }
        if (medios.getObtieneMediosComunicacionResult().getMedioComunicacion().isEmpty()) {
            throw new CorreoNoRegistradoException("El usuario no tiene correos registrados");
        }
        StringBuilder sb = new StringBuilder();

        int i = 0;
        for (MedioComunicacion medio : medios.getObtieneMediosComunicacionResult().getMedioComunicacion()) {

            switch (i) {
            case 0:
                sb.append(medio.getMedio());
                break;
            default:
                sb.append("\n");
                sb.append(medio.getMedio());
                break;
            }
            i++;

        }

        return sb.toString();
    }

    @Override
    public String obtenerNotificaciones(String info) {
        return buzonTributarioClient.getNotificacionesBuzonResponse(info).getNotificacionesBuzonResult();
    }

}
