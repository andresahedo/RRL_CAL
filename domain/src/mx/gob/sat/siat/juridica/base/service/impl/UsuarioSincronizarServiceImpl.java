package mx.gob.sat.siat.juridica.base.service.impl;

import mx.gob.sat.siat.juridica.base.dao.EnumeracionDao;
import mx.gob.sat.siat.juridica.base.dao.UsuarioSincronizarDAO;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoParametroSincronizacion;
import mx.gob.sat.siat.juridica.base.dao.domain.model.UsuarioSincronizar;
import mx.gob.sat.siat.juridica.base.service.UsuarioSincronizarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("usuarioSincronizarService")
public class UsuarioSincronizarServiceImpl extends BaseBusinessServices implements UsuarioSincronizarService {

    @Autowired
    private UsuarioSincronizarDAO usuarioSincronizarDAO;


    @Autowired
    private EnumeracionDao enumeracionDao;



    @Override
    public List<UsuarioSincronizar> obtenerUsuariosASincronizar() {
        return usuarioSincronizarDAO.obtenerUsuariosSincronizarNoActivos();
    }




    @Override
    public void setPermitirEjecucion(boolean permitirEjecucion) {
        String permite = permitirEjecucion ? "1" : "0";
        enumeracionDao.guardarParametro(TipoParametroSincronizacion.PERMITE_SINCRONIZACION.getId(),
                TipoParametroSincronizacion.PERMITE_SINCRONIZACION.getDescripcion(), permite,
                new Date(), null, true);
    }

    @Override
    public boolean getPermitirEjecucion() {
        boolean permiteEjecucion = true;

        try {
            String valor = enumeracionDao.obtenerParametroByEnum(TipoParametroSincronizacion.PERMITE_SINCRONIZACION.getId());
            permiteEjecucion = valor.equals("0") ? false : true;
        } catch (Exception ex) {
            getLogger().error("Error al intentar obtener parametro");
        }
        return permiteEjecucion;
    }

    @Override
    public Date obtenerUltimaFechaSincronizacion() {
        try {
            String fechaString = enumeracionDao.obtenerParametroByEnum(
                    TipoParametroSincronizacion.ULTIMA_FECHA_SINCRONIZACION.getId());
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            return dateFormat.parse(fechaString);
        } catch (ParseException ex) {
            return new Date();
        }
    }

}
