package mx.gob.sat.siat.juridica.oficialia.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.model.MotivoRechazo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface MotivosDao extends Serializable {

    List<MotivoRechazo> obtenerMotivosRechazo();

    MotivoRechazo guardarMotivo(MotivoRechazo motivoDto);

    MotivoRechazo obtenermotivoPorNumAsunto(String numeroAsunto);

    List<MotivoRechazo> obtenerListaMotivoPorFiltros(String numeroAsunto, String estadoDocumentoSolicitud,
            Date fechaInicio, Date fechaFin, String rfc);

}
