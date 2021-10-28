package mx.gob.sat.siat.juridica.base.api;

import mx.gob.sat.siat.juridica.base.dao.domain.model.DataPage;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.TareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.FiltroBandejaTareaDTO;

import java.io.Serializable;

public interface TareaFacade extends Serializable {

    void crearTarea(TareaDTO tarea);

    DataPage buscarTareasporUsuario(FiltroBandejaTareaDTO filtroBandejaTareaDTO);

    DataPage buscarTareasporUsuarioReasignacion(FiltroBandejaTareaDTO filtroBandejaTareaDTO);

	boolean tieneDocumentosAnexados(String idSolicitud);

	FirmaDTO obtieneFirma(DatosBandejaTareaDTO datoSelected);


}
