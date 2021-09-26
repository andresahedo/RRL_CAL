/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */

package mx.gob.sat.siat.juridica.rrl.util.helper;

import mx.gob.sat.siat.juridica.base.dao.RemisionDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoUnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Remision;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.base.helper.BaseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author softtek
 * 
 */

@Component
public class ResolucionesImpugnadasHelper extends BaseHelper {

    /**
     * 
     */
    private static final long serialVersionUID = 521195774748717097L;

    @Autowired
    private RemisionDao remisionDao;

    public boolean validacionEstadoTramiteResolucion(Tramite tramite) {

        String clave = tramite.getEstadoTramite().getClave();
        boolean remisiones = validarRemisionExterna(tramite);
        boolean resultado = false;

        if (!clave.equals(EstadoTramite.CONCLUIDO.getClave()) && !clave.equals(EstadoTramite.RECHAZADO.getClave())
                && !clave.equals(EstadoTramite.RESUELTO.getClave())
                && !clave.equals(EstadoTramite.RESUELTO_NOTIFICADO.getClave())) {
            resultado = true;
            resultado = !remisiones;
        }
        else {
            resultado = false;
        }

        return resultado;
    }

    public boolean validarRemisionExterna(Tramite tramite) {
        boolean rem = false;
        Remision remision = remisionDao.obtenerUltimaRemisionPorIdTramite(tramite.getNumeroAsunto());
        if (remision != null) {
            UnidadAdministrativa unidadAdministrativa = remision.getUnidadAdminNueva();
            if (unidadAdministrativa.getIdeTipoUnidadAdministrativa().getClave()
                    .equals(TipoUnidadAdministrativa.ADMINISTRACION_EXTERNA.getClave())
                    || unidadAdministrativa.getIdeTipoUnidadAdministrativa().getClave()
                            .equals(TipoUnidadAdministrativa.ADMINISTRACION_INTERNA.getClave())) {
                rem = true;
            }
            else {
                rem = false;
            }
        }
        return rem;
    }

}
