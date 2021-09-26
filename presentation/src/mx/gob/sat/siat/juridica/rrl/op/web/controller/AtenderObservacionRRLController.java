/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
/**
 * 
 */
package mx.gob.sat.siat.juridica.rrl.op.web.controller;

import mx.gob.sat.siat.juridica.base.constantes.RolesConstantes;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.base.web.controller.bean.controller.AtenderObservacionController;
import mx.gob.sat.siat.juridica.rrl.dto.DatosSolicitudDTO;
import mx.gob.sat.siat.juridica.rrl.op.web.bussines.AtenderObservacionRRLBussines;
import mx.gob.sat.siat.juridica.rrl.util.constante.UrlFirma;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * Bean que implementan la l&oacute;gica de negocio para atender un
 * requerimiento
 * 
 * @author Softtek
 * 
 */
@ManagedBean
@ViewScoped
public class AtenderObservacionRRLController extends AtenderObservacionController<DatosSolicitudDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = -5125775626708013442L;

    private DatosSolicitudDTO solicitud;

    @ManagedProperty(value = "#{atenderObservacionRRLBussines}")
    private AtenderObservacionRRLBussines atenderObservacionRRLBussines;

    /** Lista de unidades emisoras */
    private List<CatalogoDTO> listaUnidadesEmisoras = new ArrayList<CatalogoDTO>();

    @Override
    protected DatosSolicitudDTO obtenerSolicitud() {
        // TODO Auto-generated method stub
        return getAtenderObservacionRRLBussines().obtenerSolicitud(getDatosBandejaTareaDTO().getIdSolicitud());
    }

    @Override
    public void iniciar() {
        super.iniciar();
        setListaUnidadesEmisoras(getAtenderObservacionRRLBussines().obtenerAutoridadesEmisoras());
        setBanderaRazonSocial(getSolicitud().getRazonSocial() != null ? true : false);
    }

    /**
     * @return the solicitud
     */
    @Override
    public DatosSolicitudDTO getSolicitud() {
        return solicitud;

    }

    @Override
    public void setSolicitud(DatosSolicitudDTO solicitud) {
        this.solicitud = solicitud;

    }

    @Override
    public String getDireccionFirma() {
        return UrlFirma.PAGINA_FIRMA_OBSERVACION_RRL.toString();
    }

    @Override
    public void guardar() throws SolicitudNoGuardadaException {
        getAtenderObservacionRRLBussines().guardar(getSolicitud());

    }

    /**
     * @return the atenderObservacionRRLBussines
     */
    public AtenderObservacionRRLBussines getAtenderObservacionRRLBussines() {
        return atenderObservacionRRLBussines;
    }

    /**
     * @param atenderObservacionRRLBussines
     *            the atenderObservacionRRLBussines to set
     */
    public void setAtenderObservacionRRLBussines(AtenderObservacionRRLBussines atenderObservacionRRLBussines) {
        this.atenderObservacionRRLBussines = atenderObservacionRRLBussines;
    }

    /**
     * @return the listaUnidadesEmisoras
     */
    public List<CatalogoDTO> getListaUnidadesEmisoras() {
        return listaUnidadesEmisoras;
    }

    /**
     * @param listaUnidadesEmisoras
     *            the listaUnidadesEmisoras to set
     */
    public void setListaUnidadesEmisoras(List<CatalogoDTO> listaUnidadesEmisoras) {
        this.listaUnidadesEmisoras = listaUnidadesEmisoras;
    }

    public void validaAcceso() {
        // Validacion de acceso a la funcionalidad
        this.validaAccesoRolEmpleado(RolesConstantes.ROL_EMPLEADO_OFICIAL_PARTES);
    }
}
