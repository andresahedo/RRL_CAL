package mx.gob.sat.siat.juridica.nube.azure.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import java.nio.charset.Charset;

@WebService(serviceName = "NuevoEndPoint")
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
public class AzureEndPoint extends SpringBeanAutowiringSupport {

    private final transient Logger log = LoggerFactory.getLogger(getClass());

    @WebMethod(operationName = "holaMundo")
    public String holaMundo(@WebParam(name = "mensaje") byte[] mensaje) {
        String charSet = "UTF-8";
        log.debug("mensaje [{}]", new String(mensaje, Charset.forName(charSet)));
        return new String(mensaje, Charset.forName(charSet));
    }

}
