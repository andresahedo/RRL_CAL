package mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ResourceBundle;

@ViewScoped
@ManagedBean(name = "encabezadoController")
public class EncabezadoController extends AdministracionUsuariosBaseController {

    /**
     * 
     */
    private static final long serialVersionUID = 1444539450858807635L;

    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;

    public ResourceBundle getMessages() {
        return messages;
    }

    public void setMessages(ResourceBundle messages) {
        this.messages = messages;
    }

}
