/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.PersonasInternasDAO;
import mx.gob.sat.siat.juridica.base.dao.domain.model.PersonaInterna;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Repository("personasInternasDAO")
public class PersonasInternasDAOImpl extends BaseJPADao implements PersonasInternasDAO {

    /**
     * 
     */
    private static final long serialVersionUID = -8304441723906925478L;

    /**
     * Metodo para obtener a las pesonas internas
     * 
     * @return lista de resultados
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<PersonaInterna> getPersonasInternas() {
        String query = "from PersonaInterna";
        return getEntityManager().createQuery(query).getResultList();

    }

    /**
     * Metodo para buscar una persona interna por id
     */
    @Override
    public PersonaInterna getPersonaInternaById(Long idPersona) {
        return (PersonaInterna) getEntityManager().find(PersonaInterna.class, idPersona);
    }

    /**
     * Metodo para buscar una persona interna por RFC
     */
    @Override
    public PersonaInterna getPersonaInternaByRFC(String rfc) {
        String queryS = "from PersonaInterna where rfc = :rfc and fecbaja is null ";
        try {
            Query query = getEntityManager().createQuery(queryS);
            query.setParameter("rfc", rfc);
            return (PersonaInterna) query.getSingleResult();
        }
        catch (Exception e) {
            getLog().error("No se pudo recuperar el usuario", e);
            return null;
        }
    }

    @Override
    public PersonaInterna buscarEmpleado(Long numEmpleado) {
        StringBuffer consulta = new StringBuffer();
        consulta.append("FROM PersonaInterna WHERE numeroEmpleado = :numEmpleado ");
        try {
            Query query = getEntityManager().createQuery(consulta.toString());
            query.setParameter("numEmpleado", numEmpleado);
            List<Object> resultado = query.getResultList();
            if (resultado != null && !resultado.isEmpty()) {
                return (PersonaInterna) resultado.get(0);
            }
            else {
                return null;
            }

        }
        catch (Exception e) {
            getLog().error("no se Recupero EmpleadoAU", e);
            return null;
        }
    }

}
