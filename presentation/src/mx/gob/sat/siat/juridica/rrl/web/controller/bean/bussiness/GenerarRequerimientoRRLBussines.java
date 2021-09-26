/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness;

import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoRequerimiento;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.RequerimientoDTO;
import mx.gob.sat.siat.juridica.base.dto.UserProfileDTO;
import mx.gob.sat.siat.juridica.base.excepcion.BusinessException;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import mx.gob.sat.siat.juridica.rrl.api.GenerarRequerimientoRRLFacade;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.util.excepcion.ParametrosRequerimientoAutoridadException;
import mx.gob.sat.siat.juridica.rrl.util.excepcion.ParametrosRequerimientoPromoventeException;
import mx.gob.sat.siat.juridica.rrl.util.excepcion.RequerimientoVacioException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * Bean para conectar con el backend, atiende todas las peticiones de
 * la generacion del requerimiento.
 * 
 * @author Softtek
 * 
 */
@ManagedBean(name = "generarRequerimientoRRLBussines")
@NoneScoped
public class GenerarRequerimientoRRLBussines extends BaseBussinessBean {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1606379124103205789L;

    /**
     * Facade para atender la generaci&oacute;n del requerimiento.
     */
    @ManagedProperty("#{generarRequerimientoRRLFacade}")
    private GenerarRequerimientoRRLFacade generarRequerimientoRRLFacade;

    /**
     * M&eacute;todo para obtener los tipos de requerimiento.
     */
    public List<CatalogoDTO> obtenerTiposDeRequerimiento() {
        return getGenerarRequerimientoRRLFacade().obtenerTiposDeRequerimiento();
    }

    /**
     * M&eacute;todo para obtener las autoridades a las que se
     * solicitar&aacute; el requerimiento.
     */
    public List<CatalogoDTO> obtenerAutoridadesRRL() {
        return getGenerarRequerimientoRRLFacade().obtenerAutoridadesRRL();
    }

    /**
     * M&eacute;todo para obtener los autorizadores del requerimiento.
     */
    public List<CatalogoDTO> obtenerAutorizadores(String numeroAsunto) {
        return generarRequerimientoRRLFacade.obtenerAutorizadores(numeroAsunto);
    }

    /**
     * M&eacute;todo para preparar la generaci&oacute;n del
     * requerimiento.
     */
    public RequerimientoDTO prepararRequerimiento(String numeroAsunto, UserProfileDTO userProfile) {
        return getGenerarRequerimientoRRLFacade().prepararRequerimiento(numeroAsunto, userProfile);
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
            if (req.getClaveTipoRequerimiento().equals(TipoRequerimiento.CONTRIBUYENTE.getClave())) {
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
        getGenerarRequerimientoRRLFacade().generarRequerimiento(rfcAutorizador, requerimientos, datosBandeja,
                rfcUsuario);
    }

    /**
     * 
     * @return generarRequerimientoRRLFacade
     */
    public GenerarRequerimientoRRLFacade getGenerarRequerimientoRRLFacade() {
        return generarRequerimientoRRLFacade;
    }

    /**
     * 
     * @param generarRequerimientoRRLFacade
     *            a fijar
     */
    public void setGenerarRequerimientoRRLFacade(GenerarRequerimientoRRLFacade generarRequerimientoRRLFacade) {
        this.generarRequerimientoRRLFacade = generarRequerimientoRRLFacade;
    }

    public List<DocumentoDTO> obtenerDocumentosComplementarios(Long idSolicitud) {
        return generarRequerimientoRRLFacade.obtenerDocumentosComplementarios(idSolicitud);
    }

}
