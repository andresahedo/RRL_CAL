/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service;

import java.io.Serializable;

import mx.gob.sat.siat.juridica.base.dao.domain.model.Firma;
import mx.gob.sat.siat.juridica.base.dao.domain.model.FirmaNotificacion;
import mx.gob.sat.siat.juridica.base.dao.domain.model.FirmaRemision;
import mx.gob.sat.siat.juridica.base.dao.domain.model.FirmaRequerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.FirmaResolucion;
import mx.gob.sat.siat.juridica.base.dao.domain.model.FirmaSolicitud;

/**
 * 
 * @author softtek
 */
public interface FirmarServices extends Serializable {

    /**
     * Metodo para guardar una firma
     * 
     * @param firma
     * @return Firma
     */
    Firma guardarFrima(Firma firma);

    /**
     * Metodo para guardar una solicitud firmada
     * 
     * @param firmaSol
     */
    void guardarFirmaSolicitud(FirmaSolicitud firmaSol);

    /**
     * Metodo para guardar la firma de autorizacion de resolucion
     * 
     * @param firmaResol
     */
    void guardarFirmaAutorizacionResolucion(FirmaResolucion firmaResol);

    /**
     * Metodo guardar la firma del requerimiento de autorizacion
     * 
     * @param firmaReq
     */
    void guardarFirmaRequerimientoAutorizacion(FirmaRequerimiento firmaReq);

    /**
     * Metodo que guarda la dirma de una autorizacion de remision
     * 
     * @param firmaRem
     * @param numAsunto
     */
    void guardarFirmaAutorizacionRemision(FirmaRemision firmaRem, String numAsunto);

    void guardarFirmaNotificacion(FirmaNotificacion firmaNot);

    void guardarFrimaObservacion(Firma firma, Long idObservacion);

    void guardaFirmaRechazoAsunto(Firma firma);

    /**
     * Genera una firma con base a la cadena original recibida y al
     * sello devuelto por la selladora
     * 
     * @param cadenaOriginal
     *            Cadena original a partir de la que se obtiene el
     *            sello
     */
    Firma obtenFirmaSelloSIAT(String cadenaOriginal);

	Firma obtenerFirmaSolicitud(String idSolicitud, String claveUsuario);

}
