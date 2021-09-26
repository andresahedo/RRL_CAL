package mx.gob.sat.siat.juridica.rrl.op.web.bussines;

import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.AtenderObservacionBussines;
import mx.gob.sat.siat.juridica.rrl.api.AtenderObservacionRRLFacade;
import mx.gob.sat.siat.juridica.rrl.dto.DatosSolicitudDTO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import java.util.List;

@NoneScoped
@ManagedBean(name = "atenderObservacionRRLBussines")
public class AtenderObservacionRRLBussines extends AtenderObservacionBussines {

    @ManagedProperty("#{atenderObservacionRRLFacade}")
    private AtenderObservacionRRLFacade atenderObservacionRRLFacade;

    /**
     * 
     */
    private static final long serialVersionUID = 8551627749493953099L;

    public void guardar(DatosSolicitudDTO datosSolicitud) throws SolicitudNoGuardadaException {
        getAtenderObservacionRRLFacade().modificarSolicitud(datosSolicitud);
    }

    /**
     * @return the atenderObservacionRRLFacade
     */
    public AtenderObservacionRRLFacade getAtenderObservacionRRLFacade() {
        return atenderObservacionRRLFacade;
    }

    /**
     * @param atenderObservacionRRLFacade
     *            the atenderObservacionRRLFacade to set
     */
    public void setAtenderObservacionRRLFacade(AtenderObservacionRRLFacade atenderObservacionRRLFacade) {
        this.atenderObservacionRRLFacade = atenderObservacionRRLFacade;
    }

    public List<CatalogoDTO> obtenerAutoridadesEmisoras() {

        return getAtenderObservacionRRLFacade().obtenerAutoridadesEmisoras();

    }

    public DatosSolicitudDTO obtenerSolicitud(Long idSolicitud) {

        return getAtenderObservacionRRLFacade().obtenerSolicitud(idSolicitud);
    }

}
