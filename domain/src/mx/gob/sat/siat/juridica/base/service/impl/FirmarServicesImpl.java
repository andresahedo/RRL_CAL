/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service.impl;

import mx.gob.sat.siat.juridica.base.dao.FirmarDAO;
import mx.gob.sat.siat.juridica.base.dao.RemisionDao;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.service.FirmarServices;
import mx.gob.sat.siat.juridica.base.util.helper.SelladoraHelper;
import mx.gob.sat.siat.selladora.SelladoraException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author softtek
 * 
 */
@Service("firmarServices")
public class FirmarServicesImpl extends BaseSerializableBusinessServices implements FirmarServices {

    /**
     * Id de la version
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "firmarDAO" tipo FirmarDAO
     */
    @Autowired
    private FirmarDAO firmarDAO;

    /**
     * Atributo privado "remisionDao" tipo RemisionDao
     */
    @Autowired
    private RemisionDao remisionDao;

    @Autowired
    private SelladoraHelper selladoraHelper;

    /**
     * Metodo para guardar una firma
     * 
     * @param firma
     * @return Firma
     */
    public Firma guardarFrima(Firma firma) {
        return firmarDAO.guardarFirma(firma);
    }

    /**
     * Metodo para guardar una firma
     * 
     * @param firma
     * @return Firma
     */
    @Override
    public void guardarFrimaObservacion(Firma firma, Long idObservacion) {
        FirmaObservacionPK firmaObservacionPK = new FirmaObservacionPK();
        FirmaObservacion firmaObservacion = new FirmaObservacion();
        firmaObservacionPK.setIdFirma(firma.getIdFirma());
        firmaObservacionPK.setIdObservacion(idObservacion);
        firmaObservacion.setFirmaObservacionPK(firmaObservacionPK);
        firmarDAO.guardarFirmaObservacion(firmaObservacion);
    }

    /**
     * Metodo para guardar una solicitud firmada
     * 
     * @param firmaSol
     */
    public void guardarFirmaSolicitud(FirmaSolicitud firmaSol) {
        firmarDAO.guardarFirmaSolicitud(firmaSol);
    }

    /**
     * Metodo para guardar la firma de autorizacion de resolucion
     * 
     * @param firmaResol
     */
    public void guardarFirmaAutorizacionResolucion(FirmaResolucion firmaResol) {
        firmarDAO.guardarFirmaAutorizacionResolicion(firmaResol);
    }

    /**
     * Metodo guardar la firma del requerimiento de autorizacion
     * 
     * @param firmaReq
     */
    @Override
    public void guardarFirmaRequerimientoAutorizacion(FirmaRequerimiento firmaReq) {
        firmarDAO.guardarFirmaRequerimientoAutorizacion(firmaReq);
    }

    /**
     * Metodo que guarda la dirma de una autorizacion de remision
     * 
     * @param firmaRem
     * @param numAsunto
     */
    @Override
    public void guardarFirmaAutorizacionRemision(FirmaRemision firmaRem, String numAsunto) {
        Remision remision = remisionDao.obtenerUltimaRemisionPorIdTramite(numAsunto);
        firmaRem.getFirmaRemisionPK().setIdRemision(remision.getIdRemision());
        firmarDAO.guardarFirmaAutorizacionRemision(firmaRem);

    }

    /**
     * Metodo para guardar una notificacion firmada
     * 
     * @param firmaNot
     */
    @Override
    public void guardarFirmaNotificacion(FirmaNotificacion firmaNot) {
        firmarDAO.guardarFirmaSNotificacion(firmaNot);
    }

    /**
     * Genera una firma con base a la cadena original recibida y al
     * sello devuelto por la selladora
     * 
     * @param cadenaOriginal
     *            Cadena original a partir de la que se obtiene el
     *            sello
     */
    @Override
    public Firma obtenFirmaSelloSIAT(String cadenaOriginal) {
        Firma firmaSIAT = null;
        try {
            firmaSIAT = selladoraHelper.getSello(cadenaOriginal);
        }
        catch (SelladoraException se) {
            firmaSIAT = selladoraHelper.getSelloDefault(cadenaOriginal);
            getLogger().error("Sella cadena:  " + se.getMessage());
        }

        return firmaSIAT;
    }

    @Override
    public void guardaFirmaRechazoAsunto(Firma firma) {
        // TODO transformrerFirmaRechazo
    }

}
