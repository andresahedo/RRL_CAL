/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.idc.service.impl;

import com.softtek.idc.client.dao.model.PersonaSolicitud;
import com.softtek.idc.client.dao.model.Sucursal;
import com.softtek.idc.client.service.impl.IdcServicePOJOImpl;
import com.softtek.idc.client.util.exception.ContribuyenteInactivoException;
import com.softtek.idc.client.util.exception.ContribuyenteNoEncontradoException;
import com.softtek.idc.client.util.exception.IDCException;
import com.softtek.idc.client.util.exception.RFCNoVigenteException;
import com.softtek.idc.common.exceptions.IDCNoDisponibleException;
import mx.gob.sat.siat.juridica.idc.service.IdcJuridicoService;
import mx.gob.sat.siat.juridica.idc.util.helper.IdcEntityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author softtek
 * 
 */
@Service("idcJuridicoService")
public class IdcJuridicoServiceImpl extends IdcServicePOJOImpl implements IdcJuridicoService {

    /**
     * Atributo privado "idcEntityHelper" tipo IdcEntityHelper
     */
    @Autowired
    private IdcEntityHelper idcEntityHelper;

    /**
     * Metodo para buscar un contribuyente
     */
    public <T extends mx.gob.sat.siat.juridica.base.dao.domain.model.PersonaSolicitud> T buscarContribuyenteIdc(
            String rFC, T persona) throws IDCNoDisponibleException, IDCException, ContribuyenteNoEncontradoException,
            ContribuyenteInactivoException, RFCNoVigenteException {
        return idcEntityHelper.setDatosPersonaSolicitud(super.buscarContribuyente(rFC), persona);
    }

    /**
     * Metodo para obtener el domicilio de una persona
     */
    @Override
    public PersonaSolicitud otenerDatosDomicilio(PersonaSolicitud persona) {
        return persona;
    }

    /**
     * Metodo para obtener el domicilio de una sucursal
     */
    @Override
    public Sucursal obtenerDatosDomicilioSucursal(Sucursal sucursal) {
        return sucursal;
    }

}
