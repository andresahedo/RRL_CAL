package mx.gob.sat.siat.juridica.base.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.model.Tarea;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface TareaDao extends Serializable {

     /**
     * Metodo para crear una tarea
     *
     * @param tarea
     */
    void guardarTarea(Tarea tarea);

    Tarea obtenerTareaPorTramiteActivo(String idTramite, Long idTarea);

    Tarea obtenerTareaPorTramiteRequerimientoAtendido(String idTramite, String claveTarea);

    List<Tarea> obtenerTareasporUsuario(String numAsunto,String idusuario, String clavePromovente, Date fechaInicio, Date fechaFin, String estadoProcesal);

    List<Tarea> obtenerTareasporUsuarioReasignacion(String numAsunto,String idusuario, String abogado);

    Long obtenerNumeroTareasEmpleado(String idUsuario);

    List<Tarea> buscarTareasReasignar(String numeroAsunto, String administrador, String abogado, List<String> listaTramites);

    List<Tarea> obtenerTareaPorRequerimientoNoAtendido();

    int obtenerTareasPorAsuntoAbogado(String numeroAsunto, String claveAsignado);

	List<Tarea> obtenerTareasPorNumAsunto(String numAsunto);

}
