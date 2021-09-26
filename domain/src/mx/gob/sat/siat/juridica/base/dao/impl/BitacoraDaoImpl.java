/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.BitacoraDao;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Bitacora;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Repository("bitacoraDao")
public class BitacoraDaoImpl extends BaseJPADao implements BitacoraDao {

    /**
     * 
     */
    private static final long serialVersionUID = 5684734862060942256L;

    /**
     * Metodo para obtener un bitacora por id
     */
    @Override
    public Bitacora obtenerBitacoraPorId(String idBitacora) {

        TypedQuery<Bitacora> typedQuery =
                getEntityManager().createNamedQuery("Bitacora.findById", Bitacora.class).setParameter("idBitacora",
                        idBitacora);
        List<Bitacora> resultados = typedQuery.getResultList();
        if (resultados != null && !resultados.isEmpty()) {
            return resultados.get(0);
        }
        return null;
    }

    @Override
    public void insertarBitacora(Bitacora bitacora) {
        if (bitacora != null) {
            getEntityManager().persist(bitacora);

        }
    }
    
}
