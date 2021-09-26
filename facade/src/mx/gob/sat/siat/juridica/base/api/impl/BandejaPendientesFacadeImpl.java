package mx.gob.sat.siat.juridica.base.api.impl;

import mx.gob.sat.siat.juridica.base.api.BandejaPendientesFacade;
import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.DiscriminadorConstants;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Solicitud;
import mx.gob.sat.siat.juridica.base.dto.transformer.BandejaPendientesDTOTransformer;
import mx.gob.sat.siat.juridica.base.service.TipoTramiteServices;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaPendientesDTO;
import mx.gob.sat.siat.juridica.rrl.dto.FiltroBandejaPendientesDTO;
import mx.gob.sat.siat.juridica.rrl.service.SolicitudService;
import mx.gob.sat.siat.juridica.rrl.util.helper.DiasHabilesHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component("bandejaPendientesFacade")
public class BandejaPendientesFacadeImpl implements BandejaPendientesFacade {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private transient SolicitudService solicitudService;

    @Autowired
    private transient TipoTramiteServices tipoTramiteServices;

    @Autowired
    private transient DiasHabilesHelper diasHabilesHelper;

    @Autowired
    private transient BandejaPendientesDTOTransformer bandejaPendientesDTOTransformer;

    @SuppressWarnings("deprecation")
    @Override
    public List<DatosBandejaPendientesDTO> obtenerSolicitudes(FiltroBandejaPendientesDTO filtroBandejaPendientesDTO) {
        Calendar fechaIni = Calendar.getInstance();
        Calendar fechaFin = Calendar.getInstance();
        if (filtroBandejaPendientesDTO.getFechaInicio() != null && filtroBandejaPendientesDTO.getFechaFin() != null) {
            fechaIni.setTime(filtroBandejaPendientesDTO.getFechaInicio());
            fechaIni.set(Calendar.HOUR_OF_DAY, 0);
            fechaIni.set(Calendar.MINUTE, 0);
            fechaIni.set(Calendar.SECOND, 0);
            fechaIni.set(Calendar.MILLISECOND, 0);
            fechaFin = Calendar.getInstance();
            fechaFin.setTime(filtroBandejaPendientesDTO.getFechaFin());
            fechaFin.set(Calendar.HOUR_OF_DAY, NumerosConstantes.VEINTITRES);
            fechaFin.set(Calendar.MINUTE, NumerosConstantes.CINCUENTA_NUEVE);
            fechaFin.set(Calendar.SECOND, NumerosConstantes.CINCUENTA_NUEVE);
            fechaFin.set(Calendar.MILLISECOND, NumerosConstantes.CINCUENTA_NUEVE);
        }
        else {
            Date diferenciaActual = new Date();
            diferenciaActual.setDate(new Date().getDate() - NumerosConstantes.TRES);

            fechaIni.setTime(new Date());
            fechaIni.set(Calendar.HOUR_OF_DAY, 0);
            fechaIni.set(Calendar.MINUTE, 0);
            fechaIni.set(Calendar.SECOND, 0);
            fechaIni.set(Calendar.MILLISECOND, 0);

            long finSemana = diasHabilesHelper.buscaFinSemana(diferenciaActual, new Date());

            fechaIni.set(Calendar.DATE, (int) (fechaIni.getTime().getDate() - (NumerosConstantes.TRES + finSemana)));

            fechaFin.setTime(new Date());
            fechaFin.set(Calendar.HOUR_OF_DAY, NumerosConstantes.VEINTITRES);
            fechaFin.set(Calendar.MINUTE, NumerosConstantes.CINCUENTA_NUEVE);
            fechaFin.set(Calendar.SECOND, NumerosConstantes.CINCUENTA_NUEVE);
            fechaFin.set(Calendar.MILLISECOND, NumerosConstantes.CINCUENTA_NUEVE);
        }
        List<Solicitud> listaSolicitudes =
                solicitudService.obtenerSolicitudesPorFiltros(filtroBandejaPendientesDTO.getIdSolicitud(),
                        fechaIni.getTime(), fechaFin.getTime(), filtroBandejaPendientesDTO.getRfcPromovente());
        List<Solicitud> listaSolicitudesReturn = new ArrayList<Solicitud>();
        for (Solicitud solicitud : listaSolicitudes) {
            // La busqueda de solicitudes pendientes, solo aplica para
            // T2 (CAL) pues es el unico tramite que contempla
            // guardado parcial
            // Ver: 21_639_ECU_046ConsultarPromocionesPendientes
            // Cambio por: edgar.mendoza (22/05/2014), por observacion
            // de CPN
            if (solicitud.getTipoTramite().getIdTipoTramite().toString()
                    .startsWith(DiscriminadorConstants.T2_TIPO_TRAMITE_PREFIJO)) {
                TipoTramite tipoTramite =
                        tipoTramiteServices.obtenerTipoTramiteId(solicitud.getTipoTramite().getIdTipoTramite());
                solicitud.setTipoTramiteSolicitud(tipoTramite);
                solicitud.getTipoTramite().setDescripcionModalidad((tipoTramite.getDescripcionModalidad()));
                listaSolicitudesReturn.add(solicitud);
            }
        }

        return bandejaPendientesDTOTransformer.transformarDTO(listaSolicitudesReturn);
    }

}
