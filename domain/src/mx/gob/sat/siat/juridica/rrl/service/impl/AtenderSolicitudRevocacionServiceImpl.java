/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.service.impl;

import mx.gob.sat.siat.juridica.base.dao.*;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.CatalogoD;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TareaOrigen;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoRol;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.service.impl.BaseBusinessServices;
import mx.gob.sat.siat.juridica.rrl.dao.SolicitudDAO;
import mx.gob.sat.siat.juridica.rrl.service.AtenderSolicitudRevocacionService;
import mx.gob.sat.siat.juridica.rrl.util.helper.ResolucionHelper;
import mx.gob.sat.siat.juridica.rrl.util.helper.ResolucionesImpugnadasHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Service
public class AtenderSolicitudRevocacionServiceImpl extends BaseBusinessServices implements
        AtenderSolicitudRevocacionService {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "catalogoDDao" tipo CatalogoDDao
     */
    @Autowired
    private CatalogoDDao catalogoDDao;

    /**
     * Atributo privado "personaDao" tipo PersonaDao
     */
    @Autowired
    private PersonaDao personaDao;

    /**
     * Atributo privado "tramiteDao" tipo TramiteDao
     */
    @Autowired
    private TramiteDao tramiteDao;

    @Autowired
    private SolicitudDAO solicitudDao;

    /**
     * Atributo privado "unidadAdministrativaDao" tipo
     * UnidadAdministrativaDao
     */
    @Autowired
    private UnidadAdministrativaDao unidadAdministrativaDao;

    /**
     * Atributo privado "resolucionDao" tipo ResolucionDao
     */
    @Autowired
    private ResolucionDao resolucionDao;

    /**
     * Atributo privado "resolucionHelper" tipo ResolucionHelper
     */
    @Autowired
    private ResolucionHelper resolucionHelper;

    @Autowired
    private DocumentoDao documentoDao;

    @Autowired
    private ResolucionDatosGeneradosDao resolucionDatosGeneradosDao;

    @Autowired
    private ResolucionesImpugnadasHelper resolucionesImpugnadasHelper;

    /**
     * Metodo para obtener informacion de un funcionario
     * 
     * @param idPersona
     * @return
     */
    @Override
    public Persona obtenerInformacionFuncionario(Long idPersona) {
        return personaDao.obtenerPersonaPorId(idPersona);
    }

    /**
     * Metodo para buscar un tramite
     * 
     * @param numeroAsunto
     * @param idSolicitud
     * @return
     */
    public Tramite buscarTramite(String numeroAsunto, Long idSolicitud) {
        Tramite tramite = null;
        if (numeroAsunto != null && !numeroAsunto.isEmpty()) {
            tramite = tramiteDao.obtenerTramitePorId(numeroAsunto);
        }
        else {
            tramite = tramiteDao.obtenerTramitePorIdSolicitud(idSolicitud);
        }
        return tramite;
    }

    /**
     * Metodo para consultar una solicitud
     * 
     * @param solicitud
     * @return
     */
    @Override
    public Solicitud consultarSolicitud(Solicitud solicitud) {
        getLogger().info("obteniendo solicitud por id {}", solicitud.getIdSolicitud());

        return null;
    }

    /**
     * Metodo para obtener una resolucion
     * 
     * @param idResolucion
     * @return
     */
    @Override
    public Resolucion obtenerResolucion(Long idResolucion) {
        Resolucion resolucion = null;
        if (idResolucion != null) {
            resolucion = resolucionDao.obtenerResolucion(idResolucion);
        }
        return resolucion;
    }

    /**
     * Metodo para obtener una resolucion por id de tramite
     * 
     * @param idTramite
     * @return
     */
    @Override
    public Resolucion obtenerResolucionIdTramite(String idTramite) {
        Resolucion resolucion = resolucionDao.obtenerResolucionIdTramite(idTramite);
        if (resolucion != null) {
            List<ResolucionImpugnada> resolsImpugnadas =
                    resolucionDao.obtenerResolucionesImpugnadas(resolucion.getIdResolucion());
            if (null != resolsImpugnadas && !resolsImpugnadas.isEmpty()) {
                for (ResolucionImpugnada resol : resolsImpugnadas) {
                    if (null != resol.getAgravios()) {
                        for (AgravioDetalle ag : resol.getAgravios()) {
                            ag.getMontoConcepto();
                        }
                    }
                }
            }

            resolucion.setResolucionesImpugnadas(resolsImpugnadas);
            resolucion
                    .setResolucionesCatalogoD(resolucionDao.obtenerResolucionesCatalogoD(resolucion.getIdResolucion()));
            if (resolucion.getResolucionesImpugnadas() != null && !resolucion.getResolucionesImpugnadas().isEmpty()) {
                for (ResolucionImpugnada resolucionImpugnada : resolucion.getResolucionesImpugnadas()) {
                    resolucionImpugnada.setConceptos(resolucionDao.obtenerConceptosPorIdImpugnada(resolucionImpugnada
                            .getIdResolucionImpugnada()));
                    resolucionImpugnada.setAgravios(resolucionDao.obtenerAgraviosPorIdImpugnada(resolucionImpugnada
                            .getIdResolucionImpugnada()));
                }
            }
        }
        return resolucion;
    }

    /**
     * Metodo para obtener los conceptos de una resolucion
     * 
     * @param resolucionImpugnada
     * @return
     */
    @Override
    public List<ConceptoDetalle> obtenerConceptos(ResolucionImpugnada resolucionImpugnada) {
        List<ConceptoDetalle> conceptos =
                resolucionDao.obtenerConceptosPorIdImpugnada(resolucionImpugnada.getIdResolucionImpugnada());
        return conceptos;
    }

    /**
     * Metodo para obtener unidad administrativa
     * 
     * @param idTramite
     * @return unidadAdministrativaDao
     */
    @Override
    public UnidadAdministrativa obtenerUnidadAdministrativaBalanceo(String idTramite) {
        Tramite tramite = tramiteDao.obtenerTramitePorId(idTramite);
        SolicitudDatosGenerales sol =
                solicitudDao.obtenerSolicitudDatosGeneralesPorId(tramite.getSolicitud().getIdSolicitud());

        return unidadAdministrativaDao.obtenerUnidadPorId(sol.getUnidadAdminBalanceo());
    }

    /**
     * Metodo para guardar una resolucion
     * 
     * @param resolucion
     */
    @Override
    public void guardar(Resolucion resolucionAbogado) {

        Resolucion resolucionBD =
                resolucionDao.obtenerResolucionIdTramite(resolucionAbogado.getTramite().getNumeroAsunto());

        if (resolucionBD != null) {
            resolucionAbogado.setIdResolucion(resolucionBD.getIdResolucion());

            resolucionBD.setMonto(resolucionAbogado.getMonto());

            List<ResolucionImpugnada> resolucionesImpugnadasBD =
                    resolucionDao.obtenerResolucionesImpugnadas(resolucionAbogado.getIdResolucion());

            resolucionDao
                    .eliminarResolucionesImpugnadasPorIdResolucion(resolucionAbogado.getIdResolucion(), new Date());

            resolucionDao.eliminarResolucionesDetallePorIdResolucion(resolucionAbogado.getIdResolucion(), new Date());

            String idsRImpugnadas = resolucionHelper.concatenaIds(resolucionesImpugnadasBD);

            resolucionDao.eliminarResolucionesImpugnadasDetallePorIdsImpugnadas(idsRImpugnadas, new Date());

            List<ResolucionImpugnada> resolucionesImpugnadas = resolucionAbogado.getResolucionesImpugnadas();
            resolucionAbogado.setResolucionesImpugnadas(null);

            List<ResolucionCatalogoD> resolucionesCatalogoD = resolucionAbogado.getResolucionesCatalogoD();
            resolucionAbogado.setResolucionesCatalogoD(null);

            if (resolucionesImpugnadas != null) {
                for (ResolucionImpugnada resolucionImpugnada : resolucionesImpugnadas) {
                    
                    resolucionImpugnada.setResolucion(resolucionBD);

                    List<ConceptoDetalle> conceptos = resolucionImpugnada.getConceptos();
                    resolucionImpugnada.setConceptos(null);

                    resolucionDao.crearResolucionImpugnada(resolucionImpugnada);
                    if (resolucionImpugnada.getBlnDeterminanteCredito() != null
                            && resolucionImpugnada.getBlnDeterminanteCredito().intValue() == 0) {
                        if (conceptos != null && !conceptos.isEmpty()) {
                            for (ConceptoDetalle conceptoDetalle : conceptos) {
                                conceptoDetalle.setResolucionImpugnada(resolucionImpugnada);

                                resolucionDao.crearResolucionImpugnadaCatalogoD(conceptoDetalle);
                            }
                        }
                    }

                    List<AgravioDetalle> agravios = resolucionImpugnada.getAgravios();
                    resolucionImpugnada.setAgravios(null);

                    if (agravios != null && !agravios.isEmpty()) {
                        for (AgravioDetalle agravioDetalle : agravios) {
                            agravioDetalle.setResolucionImpugnada(resolucionImpugnada);
                            resolucionDao.crearResolucionImpugnadaCatalogoD(agravioDetalle);
                        }
                    }

                }
            }

            if (resolucionesCatalogoD != null && !resolucionesCatalogoD.isEmpty()) {
                for (ResolucionCatalogoD resolucionCatalogoD : resolucionesCatalogoD) {
                    resolucionCatalogoD.setResolucion(resolucionAbogado);
                    resolucionDao.crearResolucionCatalogoD(resolucionCatalogoD);
                }
            }

        }
        else {
            List<ResolucionImpugnada> resolucionesImpugnadas = resolucionAbogado.getResolucionesImpugnadas();
            resolucionAbogado.setResolucionesImpugnadas(null);

            List<ResolucionCatalogoD> resolucionesCatalogoD = resolucionAbogado.getResolucionesCatalogoD();
            resolucionAbogado.setResolucionesImpugnadas(null);

            getLogger().debug("Nueva resolucion....[{}]", resolucionAbogado);
            // Agregar campos que vayan por default
            getLogger().debug("Resolucion devuelta....[{}]", resolucionAbogado);
            resolucionAbogado.setFechaInicioVigencia(new Date());
            resolucionAbogado.setFechaResolucion(new Date());
            Resolucion resolucionAbogadoLocal = resolucionDao.crearResolucion(resolucionAbogado);
            getLogger().debug("idResolucion CREADA...[{}]", resolucionAbogadoLocal.getIdResolucion());

            if (resolucionesImpugnadas != null) {
                for (ResolucionImpugnada resolucionImpugnada : resolucionesImpugnadas) {

                    resolucionImpugnada.setResolucion(resolucionAbogadoLocal);

                    List<ConceptoDetalle> conceptos = resolucionImpugnada.getConceptos();
                    resolucionImpugnada.setConceptos(null);

                    resolucionDao.crearResolucionImpugnada(resolucionImpugnada);
                    if (conceptos != null && !conceptos.isEmpty()) {
                        for (ConceptoDetalle conceptoDetalle : conceptos) {
                            conceptoDetalle.setResolucionImpugnada(resolucionImpugnada);

                            resolucionDao.crearResolucionImpugnadaCatalogoD(conceptoDetalle);
                        }
                    }

                }
            }

            if (resolucionesCatalogoD != null && !resolucionesCatalogoD.isEmpty()) {
                for (ResolucionCatalogoD resolucionCatalogoD : resolucionesCatalogoD) {
                    resolucionCatalogoD.setResolucion(resolucionAbogadoLocal);

                    resolucionDao.crearResolucionCatalogoD(resolucionCatalogoD);
                }
            }
        }

    }

    /**
     * Metodo para autorizar una resolucion
     * 
     * @param resolucionAbogado
     * @param idTramite
     */
    @Override
    public void autorizar(Resolucion resolucionAbogado, String idTramite) {

        List<ResolucionImpugnada> resolucionesImpugnadas = resolucionAbogado.getResolucionesImpugnadas();
        resolucionAbogado.setResolucionesImpugnadas(null);

        Resolucion resolucion = resolucionDao.obtenerResolucionIdTramite(idTramite);

        resolucion.setFechaFinVigencia(resolucionAbogado.getFechaFinVigencia());

        String ideSentidoResolucion = null;

        if (resolucionesImpugnadas != null && !resolucionesImpugnadas.isEmpty()) {
            for (ResolucionImpugnada resolucionImpugnada : resolucionesImpugnadas) {

                List<AgravioDetalle> agravios = resolucionImpugnada.getAgravios();
                resolucionImpugnada.setAgravios(null);

                ResolucionImpugnada resolucionImpugnadaBD =
                        resolucionDao.obtenerResolucionImpugnada(resolucionImpugnada.getIdResolucionImpugnada());
                if (resolucionImpugnada.isImportante()) {
                    CatalogoD catalogoD =
                            catalogoDDao.obtenerCatalogoPorClave(resolucionImpugnada.getSentidoResolucionImpugnada()
                                    .getClave());
                    ideSentidoResolucion = catalogoD.getDescripcionGenerica2();
                }
                CatalogoD sentidoResolucionImpugnada = resolucionImpugnada.getSentidoResolucionImpugnada();
                resolucionImpugnadaBD.setSentidoResolucionImpugnada(sentidoResolucionImpugnada);

                if (agravios != null && !agravios.isEmpty()) {
                    for (AgravioDetalle agravioDetalle : agravios) {
                        agravioDetalle.setResolucionImpugnada(resolucionImpugnadaBD);

                        resolucionDao.crearResolucionImpugnadaCatalogoD(agravioDetalle);
                    }
                }
            }
        }

        resolucion.setIdeSentidoResolucion(ideSentidoResolucion);

    }

    /**
     * Metodo para firmar una resolucion
     * 
     * @param resolucion
     */
    @Override
    public void firmar(Resolucion resolucionAbogado) {
        resolucionDao.modificarResolucion(resolucionAbogado);
    }

    /**
     * Metodo para obtener autorizadores
     * 
     * @param numeroAsunto
     * @return
     */
    public List<Persona> obtenerAutorizadores(String numeroAsunto) {
        return personaDao.buscarPersonaSinUARByRol(tramiteDao.obtenerTramitePorId(numeroAsunto),
                TipoRol.ADMINISTRADOR.getClave());
    }

    /**
     * Metodo para obtener un tramite por numero de resolucion
     * 
     * @param numResolucion
     * @return
     */
    @Override
    public Tramite obtenerTramite(String numResolucion, String numAsunto) {
        return tramiteDao.obtenerTramitePorResolucion(numResolucion, numAsunto);
    }

    /**
     * Metodo para obtener unidad administrativa
     * 
     * @param idTramite
     * @return unidadAdministrativaDao
     */
    @Override
    public UnidadAdministrativa obtenerUnidadAdministrativa(String idTramite) {
        return unidadAdministrativaDao.obtenerUnidadPorTramite(idTramite);
    }

    @Override
    public List<DocumentoSolicitud> obtenerDocumentosComplementarios(Long idSolicitud) {
        return documentoDao.obtenerDocumentosComplementarios(idSolicitud);

    }

    @Override
    public void guardarResolucionDatosGenerados(ResolucionDatosGenerados resolucionDatosGenerados) {
        ResolucionDatosGenerados res =
                resolucionDatosGeneradosDao.obtenerResolucionDatosGeneradosIdResolucion(resolucionDatosGenerados
                        .getResolucion().getIdResolucion());
        if (res == null) {
            resolucionDatosGeneradosDao.crearResolucionDatosGenerados(resolucionDatosGenerados);
        }
        else {
            resolucionDatosGeneradosDao.modificarResolucionDatosGenerados(resolucionDatosGenerados);
        }
    }

    @Override
    public String getIdeTareaOrigen() {
        return TareaOrigen.ATENDER_ASUNTO.getClave();
    }

    @Override
    public Tramite obtenerTramiteParaMensajeAviso(String numResolucion, String numAsunto) {
        boolean resp = false;
        Tramite tramite = tramiteDao.obtenerTramitePorResolucion(numResolucion, numAsunto);
        if (tramite != null) {
            resp = resolucionesImpugnadasHelper.validacionEstadoTramiteResolucion(tramite);
        }
        if (resp) {
            return tramite;
        }
        else {
            return null;
        }

    }

}
