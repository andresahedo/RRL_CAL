/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness;

import mx.gob.sat.siat.juridica.base.api.BandejaPendientesFacade;
import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaPendientesDTO;
import mx.gob.sat.siat.juridica.rrl.dto.FiltroBandejaPendientesDTO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import java.util.List;

@NoneScoped
@ManagedBean
public class BandejaPendientesBusiness extends BaseBussinessBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{bandejaPendientesFacade}")
    private BandejaPendientesFacade bandejaPendientesFacade;

    public List<DatosBandejaPendientesDTO> obtenerPendientes(FiltroBandejaPendientesDTO filtroBandejaPendientesDTO) {
        return bandejaPendientesFacade.obtenerSolicitudes(filtroBandejaPendientesDTO);

    }

    public BandejaPendientesFacade getBandejaPendientesFacade() {
        return bandejaPendientesFacade;
    }

    public void setBandejaPendientesFacade(BandejaPendientesFacade bandejaPendientesFacade) {
        this.bandejaPendientesFacade = bandejaPendientesFacade;
    }

}
