package mx.gob.sat.siat.juridica.base.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.UsuarioSincronizarDAO;
import mx.gob.sat.siat.juridica.base.dao.domain.model.UsuarioSincronizar;
import org.springframework.stereotype.Repository;

import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Repository("usuarioSincronizarDAO")
public class UsuarioSincronizarDAOImpl extends BaseJPADao implements UsuarioSincronizarDAO {

    /**
     *
     */
    private static final long serialVersionUID = -2388251257583419988L;

    @Override
    public UsuarioSincronizar obtenerUsuarioSincronizar(String rfcCorto) {
        String q = "SELECT us FROM UsuarioSincronizar AS us WHERE us.rfcCorto = :rfcCorto";
        List<UsuarioSincronizar> users = getEntityManager()
                .createQuery(q, UsuarioSincronizar.class)
                .setParameter("rfcCorto", rfcCorto)
                .getResultList();

        if (users.size() > 0) {
            return users.get(0);
        }
        return null;
    }

    @Override
    public void guardarUsuarioSincronizar(UsuarioSincronizar usuarioSincronizar) {
        UsuarioSincronizar usuarioSincronizarBD = null;
        try {
            usuarioSincronizarBD = obtenerUsuarioSincronizar(usuarioSincronizar.getRfcCorto());
        } catch (Exception ex) {
            usuarioSincronizarBD = null;
        }

        if (usuarioSincronizarBD == null) {
            getEntityManager().persist(usuarioSincronizar);
            getEntityManager().flush();
        }
        else {
            actualizarUsuarioSincronizar(usuarioSincronizar);
        }
    }

    @Override
    public void actualizarUsuarioSincronizar(
            UsuarioSincronizar usuarioSincronizar) {
        getEntityManager().merge(usuarioSincronizar);
        getEntityManager().flush();
    }

    @Override
    public List<UsuarioSincronizar> obtenerUsuariosSincronizarPorFecha(Date date) {
        String q = "SELECT us FROM UsuarioSincronizar AS us WHERE us.fecha = :fecha";
        TypedQuery<UsuarioSincronizar> query = getEntityManager()
                .createQuery(q, UsuarioSincronizar.class);

        query.setParameter("fecha", date, TemporalType.DATE);
        return query.getResultList();
    }

    @Override
    public List<UsuarioSincronizar> obtenerUsuariosSincronizarNoActivos() {
        String q = "SELECT us FROM UsuarioSincronizar AS us WHERE us.activo = :activo";
        TypedQuery<UsuarioSincronizar> query = getEntityManager()
                .createQuery(q, UsuarioSincronizar.class);

        query.setParameter("activo", 0);
        return query.getResultList();
    }

}
