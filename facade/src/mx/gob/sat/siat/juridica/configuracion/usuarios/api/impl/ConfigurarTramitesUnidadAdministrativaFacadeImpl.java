/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.api.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.AccionesBitacoraConstants;
import mx.gob.sat.siat.juridica.base.dao.domain.model.BitacoraAU;
import mx.gob.sat.siat.juridica.base.dao.domain.model.TipoTramiteUnidadAdministrativaCatalogo;
import mx.gob.sat.siat.juridica.base.dto.TipoTramiteDTO;
import mx.gob.sat.siat.juridica.base.dto.UnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.UnidadAdministrativaDTOTransformer;
import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.base.service.UnidadAdministrativaServices;
import mx.gob.sat.siat.juridica.cal.dto.transformer.TipoTramiteDTOTransformer;
import mx.gob.sat.siat.juridica.configuracion.usuarios.api.ConfigurarTramitesUnidadAdministrativaFacade;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.BitacoraDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.transformer.BitacoraAUDTOTransformer;
import mx.gob.sat.siat.juridica.configuracion.usuarios.service.BitacorAUService;
import mx.gob.sat.siat.juridica.configuracion.usuarios.service.ConfigurarTramitesUnidadAdministrativaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author softtek
 * 
 */
@Component("configurarTramitesUnidadAdministrativaFacade")
public class ConfigurarTramitesUnidadAdministrativaFacadeImpl extends BaseFacadeImpl implements
        ConfigurarTramitesUnidadAdministrativaFacade {

    /**
     * 
     */
    private static final long serialVersionUID = -4037962765594088602L;

    @Autowired
    private transient ConfigurarTramitesUnidadAdministrativaService configurarService;

    @Autowired
    private transient UnidadAdministrativaServices unidadService;

    @Autowired
    private UnidadAdministrativaDTOTransformer unidadTransformer;

    @Autowired
    private TipoTramiteDTOTransformer tipoTramiteDTOTransformer;

    @Autowired
    private BitacorAUService bitacorAUService;

    @Autowired
    private BitacoraAUDTOTransformer bitacoraTransformer;

    @Override
    public List<UnidadAdministrativaDTO> comboUnidades() {
        List<UnidadAdministrativa> listaUnidades = unidadService.obtenerCatalogo();
        return unidadTransformer.transformarDTO(listaUnidades);
    }

    @Override
    public List<TipoTramiteDTO> buscarTramites(String idUnidad) {

        Map<Integer, String> servicioSelect = new HashMap<Integer, String>();
        List<TipoTramiteUnidadAdministrativaCatalogo> modalidadesUnidad =
                configurarService.buscarTramitesUnidad(idUnidad);
        List<TipoTramiteUnidadAdministrativaCatalogo> modalidadesSelect =
                new ArrayList<TipoTramiteUnidadAdministrativaCatalogo>();
        List<TipoTramiteDTO> tramites = this.obtenerServicios();
        for (TipoTramiteUnidadAdministrativaCatalogo modalidad : modalidadesUnidad) {
            if (modalidad.getVigencia().getBlnActivo() != null && modalidad.getVigencia().getBlnActivo().equals(1)) {
                modalidadesSelect.add(modalidad);
            }
        }

        if (modalidadesSelect != null && !modalidadesSelect.isEmpty()) {

            for (TipoTramiteUnidadAdministrativaCatalogo mod : modalidadesSelect) {
                int idServicio = Integer.parseInt(mod.getTipoTramite().getServicio());
                String activo = mod.getVigencia().getBlnActivo().toString();
                if (!servicioSelect.containsKey(idServicio)) {
                    servicioSelect.put(idServicio, activo);
                }
            }
        }

        for (TipoTramiteDTO serv : tramites) {
            if (servicioSelect.containsKey(Integer.parseInt(serv.getServicio()))) {
                serv.setBlnServicioSelect(true);
            }
            else {
                serv.setBlnServicioSelect(false);
            }
        }

        return tramites;
    }

    @Override
    public List<TipoTramiteDTO> obtenerServicios() {
        List<TipoTramiteDTO> servicios = new ArrayList<TipoTramiteDTO>();
        List<TipoTramite> servs = configurarService.obtenerServicios();
        for (TipoTramite tram : servs) {
            TipoTramiteDTO tramDTO = tipoTramiteDTOTransformer.transformarDTO(tram);
            servicios.add(tramDTO);
        }
        return servicios;
    }

    @Override
    public void actualizarTramitesUnidad(List<TipoTramiteDTO> modalidades, String idUnidadAdministrativa) {
        List<TipoTramite> tramiteList = tipoTramiteDTOTransformer.transformarModel(modalidades);
        configurarService.actualizarTramitesUnidad(tramiteList, idUnidadAdministrativa);

        // Guardado en bitacora de AU
        for (TipoTramite tramite : tramiteList) {
            BitacoraDTO bitacoraDTO = new BitacoraDTO();

            bitacoraDTO.setDescripcion(tramite.getDescripcionModalidad());
            bitacoraDTO.setIdTipoTramite(tramite.getIdTipoTramite());
            if (tramite.getModalidad() != null) {
                bitacoraDTO.setModalidad(Long.parseLong(tramite.getModalidad()));
            }
            bitacoraDTO.setRfcEmpleadoRealiza(getUserProfile().getRfc());
            bitacoraDTO.setRfcAplicadoA(getUserProfile().getRfc());
            bitacoraDTO.setIdUniAdmin(idUnidadAdministrativa);
            bitacoraDTO.setIdRol(getUserProfile().getRol());

            bitacoraDTO.setIdRealizadoPor(AccionesBitacoraConstants.ACCION_REALIZA_EMPLEADO);
            bitacoraDTO.setIdAplicadoA(AccionesBitacoraConstants.ACCION_REALIZA_EMPLEADO_APLICA);
            bitacoraDTO.setIdAccion(AccionesBitacoraConstants.ACTUALIZAR_TRAMITES_UA);

            BitacoraAU bitacora = bitacoraTransformer.tranformar(bitacoraDTO);
            bitacorAUService.guardarDatosBitacora(bitacora);
        }

    }

}
