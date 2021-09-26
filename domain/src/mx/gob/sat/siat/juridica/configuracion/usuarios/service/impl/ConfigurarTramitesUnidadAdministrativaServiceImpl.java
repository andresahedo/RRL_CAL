/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.service.impl;

import mx.gob.sat.siat.juridica.base.dao.TipoTramiteDAO;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Vigencia;
import mx.gob.sat.siat.juridica.base.dao.domain.model.TipoTramiteUnidadAdministrativaCatalogo;
import mx.gob.sat.siat.juridica.base.service.impl.BaseSerializableBusinessServices;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.TipoTramiteConfigurarDao;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.TipoTramiteUnidadAdministrativaDao;
import mx.gob.sat.siat.juridica.configuracion.usuarios.service.ConfigurarTramitesUnidadAdministrativaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author softtek
 * 
 */
@Service
public class ConfigurarTramitesUnidadAdministrativaServiceImpl extends BaseSerializableBusinessServices implements
        ConfigurarTramitesUnidadAdministrativaService {

    /**
     * 
     */
    private static final long serialVersionUID = 5547554971231661788L;

    @Autowired
    private TipoTramiteUnidadAdministrativaDao tipoTramiteUnidadDao;

    @Autowired
    private TipoTramiteDAO tipoTramiteDao;

    @Autowired
    private TipoTramiteConfigurarDao tramiteConfigurarDao;

    @Override
    public List<TipoTramiteUnidadAdministrativaCatalogo> buscarTramitesUnidad(String claveUnidad) {
        List<TipoTramiteUnidadAdministrativaCatalogo> lista = tipoTramiteUnidadDao.buscarTramitesUnidad(claveUnidad);
        for (TipoTramiteUnidadAdministrativaCatalogo servicio : lista) {
            servicio.getTipoTramite().getServicio();
            servicio.getTipoTramite().getBlnActivo();
        }

        return lista;
    }

    @Override
    public Integer validarServicio(String idServicio, String idUnidadAdministrativa) {

        return tramiteConfigurarDao.validarServicio(idServicio, idUnidadAdministrativa);
    }

    @Override
    public List<TipoTramite> obtenerServicios() {

        return tipoTramiteDao.buscarTramites();
    }

    @Override
    public void actualizarTramitesUnidad(List<TipoTramite> modalidades, String idUnidadAdministrativa) {
        Map<Integer, String> servicioSelect = new HashMap<Integer, String>();
        List<TipoTramite> modalidadesAsignar = tipoTramiteDao.todasModalidades();
        List<TipoTramiteUnidadAdministrativaCatalogo> tramitesGuardar =
                new ArrayList<TipoTramiteUnidadAdministrativaCatalogo>();
        for (TipoTramite tr : modalidades) {
            int servicio = Integer.parseInt(tr.getServicio());
            String activo = tr.getBlnServicioSelect().toString();
            servicioSelect.put(servicio, activo);

        }
        List<TipoTramiteUnidadAdministrativaCatalogo> modUnidad = this.buscarTramitesUnidad(idUnidadAdministrativa);

        for (TipoTramite tipo : modalidadesAsignar) {
            TipoTramiteUnidadAdministrativaCatalogo modalidadAsignada = new TipoTramiteUnidadAdministrativaCatalogo();
            modalidadAsignada.setTipoTramite(tipo);
            modalidadAsignada.setUnidadAdministrativa(new UnidadAdministrativa(idUnidadAdministrativa));
            Vigencia vigencia = new Vigencia();
            if (servicioSelect.size() > 0) {
                if (servicioSelect.containsKey(Integer.parseInt(tipo.getServicio()))) {
                    vigencia.setBlnActivo(1);
                    modalidadAsignada.setVigencia(vigencia);
                }
                else {
                    vigencia.setBlnActivo(0);
                    modalidadAsignada.setVigencia(vigencia);
                }
            }
            else {
                vigencia.setBlnActivo(0);
                modalidadAsignada.setVigencia(vigencia);
            }

            tramitesGuardar.add(modalidadAsignada);
        }

        if (modUnidad.size() > 0) {
            for (TipoTramiteUnidadAdministrativaCatalogo mod : modUnidad) {
                for (TipoTramiteUnidadAdministrativaCatalogo tramite : tramitesGuardar) {
                    if (mod.getTipoTramite().getIdTipoTramite().equals(tramite.getTipoTramite().getIdTipoTramite())) {
                        tramite.setIdTipoTramiteUniAdmin(mod.getIdTipoTramiteUniAdmin());
                        tipoTramiteUnidadDao.actualizarTramitesUnidad(tramite);
                    }
                }
            }
        }
        else {
            for (TipoTramiteUnidadAdministrativaCatalogo tramite : tramitesGuardar) {
                tipoTramiteUnidadDao.actualizarTramitesUnidad(tramite);
            }
        }
    }

}
