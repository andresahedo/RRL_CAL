/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.cal.service.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.constants.TareaOrigen;
import mx.gob.sat.siat.juridica.base.service.impl.BaseSerializableBusinessServices;
import mx.gob.sat.siat.juridica.cal.service.AtencionPromocionCALServices;
import org.springframework.stereotype.Service;

/**
 * Servicio para atender las peticiones del registro de la solicitud.
 * 
 * @author Softtek
 * 
 */
@Service("atencionPromocionCALServices")
public class AtencionPromocionCALServicesImpl extends BaseSerializableBusinessServices implements
        AtencionPromocionCALServices {

    /**
     * Id
     */
    private static final long serialVersionUID = 227182020765630398L;

    @Override
    public String getIdeTareaOrigen() {
        return TareaOrigen.ATENDER_ASUNTO.getClave();
    }

}
