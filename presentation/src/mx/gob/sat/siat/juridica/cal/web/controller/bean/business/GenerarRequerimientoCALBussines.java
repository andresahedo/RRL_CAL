package mx.gob.sat.siat.juridica.cal.web.controller.bean.business;

import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoRequerimiento;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.RequerimientoDTO;
import mx.gob.sat.siat.juridica.base.dto.UserProfileDTO;
import mx.gob.sat.siat.juridica.base.excepcion.BusinessException;
import mx.gob.sat.siat.juridica.cal.api.GenerarRequerimientoCALFacade;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.util.excepcion.ParametrosRequerimientoAutoridadException;
import mx.gob.sat.siat.juridica.rrl.util.excepcion.ParametrosRequerimientoPromoventeException;
import mx.gob.sat.siat.juridica.rrl.util.excepcion.RequerimientoVacioException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import java.util.ArrayList;
import java.util.List;

@NoneScoped
@ManagedBean(name = "generarRequerimientoCALBussines")
public class GenerarRequerimientoCALBussines extends BaseBussinessBean {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1606379124103205789L;
    /**
     * Facade para atender la generaci&oacute;n del requerimiento.
     */
    @ManagedProperty("#{generarRequerimientoCALFacade}")
    private transient GenerarRequerimientoCALFacade generarRequerimientoCALFacade;

    /**
     * M&eacute;todo para obtener los tipos de requerimiento.
     */
    public List<CatalogoDTO> obtenerTiposDeRequerimiento() {
        return getGenerarRequerimientoCALFacade().obtenerTiposDeRequerimiento();
    }

    /**
     * M&eacute;todo para obtener las autoridades a las que se
     * solicitar&aacute; el requerimiento.
     */
    public List<CatalogoDTO> obtenerAutoridadesCAL() {
        return getGenerarRequerimientoCALFacade().obtenerAutoridadesCAL();
    }

    /**
     * M&eacute;todo para obtener los autorizadores del requerimiento.
     */
    public List<CatalogoDTO> obtenerAutorizadores(String numeroAsunto) {
        return generarRequerimientoCALFacade.obtenerAutorizadores(numeroAsunto);
    }

    /**
     * M&eacute;todo para preparar la generaci&oacute;n del
     * requerimiento.
     */
    public RequerimientoDTO prepararRequerimiento(String numeroAsunto, UserProfileDTO userProfile) {
        return getGenerarRequerimientoCALFacade().prepararRequerimiento(numeroAsunto, userProfile);
    }

    /**
     * M&eacute;todo para guardar el requerimiento.
     * 
     * @throws BusinessException
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public void generarRequerimiento(String rfcAutorizador, List<RequerimientoDTO> requerimientos,
            DatosBandejaTareaDTO datosBandeja, String rfcUsuario) throws BusinessException {
        int n = 0;
        @SuppressWarnings("rawtypes")
        List claves = new ArrayList<String>();
        if (requerimientos == null || requerimientos.isEmpty()) {
            throw new RequerimientoVacioException("Debe tener al menos un requerimiento a Promovente");
        }
        for (RequerimientoDTO req : requerimientos) {
            if (req.getClaveTipoRequerimiento().equals(TipoRequerimiento.PROMOVENTE_CAL.getClave())) {
                n++;
            }
            else {
                if (claves.contains(req.getClaveUnidadAdministrativa())) {

                    throw new ParametrosRequerimientoAutoridadException();
                }
                else {
                    claves.add(req.getClaveUnidadAdministrativa().toString());
                }

            }
        }
        if (n > 1) {
            throw new ParametrosRequerimientoPromoventeException("No debe tener mas de un requerimiento a Promovente");
        }
        getGenerarRequerimientoCALFacade().generarRequerimiento(rfcAutorizador, requerimientos, datosBandeja,
                rfcUsuario);
    }

    public GenerarRequerimientoCALFacade getGenerarRequerimientoCALFacade() {
        return generarRequerimientoCALFacade;
    }

    public void setGenerarRequerimientoCALFacade(GenerarRequerimientoCALFacade generarRequerimientoCALFacade) {
        this.generarRequerimientoCALFacade = generarRequerimientoCALFacade;
    }

}
