package mx.gob.sat.siat.juridica.health.service.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.model.UsuarioSincronizar;
import mx.gob.sat.siat.juridica.base.service.BaseBusinessServices;
import mx.gob.sat.siat.juridica.base.service.UsuarioSincronizarService;
import mx.gob.sat.siat.juridica.health.dto.HealthDTO;
import mx.gob.sat.siat.juridica.health.dto.UsuarioSincronizarDTO;
import mx.gob.sat.siat.juridica.health.service.HealthCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;



@Service
public class HealthCheckServiceImpl extends BaseBusinessServices implements
        HealthCheckService {
    
   
    @Autowired
    private UsuarioSincronizarService usuarioSincronizarService;
    


    @Override
    public HealthDTO obtenerResultadosSincronizacion(Boolean permitirEjecucion) {
        if (permitirEjecucion != null) {
            usuarioSincronizarService.setPermitirEjecucion(permitirEjecucion);
        }

        Date ultimaFechaSincronizacion = usuarioSincronizarService.obtenerUltimaFechaSincronizacion();
        List<UsuarioSincronizar> usuariosSincronizar = usuarioSincronizarService.obtenerUsuariosASincronizar();
        boolean permiteEjecucion = usuarioSincronizarService.getPermitirEjecucion();
        List<UsuarioSincronizarDTO> usuariosDTO = new LinkedList<UsuarioSincronizarDTO>();

        for (UsuarioSincronizar usuarioSincronizar : usuariosSincronizar) {
            UsuarioSincronizarDTO usuarioSincronizarDTO = new UsuarioSincronizarDTO();
            usuarioSincronizarDTO.setActivo(usuarioSincronizar.getActivo());
            usuarioSincronizarDTO.setFecha(usuarioSincronizar.getFecha());
            usuarioSincronizarDTO.setRfcCorto(usuarioSincronizar.getRfcCorto());
            usuariosDTO.add(usuarioSincronizarDTO);
        }

        HealthDTO healthDTO = new HealthDTO();
        healthDTO.setFechaUltimaSincronizacion(ultimaFechaSincronizacion);
        healthDTO.setPermitirEjecucionSincronizacion(permiteEjecucion);
        healthDTO.setUsuariosSincronizacion(usuariosDTO);
        return healthDTO;
    }

}
