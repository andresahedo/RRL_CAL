package mx.gob.sat.siat.juridica.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sat.siat.juridica.base.constantes.ProcesosConstantes;
import mx.gob.sat.siat.juridica.base.dao.TareaDao;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EnumeracionBitacora;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoRequerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Requerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tarea;
import mx.gob.sat.siat.juridica.base.service.RequerimientoServices;
import mx.gob.sat.siat.juridica.base.service.TareaServices;
import mx.gob.sat.siat.juridica.base.service.TramiteServices;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.rrl.util.exception.FechaInvalidaException;

@Service("tareaService")
public class TareaServicesImpl extends BaseSerializableBusinessServices implements TareaServices{
    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -8153419578735328192L;

    /**
     * Atributo tramiteDao tipo TramiteDao
     */
    @Autowired
    private TareaDao tareaDao;
    
    @Autowired
    private transient RequerimientoServices requerimientoServices;
    
    @Autowired
    private transient TramiteServices tramiteServices;


    /**
     * Metodo para guardar un tramite
     *
     * @param tarea
     */
    @Override
    public void guardarTarea(Tarea tarea) {
        getLogger().debug("TareaServicesImpl : guardaTarea");
        tareaDao.guardarTarea(tarea);
    }

    public List<Tarea> buscarTareasporUsuario(String numAsunto, String idUsuario, String clavePromovente, Date fechaInicio, Date fechaFin, String estadoProcesal){
        this.procesarRequerimientos();
        getLogger().debug("TareaServicesImpl : buscarTareaporUsuario");
        List<Tarea> tareas = null;
        if (idUsuario != null && !idUsuario.isEmpty()) {
            tareas = tareaDao.obtenerTareasporUsuario(numAsunto,idUsuario,clavePromovente,fechaInicio,fechaFin,estadoProcesal);
        }
        return tareas;
    }

    public List<Tarea> buscarTareasporUsuarioReasignacion(String numAsunto, String idUsuario, String abogado){
        this.procesarRequerimientos();
        getLogger().debug("TareaServicesImpl : buscarTareaporUsuario");
        List<Tarea> tareas = null;
        if (idUsuario != null && !idUsuario.isEmpty()) {
            tareas = tareaDao.obtenerTareasporUsuarioReasignacion(numAsunto,idUsuario,abogado);
        }
        return tareas;
    }

    @Override
    public String obtenerNumeroTareasEmpleado(String idUsuario){
        getLogger().debug("TareaServicesImpl : obtenerTareasEmpleado");
        return tareaDao.obtenerNumeroTareasEmpleado(idUsuario).toString();
    }


    @Override
    public Tarea obtenerTareaPorTramiteActivo(String idTramite, Long idTarea){
        this.procesarRequerimientos();
        getLogger().debug("TareaServicesImpl : obtenerTareaPorTramiteActivo");
        return tareaDao.obtenerTareaPorTramiteActivo(idTramite, idTarea);
    }

    @Override
    public Tarea obtenerTareaPorTramiteRequerimientoAtendido(String idTramite, String claveTarea){
        this.procesarRequerimientos();
        getLogger().debug("TareaServicesImpl : obtenerTareaPorTramiteActivo");
        return tareaDao.obtenerTareaPorTramiteRequerimientoAtendido(idTramite, claveTarea);
    }

    @Override
    public List<Tarea> buscarTareasReasignar(String numeroAsunto, String administrador, String abogado) {
         this.procesarRequerimientos();
         getLogger().debug("TareaServicesImpl : buscarTareasReasignar");
         List<String> listaTramites = tramiteServices.obtenerAsuntosPorAdministrador(administrador);
         return listaTramites.isEmpty() ? new ArrayList<Tarea>() : tareaDao.buscarTareasReasignar(numeroAsunto, administrador, abogado, listaTramites);

    }
    
    private void procesarRequerimientos() {
        List<Tarea> listaTareas = this.obtenerTareaPorRequerimientoNoAtendido();
        getLogger().info("Procesando requerimientos:" + listaTareas.size());
        for (Tarea tarea : listaTareas) {
            int tareasAbogadorAsunto = 0;
            Requerimiento requerimiento = requerimientoServices.obtenerRequerimientoById(tarea.getIdRequerimeinto());
            requerimiento.setEstadoRequerimiento(EstadoRequerimiento.NO_ATENDIDO);
            requerimiento.setFechaAtencion(new Date());
            tarea.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_CANCELADO);
            try {
                requerimientoServices.actualizarRequerimiento(requerimiento);
                tareasAbogadorAsunto = this.obtenerTareasPorAsuntoAbogado(tarea.getNumeroAsunto(),
                        tarea.getClaveAsignado());
            } catch (FechaInvalidaException e) {
                getLogger().error(e.getMessage());
                getLogger().error("Ocurrio un error al procesar el requerimiento con id:"
                        + requerimiento.getIdRequerimiento() + "  del asunto:" + tarea.getNumeroAsunto());
            } finally {
                this.guardarTarea(tarea);
                if (tareasAbogadorAsunto == 0) {
                    Tarea tareaAI = new Tarea();
                    tareaAI.setNumeroAsunto(tarea.getNumeroAsunto());
                    tareaAI.setClaveAdm(tarea.getClaveAdm());
                    tareaAI.setClaveAsignado(requerimiento.getIdUsuario());
                    tareaAI.setTarea(EnumeracionBitacora.ATENDER_ASUNTO.getClave());
                    tareaAI.setDescripcion(EnumeracionBitacora.ATENDER_ASUNTO.getDescripcion());
                    tareaAI.setFechaCreacion(new Date());
                    tareaAI.setFechaAct(new Date());
                    tareaAI.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_PROCESO);
                    tareaAI.setClaveAbogado(tarea.getClaveAbogado());
                    this.guardarTarea(tareaAI);
                    try {
                        tramiteServices.modificaEstadoProcesal(tarea.getNumeroAsunto(),
                                EstadoTramite.EN_ESTUDIO.getClave());
                    } catch (SolicitudNoGuardadaException e) {
                        getLogger().error(e.getMessage());
                        getLogger().error("Ocurrio un error al procesar el requerimiento con id:"
                                + requerimiento.getIdRequerimiento() + "  del asunto:" + tarea.getNumeroAsunto());
                    }
                }
            }
        }
    }

    private int obtenerTareasPorAsuntoAbogado(String numeroAsunto, String claveAsignado) {
        getLogger().debug("TareaServicesImpl : obtenerTareaPorAsuntoAbogado");
        return tareaDao.obtenerTareasPorAsuntoAbogado(numeroAsunto, claveAsignado);
    }

    private List<Tarea> obtenerTareaPorRequerimientoNoAtendido() {
        getLogger().debug("TareaServicesImpl : obtenerTareaPorRequerimientoNoAtendido");
        return tareaDao.obtenerTareaPorRequerimientoNoAtendido();
    }

	@Override
	public List<Tarea> obtenerTareasPorNumAsunto(String numAsunto) {
		return tareaDao.obtenerTareasPorNumAsunto(numAsunto);
	}
}
