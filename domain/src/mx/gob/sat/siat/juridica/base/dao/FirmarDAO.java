/*
 *  Todos los Derechos Reservados © 2013 SAT
 *  Servicio de Administracion Tributaria (SAT).
 *  
 *  Este software contiene informacion propiedad exclusiva del SAT considerada
 *  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 *  parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.model.*;

/**
 * 
 * @author softtek
 * 
 */
public interface FirmarDAO {

    /**
     * Metodo para guardar una firma
     * 
     * @param firma
     * @return
     */
    Firma guardarFirma(Firma firma);

    /**
     * Metodo para guardar una firmar de solicitud
     * 
     * @param firmaSol
     */
    void guardarFirmaSolicitud(FirmaSolicitud firmaSol);

    /**
     * Metodo para guardar una Firma de autorizacion de resolucion
     * 
     * @param firmaResol
     */
    void guardarFirmaAutorizacionResolicion(FirmaResolucion firmaResol);

    void guardarFirmaRequerimientoAutorizacion(FirmaRequerimiento firmaReq);

    void guardarFirmaAutorizacionRemision(FirmaRemision firmaRem);

    void guardarFirmaSNotificacion(FirmaNotificacion firmaNot);

    void guardarFirmaObservacion(FirmaObservacion firmaObservacion);

    FirmaNotificacion obtenerFirmaNotificacion(Long idNotificacion);

}
