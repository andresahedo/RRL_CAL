/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.cal.web.controller.bean.business;

import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.dto.UserProfileDTO;
import mx.gob.sat.siat.juridica.cal.api.AtencionPromocionFacade;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.FiltroBandejaTareaDTO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;

/**
 * Clase para hacer los llamados al facade de atenci&oacute;n de
 * promoci&oacute;n.
 * 
 * @author antonio.flores
 * 
 */
@ManagedBean(name = "atencionPromocionBussines")
@NoneScoped
public class AtencionPromocionCALBussines extends BaseBussinessBean {

    /**
     * 
     */
    private static final long serialVersionUID = 264314855084549637L;
    @ManagedProperty("#{atencionPromocionFacade}")
    private AtencionPromocionFacade atencionPromocionFacade;

    /**
     * Method to update data from atenci&oacute;n promoci&oacute;n.
     * 
     * @param filtroBandejaTareaDTO
     * @param userProfileDTO
     */
    public void updateAtencion(FiltroBandejaTareaDTO filtroBandejaTareaDTO, UserProfileDTO userProfileDTO) {

        atencionPromocionFacade.registraAtencionPromocion();
    }

    /**
     * @return the atencionPromocionFacade
     */
    public AtencionPromocionFacade getAtencionPromocionFacade() {
        return atencionPromocionFacade;
    }

    /**
     * @param atencionPromocionFacade
     *            the atencionPromocionFacade to set
     */
    public void setAtencionPromocionFacade(AtencionPromocionFacade atencionPromocionFacade) {
        this.atencionPromocionFacade = atencionPromocionFacade;
    }

    public TramiteDTO obtenerTramite(String numAsunto) {
        return atencionPromocionFacade.obtenerTramite(numAsunto);
    }

    /**
     * M&eacute;todo para actualizar los datos de BP
     * 
     * @param datosBandejaTareaDTO
     */
    public void actualizaDatosBP(DatosBandejaTareaDTO datosBandejaTareaDTO) {
        atencionPromocionFacade.actualizaDatosBP(datosBandejaTareaDTO.getIdInstancia().intValue(),
                datosBandejaTareaDTO.getNumeroAsunto(), datosBandejaTareaDTO.getRfcUsuarioAsignado());
    }

    public String getIdeTareaOrigen() {
        return atencionPromocionFacade.getIdeTareaOrigen();
    }

    public String obtenerObservacionTurnado(String numeroAsunto) {
        return atencionPromocionFacade.obtenerObservacionTurnado(numeroAsunto);
    }

}
