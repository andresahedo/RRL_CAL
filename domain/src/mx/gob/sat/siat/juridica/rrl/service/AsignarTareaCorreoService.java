/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.service;

import java.io.Serializable;

/**
 * 
 * @author softtek
 * 
 */
public interface AsignarTareaCorreoService extends Serializable {

    /**
     * Metodo para enviar un correo
     * 
     * @param numeroAsunto
     * @param rfc
     */
    void enviarCorreo(String numeroAsunto, String rfc, String tarea);

}
