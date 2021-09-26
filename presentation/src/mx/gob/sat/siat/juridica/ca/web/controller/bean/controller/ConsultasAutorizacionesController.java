/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.ca.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.web.controller.bean.controller.ConsultasController;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.ca.web.controller.bean.bussiness.ConsultasAutorizacionesBusiness;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.SolicitudConsultaAutorizacionDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.ConsultasConstantes;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * Controller que implementa la logica de negocio para las consultas
 * de autorizaciones
 * 
 * @author softtek
 * 
 */
@ViewScoped
@ManagedBean
public class ConsultasAutorizacionesController extends ConsultasController {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /** DTO que representa los datos del requerimiento */
    private SolicitudConsultaAutorizacionDTO solicitud;
    /**
     * Bussines que implenta la logica de negocio para las consultas a
     * autorizaciones.
     */
    @ManagedProperty("#{consultasAutorizacionesBusiness}")
    private transient ConsultasAutorizacionesBusiness consultasAutorizacionesBusiness;

    @PostConstruct
    public void inicio() {
        setDatosBandejaTareaDTO((DatosBandejaTareaDTO) getFlash().get(VistaConstantes.DATOS_BANDEJA_DTO));

        setIdSolicitud(getDatosBandejaTareaDTO().getIdSolicitud());
        setIdTipoTramite(getDatosBandejaTareaDTO().getTipoTramite());
        setNumFolio(getDatosBandejaTareaDTO().getNumeroAsunto());
        if (getIdSolicitud() != null) {
            setDatosSolicitud(consultasAutorizacionesBusiness.buscarSolicitante(getIdSolicitud()));
        }
    }

    /**
     * Metodo que realiza una busqueda de una solicitud con su tipo de
     * tramite y devuelve los datos del solicitante.
     */

    @Override
    public String getTabSolicitante() {

        return ConsultasConstantes.CONSULTA_DATOS_SOLICITANTE_CA;
    }

    /**
     * Metodo que devuelve los datos de un promocion.
     */
    @Override
    public String getTabTramite() {
        setSolicitud(consultasAutorizacionesBusiness.obtenerDatos(getIdSolicitud()));
        return ConsultasConstantes.CONSULTA_DATOS_PROMOCION;
    }

    /**
     * Metodo que obtiene los documentos de una solicitud.
     */
    @Override
    public String getTabDocumentos() {
        if (this.getNumFolio() == null) {
            setNumFolio((String) getFlash().get("numAsunto"));
        }
        obtenerDocumentosOficiales();
        setDocumentoDataModel(getDocumentosConsulta());
        return ConsultasConstantes.CONSULTA_DATOS_DOCUMENTOS_CA;
    }

    /**
     * 
     * @return consultasAutorizacionesBusiness
     */
    public ConsultasAutorizacionesBusiness getConsultasAutorizacionesBusiness() {
        return consultasAutorizacionesBusiness;
    }

    /**
     * 
     * @param consultasAutorizacionesBusiness
     *            el consultasAutorizacionesBusiness a fijar.
     */
    public void setConsultasAutorizacionesBusiness(ConsultasAutorizacionesBusiness consultasAutorizacionesBusiness) {
        this.consultasAutorizacionesBusiness = consultasAutorizacionesBusiness;
    }

    /**
     * 
     * @return solicitud
     */
    public SolicitudConsultaAutorizacionDTO getSolicitud() {
        return solicitud;
    }

    /**
     * 
     * @param solicitud
     *            la solicitud a fijar.
     */
    public void setSolicitud(SolicitudConsultaAutorizacionDTO solicitud) {
        this.solicitud = solicitud;
    }

}
