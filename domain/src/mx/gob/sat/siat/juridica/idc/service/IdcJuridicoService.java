/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.idc.service;

import com.softtek.idc.client.service.IdcService;
import com.softtek.idc.client.util.exception.ContribuyenteInactivoException;
import com.softtek.idc.client.util.exception.ContribuyenteNoEncontradoException;
import com.softtek.idc.client.util.exception.IDCException;
import com.softtek.idc.client.util.exception.RFCNoVigenteException;
import com.softtek.idc.common.exceptions.IDCNoDisponibleException;

/**
 * 
 * @author softtek
 * 
 */
public interface IdcJuridicoService extends IdcService {

    /**
     * Metodo para buscar un contribuyente
     * 
     * @param RFC
     * @param persona
     * @return
     * @throws IDCNoDisponibleException
     * @throws IDCException
     * @throws ContribuyenteNoEncontradoException
     * @throws ContribuyenteInactivoException
     * @throws RFCNoVigenteException
     * 
     */
    <T extends mx.gob.sat.siat.juridica.base.dao.domain.model.PersonaSolicitud> T buscarContribuyenteIdc(
            String rFC, T persona) throws IDCNoDisponibleException, IDCException, ContribuyenteNoEncontradoException,
            ContribuyenteInactivoException, RFCNoVigenteException;
}
