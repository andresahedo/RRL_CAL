package mx.gob.sat.siat.juridica.base.service.impl;

import mx.gob.sat.siat.juridica.ca.util.helper.EnvioCorreosHelper;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.domain.model.DatosCorreoNotificacion;
import mx.gob.sat.siat.juridica.configuracion.usuarios.service.EnviarCorreoNotificacionMovimientosNovellService;
import mx.gob.sat.siat.juridica.rrl.util.recurso.correo.MailRequestSender;
import mx.gob.sat.siat.juridica.rrl.util.recurso.correo.model.MailMessageRequest;
import mx.sat.siat.juridica.bpm.constant.CorreoConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Service("enviarCorreoNotificacionService")
public class EnviarCorreoNotificacionMovimientosNovellServiceImpl implements
        EnviarCorreoNotificacionMovimientosNovellService {

    /**
     * 
     */
    private static final long serialVersionUID = 8558518727536830569L;

    /**
     * Atributo "envioCorreosHelper" tipo EnvioCorreosHelper
     */
    @Autowired
    private EnvioCorreosHelper envioCorreosHelper;

    /**
     * Atributo "mailRequestSender" tipo MailRequestSender
     */
    @Autowired
    private MailRequestSender mailRequestSender;

    /**
     * Metodo para enviar un correo
     */
    public void enviarCorreoNotificacionActualizarMovimiento(DatosCorreoNotificacion datosCorreo) {
        MailMessageRequest request =
                new MailMessageRequest(new HashSet<String>(), "SUBJECT", "TEMPLATE", new HashMap<String, String>());
        HashSet<String> destinatarios = new HashSet<String>();
        for (String para : datosCorreo.getCorreos()) {
            destinatarios.add(para);
        }
        request.setDestinations(destinatarios);
        request.setParams(envioCorreosHelper.correoNotificacionActualizacionMovimientos(datosCorreo));

        String logoSat =
                File.separator + "resources" + File.separator + "css" + File.separator + "images" + File.separator
                        + "SAT.jpg";

        Map<String, String> imagenes = new HashMap<String, String>();
        imagenes.put("logosat", logoSat);

        String logoHacienda =
                File.separator + "resources" + File.separator + "css" + File.separator + "images" + File.separator
                        + "SHCP.jpg";
        imagenes.put("logoHacienda", logoHacienda);

        request.setImagenes(imagenes);

        request.setSubject("Notificaci\u00f3n de Movimiento de Usuario");
        request.setTemplate(CorreoConstant.CORREO_ACTUALIZACION_USUARIOS);
        mailRequestSender.sendMailRequest(request);

    }

}
