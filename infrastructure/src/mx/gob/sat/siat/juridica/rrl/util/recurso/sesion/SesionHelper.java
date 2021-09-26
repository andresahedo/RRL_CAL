/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.util.recurso.sesion;

import mx.gob.sat.siat.juridica.base.helper.BaseHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Clase Helper de correo
 * 
 * @author softtek
 * 
 */
@Component
public class SesionHelper extends BaseHelper {

    /**
     * Numero de version
     */
    private static final long serialVersionUID = 1L;

    @Value("${sesion.tiempo.aviso.expira}")
    private String tiempoAvisoSesion;

    @Value("${sesion.tiempo.espera.aviso}")
    private String tiempoEsperaAviso;

    public String getTiempoAvisoSesion() {

        return this.tiempoAvisoSesion;
    }

    public void setTiempoAvisoSesion(String tiempoAvisoSesion) {
        this.tiempoAvisoSesion = tiempoAvisoSesion;
    }

    public String getTiempoEsperaAviso() {

        return this.tiempoEsperaAviso;
    }

    public void setTiempoEsperaAviso(String tiempoEsperaAviso) {
        this.tiempoEsperaAviso = tiempoEsperaAviso;
    }

}
