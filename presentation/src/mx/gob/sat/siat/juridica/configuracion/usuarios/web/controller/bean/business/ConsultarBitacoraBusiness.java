/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.business;

import mx.gob.sat.siat.juridica.base.api.BaseCloudFacade;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.BaseCloudBusiness;
import mx.gob.sat.siat.juridica.configuracion.usuarios.api.ConsultarBitacoraFacade;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.BitacoraDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.FiltroBitacoraAGDTO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import java.util.List;

/**
 * Business para la consulta de la bitacora
 * 
 * @author softtek - EQG
 * 
 */
@ManagedBean(name = "consultarBitacoraBusiness")
@NoneScoped
public class ConsultarBitacoraBusiness extends BaseCloudBusiness {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Facade para la consulta de la bit�cora
     */
    @ManagedProperty("#{consultarBitacoraFacade}")
    private ConsultarBitacoraFacade consultarBitacoraFacade;

    /**
     * Metodo que obtiene las Unidades Administrativas
     * 
     * @return Lista de Unidades Administrativas
     */
    public List<CatalogoDTO> obtenerUnidadesAdministrativas() {
        return consultarBitacoraFacade.obtenerUnidadesAdministrativas();
    }

    public List<BitacoraDTO> obtenerDatosBitacora(FiltroBitacoraAGDTO filtroBitacora) {
        return consultarBitacoraFacade.obtenerDatosBitacora(filtroBitacora);
    }

    public List<CatalogoDTO> obtenerUnidadAdministrativa(String cveUnidad) {
        return consultarBitacoraFacade.obtenerUnidadAdministrativa(cveUnidad);
    }

    public String obtenerRangoDeDias() {
        return consultarBitacoraFacade.obtenerRangoDeDias();
    }

    @Override
    public BaseCloudFacade getCloudFacade() {
        // TODO Auto-generated method stub
        return null;
    }

    public List<CatalogoDTO> obtenerComboSeleccion() {
        return consultarBitacoraFacade.obtenerComboSeleccion();
    }

    public List<CatalogoDTO> obtenerComboSeleccionAplica() {
        return consultarBitacoraFacade.obtenerComboSeleccionAplica();
    }

    public ConsultarBitacoraFacade getConsultarBitacoraFacade() {
        return consultarBitacoraFacade;
    }

    public void setConsultarBitacoraFacade(ConsultarBitacoraFacade consultarBitacoraFacade) {
        this.consultarBitacoraFacade = consultarBitacoraFacade;
    }

}
