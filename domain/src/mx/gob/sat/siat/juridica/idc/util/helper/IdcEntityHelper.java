/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.idc.util.helper;

import com.softtek.idc.client.dao.model.DomicilioSolicitud;
import mx.gob.sat.siat.juridica.base.dao.DomicilioDAO;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.*;
import mx.gob.sat.siat.juridica.base.helper.BaseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

/**
 * 
 * @author softtek
 */
@Component
public class IdcEntityHelper extends BaseHelper {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 5512302712875740188L;

    @Autowired
    private DomicilioDAO domicilioDAO;

    public <T extends mx.gob.sat.siat.juridica.base.dao.domain.model.PersonaSolicitud> T setDatosPersonaSolicitud(
            com.softtek.idc.client.dao.model.PersonaSolicitud solicitanteSolicitud, T solicitante) {
        solicitante.setRfc(solicitanteSolicitud.getRfc());
        solicitante.setEstadoContribuyente(solicitanteSolicitud.getEstadoContribuyente());
        solicitante.setCurp(solicitanteSolicitud.getCurp());
        solicitante.setBooleanExtranjero(false);

        if (solicitanteSolicitud.getTipoPersona().equals("F")) {
            solicitante.setNombre(solicitanteSolicitud.getNombre());
            solicitante.setApellidoPaterno(solicitanteSolicitud.getApellidoPaterno());
            solicitante.setApellidoMaterno(solicitanteSolicitud.getApellidoMaterno());
            solicitante.setPersonaMoral(false);
        }
        else {
            solicitante.setRazonSocial(solicitanteSolicitud.getRazonSocial());
            solicitante.setPersonaMoral(true);
        }

        mx.gob.sat.siat.juridica.base.dao.domain.model.DomicilioSolicitud domicilio = this.setAtributosDomicilio(solicitanteSolicitud.getDomicilio());
        solicitante.setDomicilio(domicilio);
        solicitante.setTelefono(solicitanteSolicitud.getTelefono());
        solicitante.setDescripcionGiro(solicitanteSolicitud.getDescripcionGiro());

        return solicitante;
    }

    /**
     * Metodo para agragar los atributos de un domicilio
     *
     * @param domicilioSolicitud
     * @return
     */
    private mx.gob.sat.siat.juridica.base.dao.domain.model.DomicilioSolicitud setAtributosDomicilio( DomicilioSolicitud domicilioSolicitud) {

        mx.gob.sat.siat.juridica.base.dao.domain.model.DomicilioSolicitud domicilio =
                new mx.gob.sat.siat.juridica.base.dao.domain.model.DomicilioSolicitud();

        if (domicilioSolicitud != null) {
            domicilio.setCalle(domicilioSolicitud.getCalle());
            domicilio.setCodigoPostal(domicilioSolicitud.getCodigoPostal());
            domicilio.setNumeroExterior(domicilioSolicitud.getNumeroExterior());
            domicilio.setNumeroInterior(domicilioSolicitud.getNumeroInterior());

            Pais pais = domicilioDAO.buscarPaisByClave("MEX");
            domicilio.setPais(pais);


            if (null != domicilioSolicitud.getEntidadFederativa().getClave()) {
                EntidadFederativa entidad = domicilioDAO.buscarEntidadByClaveIDC(domicilioSolicitud.getEntidadFederativa().getClave());

                if(null != entidad){
                    if(null != entidad.getClave()){
                        domicilio.setEntidadFederativa(entidad);
                        if (null != domicilioSolicitud.getDelegacionMunicipio().getClave() && null !=entidad.getClave() ) {
                            DelegacionMunicipio delegacionMunicipio = domicilioDAO.buscarDelegacionByClaveIDC(domicilioSolicitud.getDelegacionMunicipio().getClave(), entidad.getClave());
                            domicilio.setDelegacionMunicipio(delegacionMunicipio);
                        }
                    }
                }

            }

            if (null !=  domicilioSolicitud.getColonia().getClave()) {
                Colonia colonia = domicilioDAO.buscarColoniaByClaveIDC(domicilioSolicitud.getColonia().getClave());
                domicilio.setColonia(colonia);
            }

            if (null != domicilioSolicitud.getLocalidad().getClave()) {
                Localidad localidad = domicilioDAO.buscarLocalidadByClaveIDC(domicilioSolicitud.getLocalidad().getClave());
                domicilio.setLocalidad(localidad);
            }

            domicilio.setClaveAdministracionLocalRecaudadora(domicilioSolicitud.getClaveAdministracionLocalRecaudadora());
        }

        return domicilio;
    }
    public Flash getFlash() {
        return FacesContext.getCurrentInstance().getExternalContext().getFlash();
    }
}
