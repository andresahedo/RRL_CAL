package mx.gob.sat.siat.juridica.cal.service;

import mx.gob.sat.siat.juridica.base.dao.domain.model.Firma;
import mx.gob.sat.siat.juridica.rrl.service.SolicitudService;

import java.util.List;

public interface SolicitudCALServices extends SolicitudService {

    List<Object> firmarSolicitud(Long idSolicitud, String rfcSolicitante, String usuario, Firma firma);

}
