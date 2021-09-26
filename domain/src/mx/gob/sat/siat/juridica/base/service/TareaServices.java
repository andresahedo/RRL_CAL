package mx.gob.sat.siat.juridica.base.service;

import mx.gob.sat.siat.juridica.base.dao.domain.model.Tarea;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface TareaServices extends Serializable{



    /**
     * Metodo para guardar un tramite
     *
     * @param tarea
     */
    void guardarTarea(Tarea tarea);

    List<Tarea> buscarTareasporUsuario(String numAsunto,String idusuario, String clavePromovente, Date fechaInicio, Date fechaFin, String estadoProcesal);

    List<Tarea> buscarTareasporUsuarioReasignacion(String numAsunto,String idusuario, String abogado);

    String obtenerNumeroTareasEmpleado(String idUsuario);

    Tarea obtenerTareaPorTramiteActivo(String idTramite, Long idTarea);

    Tarea obtenerTareaPorTramiteRequerimientoAtendido(String idTramite, String claveTarea);

    List<Tarea> buscarTareasReasignar(String numeroAsunto, String administrador, String abogado);

}
