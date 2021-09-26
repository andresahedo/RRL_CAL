package mx.gob.sat.siat.juridica.base.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.constants.DiscriminadorConstants;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Solicitud;
import mx.gob.sat.siat.juridica.ca.util.constants.RegistroSolicitudConstants;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaPendientesDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DTOTransformer;
import mx.gob.sat.siat.juridica.rrl.util.helper.DiasHabilesHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class BandejaPendientesDTOTransformer extends DTOTransformer<List<Solicitud>, List<DatosBandejaPendientesDTO>> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private transient DiasHabilesHelper diasHabilesHelper;

    @Override
    public List<DatosBandejaPendientesDTO> transformarDTO(List<Solicitud> source) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        List<DatosBandejaPendientesDTO> listaBandejaPendientes = new ArrayList<DatosBandejaPendientesDTO>();
        DatosBandejaPendientesDTO bandejaPendientesDTO;
        for (Solicitud solicitud : source) {
            bandejaPendientesDTO = new DatosBandejaPendientesDTO();
            bandejaPendientesDTO.setIdSolicitud(solicitud.getIdSolicitud());
            bandejaPendientesDTO.setFechaCreacionStr(dateFormat.format(solicitud.getFechaCreacion()));
            bandejaPendientesDTO.setTipoTramite(solicitud.getTipoTramite().getDescripcionModalidad());
            bandejaPendientesDTO.setDiasHabilesTranscurridos(diasHabilesHelper.calcularDiasTranscurridos(
                    solicitud.getFechaCreacion(), new Date()).toString());
            if (solicitud.getTipoTramite().getIdTipoTramite().toString()
                    .startsWith(DiscriminadorConstants.T2_TIPO_TRAMITE_PREFIJO)) {
                bandejaPendientesDTO.setUrl(RegistroSolicitudConstants.CAPTURA_SOLICITUD_CAL);
            }
            else {
                bandejaPendientesDTO.setUrl("/resources/pages/rrl/capturaSolicitud.jsf");
            }
            listaBandejaPendientes.add(bandejaPendientesDTO);
        }
        return listaBandejaPendientes;
    }

}
