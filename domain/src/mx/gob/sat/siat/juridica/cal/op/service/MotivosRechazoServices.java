package mx.gob.sat.siat.juridica.cal.op.service;

import mx.gob.sat.siat.juridica.base.dao.domain.model.MotivoRechazo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface MotivosRechazoServices extends Serializable {

    List<MotivoRechazo> obtenerMotivosRechazoOficialia();

    void rechazoAsuntoOficialia(MotivoRechazo motivoRechazo, String rfcAutorizador, String idTarea);

    void rechazoDocumento(MotivoRechazo motivoRechazo, String rfc);

    MotivoRechazo obtenerMotivoPorNumAsunto(String numeroAsunto);

    List<MotivoRechazo> obtenerTramitesDoctosRechazadosPorFiltros(String numeroAsunto, String estadoDocto,
            Date fechaIni, Date fechaFin, String rfc);

    String obtenerModalidadTramitePorClave(Integer tipoTramite);

}
