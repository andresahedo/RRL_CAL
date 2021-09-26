/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Turnar;
import mx.gob.sat.siat.juridica.base.dao.domain.model.UserState;
import mx.gob.sat.siat.juridica.base.service.UserStateServices;
import mx.gob.sat.siat.juridica.ca.service.DetallePromocionServices;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.TurnarDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DTOTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Clase Transformer para la Reasignacion y turnado de asuntos
 * 
 * @author Softtek - EQG
 * @since 14/11/2014
 */
@Component
public class TurnarDTOTransformer extends DTOTransformer<Turnar, TurnarDTO> {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private DetallePromocionServices detallePromocionServices;

    @Autowired
    private UserStateServices userStateServices;

    @Override
    public TurnarDTO transformarDTO(Turnar turnar) {
        TurnarDTO turnarDTO = new TurnarDTO();

        turnarDTO.setFechaAsignacion(turnar.getFechaTurnado());
        turnarDTO.setFechaBaja(turnar.getFechaBaja());
        turnarDTO.setIdTramite(turnar.getTramite().getNumeroAsunto());
        turnarDTO.setIdUsuarioAnterior(turnar.getUsuarioAnterior().getUserName());
        turnarDTO.setIdUsuarioAsigna(turnar.getUsuarioAsigna().getUserName());
        turnarDTO.setIdUsuarioNuevo(turnar.getUsuarioNuevo().getUserName());
        turnarDTO.setNombreTarea(turnar.getNombreTarea());
        turnarDTO.setObservacion(turnar.getDescObservacion());

        return turnarDTO;
    }

    public Turnar transformarDTO(TurnarDTO turnarDTO) {
        Turnar turnar = new Turnar();

        Tramite tramite = detallePromocionServices.buscarTramite(turnarDTO.getIdTramite(), null);
        UserState usuarioAsgina = userStateServices.buscarUsuario(turnarDTO.getIdUsuarioAsigna());
        UserState usuarioNuevo = userStateServices.buscarUsuario(turnarDTO.getIdUsuarioNuevo());

        turnar.setDescObservacion(turnarDTO.getObservacion());
        turnar.setFechaTurnado(turnarDTO.getFechaAsignacion());
        turnar.setNombreTarea(turnarDTO.getNombreTarea());
        turnar.setTramite(tramite);
        turnar.setUsuarioAsigna(usuarioAsgina);
        turnar.setUsuarioNuevo(usuarioNuevo);

        return turnar;
    }

}
