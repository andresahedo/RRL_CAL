package mx.gob.sat.siat.juridica.buzon.client;

import mx.gob.sat.siat.juridica.buzon.wsdl.NotificacionesBuzon;
import mx.gob.sat.siat.juridica.buzon.wsdl.NotificacionesBuzonResponse;
import mx.gob.sat.siat.juridica.buzon.wsdl.ObtieneMediosComunicacion;
import mx.gob.sat.siat.juridica.buzon.wsdl.ObtieneMediosComunicacionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class BuzonTributarioClient extends WebServiceGatewaySupport {

    @Value("${buzon.soapAction}")
    private String soapAction;

    public NotificacionesBuzonResponse getNotificacionesBuzonResponse(String info) {

        NotificacionesBuzon request = new NotificacionesBuzon();
        request.setInfo(info);

        logger.debug("Enviando request para info: " + info);
        NotificacionesBuzonResponse response =
                (NotificacionesBuzonResponse) getWebServiceTemplate().marshalSendAndReceive(request,
                        new SoapActionCallback(soapAction + "NotificacionesBuzon"));
        return response;

    }

    public ObtieneMediosComunicacionResponse getMediosComunicacion(String rfc) {

        ObtieneMediosComunicacion request = new ObtieneMediosComunicacion();
        request.setRfc(rfc);

        logger.debug("Enviando request para rfc: " + rfc);
        ObtieneMediosComunicacionResponse response =
                (ObtieneMediosComunicacionResponse) getWebServiceTemplate().marshalSendAndReceive(request,
                        new SoapActionCallback(soapAction + "ObtieneMediosComunicacion"));
        return response;

    }

}
