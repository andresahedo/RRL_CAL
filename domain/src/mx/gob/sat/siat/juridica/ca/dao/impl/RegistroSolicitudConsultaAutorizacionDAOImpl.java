/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.ca.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.DocumentoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.DiscriminadorConstants;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoAutoridad;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Autoridad;
import mx.gob.sat.siat.juridica.base.dao.domain.model.FraccionDuda;
import mx.gob.sat.siat.juridica.base.dao.domain.model.MedioDefensa;
import mx.gob.sat.siat.juridica.base.dao.domain.model.PersonaSolicitud;
import mx.gob.sat.siat.juridica.base.dao.impl.BaseJPADao;
import mx.gob.sat.siat.juridica.ca.dao.RegistroSolicitudConsultaAutorizacionDAO;
import mx.gob.sat.siat.juridica.ca.dao.domain.model.SolicitudConsultaAutorizacion;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Bean para tender las peticiones a BD del registro de la solicitud.
 * 
 * @author Softtek
 * 
 */
@Repository
public class RegistroSolicitudConsultaAutorizacionDAOImpl extends BaseJPADao implements
        RegistroSolicitudConsultaAutorizacionDAO {

    /**
     * 
     */
    private static final long serialVersionUID = -2176210661569861068L;

    /**
     * M&eacute;todo para obtener las modalidades del tr&aacute;mite
     * por id de subservicio.
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<TipoTramite> obtenerModalidadesPorSubservicio(String idSubservicio, String tipoPersona) {
        Query query =
                getEntityManager()
                        .createQuery(
                                "SELECT t FROM TipoTramite t, TramiteModalidad tm "
                                        + "WHERE (t.idTipoTramite BETWEEN :idInicial AND :idFinal) "
                                        + "AND t.idTipoTramite = tm.tipoTramite.idTipoTramite  AND tm.idTipoPersona = :tipoPersona "
                                        + "AND t.subservicio = :idSubservicio  AND t.modalidad <> '00' AND t.blnActivo=1 "
                                        + "ORDER BY  t.idTipoTramite ", TipoTramite.class);

        query.setParameter("idInicial", Integer.valueOf(DiscriminadorConstants.T2_CLASIFICACION_ARANCELARIA));
        query.setParameter("idFinal",
                Integer.valueOf(DiscriminadorConstants.T2_AVISO_ACTUALIZACION_MONEDEROS_ELECTRONICOS));
        query.setParameter("idSubservicio", idSubservicio);
        query.setParameter("tipoPersona", tipoPersona);

        return query.getResultList();
    }

    public TipoTramite obtenerTipoTramitePorId(String idTipoTramite) {
        Query query =
                getEntityManager().createQuery("Select t from TipoTramite t where t.idTipoTramite = :idTipoTramite",
                        TipoTramite.class);
        query.setParameter("idTipoTramite", Integer.valueOf(idTipoTramite));
        return (TipoTramite) query.getSingleResult();
    }

    /**
     * M&eacute;todo para obtener los tipos de documentos (requeridos
     * u opcionales) asociados a la modalidad.
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<DocumentoTramite> obtenerTiposDocumentosPorTramite(Integer idTipoTramite, Integer opcional) {
        Query query =
                getEntityManager()
                        .createQuery(
                                "SELECT dt FROM DocumentoTramite dt"
                                        + " WHERE dt.especifico = :especifico AND dt.tipoTramite.idTipoTramite = :idTipoTramite"
                                        + " AND dt.vigencia.blnActivo=1"
                                        + " AND (SYSDATE BETWEEN dt.vigencia.fechaInicioVigencia and dt.vigencia.fechaFinVigencia OR dt.vigencia.fechaFinVigencia IS NULL) "
                                        + " ORDER BY dt.orden ");
        query.setParameter("especifico", opcional);
        query.setParameter("idTipoTramite", idTipoTramite);

        return query.getResultList();
    }

    public DocumentoTramite obtenerDocumentoRazonesLogicoJuridicas() {
        Query query =
                getEntityManager().createQuery(
                        "SELECT dt FROM DocumentoTramite dt WHERE dt.vigencia.blnActivo=1 AND dt.tipoDoc = 15");
        return (DocumentoTramite) query.getSingleResult();

    }

    /**
     * M&eacute;todo para guardar personas relacionadas a la
     * solicitud.
     * 
     * @param solicitante
     */
    public SolicitudConsultaAutorizacion crearSolicitud(SolicitudConsultaAutorizacion solicitud) {
        if (solicitud != null && solicitud.getIdSolicitud() == null) {
            getEntityManager().persist(solicitud);
        }
        else {
            getEntityManager().merge(solicitud);
        }
        return solicitud;
    }

    /**
     * M&eacute;todo para guardar personas relacionadas a la
     * solicitud.
     */
    public PersonaSolicitud crearPersonaSolicitud(PersonaSolicitud personaSolicitud) {
        if (personaSolicitud != null && personaSolicitud.getIdPersonaSolicitud() == null) {
            getEntityManager().persist(personaSolicitud);
        }
        else {
            getEntityManager().merge(personaSolicitud);
        }
        return personaSolicitud;
    }

    /**
     * Metodo para crear un medio de defensa
     * 
     * @param medioDefensa
     */
    public MedioDefensa crearMedioDefensa(MedioDefensa medioDefensa) {
        if (medioDefensa != null && medioDefensa.getIdMedioDefensa() == null) {
            getEntityManager().persist(medioDefensa);
        }
        else {
            getEntityManager().merge(medioDefensa);
        }
        return medioDefensa;
    }

    public void crearAutoridad(Autoridad autoridad) {
        if (autoridad != null && autoridad.getIdAutoridad() == null) {
            getEntityManager().persist(autoridad);
        }
        else {
            getEntityManager().merge(autoridad);
        }
    }

    public void crearFraccionDuda(FraccionDuda fraccionDuda) {
        getEntityManager().persist(fraccionDuda);
    }

    public void actualizaFraccionDuda(FraccionDuda fraccionDuda) {
        getEntityManager().merge(fraccionDuda);
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public Autoridad obtenerAutoridad(Long idSolicitud, TipoAutoridad tipoAutoridad) {
        Autoridad autoridad = null;
        Query query =
                getEntityManager().createQuery(
                        "SELECT au FROM Autoridad au" + " WHERE au.idSolicitud = :idSolicitud"
                                + " AND au.idTipoAutoridad = :tipoAutoridad " + " AND au.blnActivo = 1 "
                                + " ORDER BY au.idAutoridad DESC");
        query.setParameter("idSolicitud", idSolicitud);
        query.setParameter("tipoAutoridad", tipoAutoridad.getClave());
        List<Autoridad> lista = query.getResultList();
        if (lista != null && lista.size() > 0) {
            autoridad = lista.get(0);
        }
        return autoridad;
    }

}
