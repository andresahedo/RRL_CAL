/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.impl;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.dao.RequerimientoDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoRequerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoRequerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoOficial;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoRequerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.NotificacionRequerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Requerimiento;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * DAO para atender las solicitudes a BD del requerimiento.
 * 
 * @author Softtek
 * 
 */
@Repository("requerimientoDao")
public class RequerimientoDaoImpl extends BaseJPADao implements
                RequerimientoDao {
        
        private static final long serialVersionUID = 5750918814896687553L;
        @Value("${parametro.idTramite}")
        private String parametroIdTramite;
        @Value("${parametro.idRequerimiento}")
        private String parametroIdRequerimiento;

        @Value("${requerimientodaoimpl.select.requerimiento}")
        private String selectRequerimiento;

        @Value("${requerimientodaoimpl.orderby.idRequerimiento}")
        private String orderByIdRequerimiento;

        /**
         * M&eacute;todo para guardar requerimiento.
         */
        public void crearRequerimiento(Requerimiento requerimiento)
                        {

                if (requerimiento != null) {
                                        getEntityManager().persist(requerimiento);
                                getEntityManager().flush();
                        }
                
        }

        /**
         * M&eacute;todo para obtener el &uacute;ltimo requerimiento del tramite
         * solicitado.
         */
        @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
        @Override
        public Requerimiento obtenerUltimoRequerimientoPorTramiteRRL(
                        String idTramite) {
                Query query = getEntityManager()
                                .createQuery(
                                                this.selectRequerimiento
                                                                + "WHERE r.tramite.numeroAsunto = :idTramite and r.tipoRequerimiento ='"
                                                                + TipoRequerimiento.CONTRIBUYENTE.getClave()
                                                                + this.orderByIdRequerimiento,
                                                Requerimiento.class);
                query.setParameter(this.parametroIdTramite, idTramite);
                List<Requerimiento> lista = query.getResultList();
                if (!lista.isEmpty()) {
                        return lista.get(0);
                }
                return null;
        }

        /**
         * M&eacute;todo para obtener el &uacute;ltimo requerimiento del tramite
         * solicitado.
         */
        @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
        @Override
        public Requerimiento obtenerUltimoRequerimientoPorTramiteRRLTarea(
                String idTramite) {
                Query query = getEntityManager()
                        .createQuery(
                                this.selectRequerimiento
                                        + "WHERE r.tramite.numeroAsunto = :idTramite and r.estadoRequerimiento <>'"
                                        + EstadoRequerimiento.RECHAZADO.getClave()
                                        + this.orderByIdRequerimiento,
                                Requerimiento.class);
                query.setParameter(this.parametroIdTramite, idTramite);
                List<Requerimiento> lista = query.getResultList();
                if (!lista.isEmpty()) {
                        return lista.get(0);
                }
                return null;
        }

        @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
        @Override
        public Requerimiento obtenerRequerimientoAutoridadInterna(String idTramite){
                Query query = getEntityManager()
                        .createQuery(
                                this.selectRequerimiento
                                        + "WHERE r.tramite.numeroAsunto = :idTramite and r.descripcion like 'TIREQ.RAI' and r.estadoRequerimiento <>'"
                                        + EstadoRequerimiento.RECHAZADO.getClave()
                                        + this.orderByIdRequerimiento,
                                Requerimiento.class);
                query.setParameter(this.parametroIdTramite, idTramite);
                List<Requerimiento> lista = query.getResultList();
                if (!lista.isEmpty()) {
                        return lista.get(0);
                }
                return null;
        }

        @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
        @Override
        public Requerimiento obtenerRequerimientoContributente(
                String idTramite) {
                Query query = getEntityManager()
                        .createQuery(
                                this.selectRequerimiento
                                        + "WHERE r.tramite.numeroAsunto = :idTramite AND r.tipoRequerimiento = 'TIREQ.RIP' order by r.idRequerimiento desc",
                                Requerimiento.class);
                query.setParameter(this.parametroIdTramite, idTramite);
                List<Requerimiento> lista = query.getResultList();
                if (!lista.isEmpty()) {
                        return lista.get(0);
                }
                return null;
        }

        /**
         * M&eacute;todo para obtener el &uacute;ltimo requerimiento del tramite
         * solicitado.
         */
        @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
        @Override
        public Requerimiento obtenerUltimoRequerimientoPorTramiteRRLCAL(
                        String idTramite) {
                Query query = getEntityManager()
                                .createQuery(
                                                this.selectRequerimiento
                                                                + "WHERE r.tramite.numeroAsunto = :idTramite and (r.tipoRequerimiento ='"
                                                                + TipoRequerimiento.PROMOVENTE_CAL.getClave()
                                                                + "' or r.tipoRequerimiento ='"
                                                                + TipoRequerimiento.CONTRIBUYENTE.getClave()
                                                                + "') and (r.estadoRequerimiento ='"
                                                                + EstadoRequerimiento.AUTORIZADO.getClave()
                                                                + "' or r.estadoRequerimiento ='"
                                                                + EstadoRequerimiento.ATENDIDO.getClave()
                                                                + "') ORDER BY r.idRequerimiento DESC ",
                                                Requerimiento.class);
                query.setParameter(this.parametroIdTramite, idTramite);
                List<Requerimiento> lista = query.getResultList();
                if (!lista.isEmpty()) {
                        return lista.get(0);
                }
                return null;
        }

        /**
         * M&eacute;todo para obtener el &uacute;ltimo requerimiento del tramite
         * solicitado.
         */
        @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
        @Override
        public Requerimiento obtenerUltimoRequerimientoPorTramiteCAL(
                        String idTramite) {
                Query query = getEntityManager()
                                .createQuery(
                                                this.selectRequerimiento
                                                                + "WHERE r.tramite.numeroAsunto = :idTramite and r.tipoRequerimiento in ('REQCAL.RTR','REQCAL.PR') order by r.idRequerimiento desc",
                                                  Requerimiento.class);
                query.setParameter(this.parametroIdTramite, idTramite);
                List<Requerimiento> lista = query.getResultList();
                if (!lista.isEmpty()) {
                        return lista.get(0);
                }
                return null;
        }

        /**
         * M&eacute;todo para obtener el &uacute;ltimo requerimiento del tramite
         * solicitado.
         */
        @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
        @Override
        public List<Requerimiento> obtenerRequermientosAutoridad(String idTramite) {
                Query query = getEntityManager()
                                .createQuery(
                                                this.selectRequerimiento
                                                                + "WHERE r.tramite.numeroAsunto = :idTramite and r.tipoRequerimiento <>'"
                                                                + TipoRequerimiento.PROMOVENTE_CAL.getClave()
                                                                + "' and r.tipoRequerimiento <>'"
                                                                + TipoRequerimiento.CONTRIBUYENTE.getClave()+"' and r.estadoRequerimiento <>'"+EstadoRequerimiento.RECHAZADO.getClave()
                                                                + this.orderByIdRequerimiento,
                                                Requerimiento.class);
                query.setParameter(this.parametroIdTramite, idTramite);
                List<Requerimiento> lista = query.getResultList();

                return lista;

        }

        @Override
        public boolean obtenerTramitesActivos(String idTramite) {
                Query query = getEntityManager()
                                .createQuery(
                                                this.selectRequerimiento
                                                                + "WHERE r.tramite.numeroAsunto = :idTramite and r.estadoRequerimiento = '"
                                                                + EstadoRequerimiento.GENERADO.getClave()
                                                                + this.orderByIdRequerimiento,
                                                Requerimiento.class);
                query.setParameter(this.parametroIdTramite, idTramite);
                @SuppressWarnings("unchecked")
                List<Requerimiento> lista = query.getResultList();
                if (!lista.isEmpty()) {
                        return true;
                }
                return false;
        }

        @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
        @Override
        public Requerimiento obtenerUltimoRequerimientoPorTramiteActivoCAL(
                        String idTramite) {
                Query query = getEntityManager()
                                .createQuery(
                                                this.selectRequerimiento
                                                                + "WHERE r.tramite.numeroAsunto = :idTramite and r.tipoRequerimiento ='"
                                                                + TipoRequerimiento.PROMOVENTE_CAL.getClave()
                                                                + "' and r.estadoRequerimiento = '"
                                                                + EstadoRequerimiento.AUTORIZADO.getClave()
                                                                + this.orderByIdRequerimiento,
                                                Requerimiento.class);
                query.setParameter(this.parametroIdTramite, idTramite);
                List<Requerimiento> lista = query.getResultList();
                if (!lista.isEmpty()) {
                        return lista.get(0);
                }
                return null;
        }

        /**
         * M&eacute;todo para obtener requerimiento por ID.
         */
        public Requerimiento obtenerRequerimientoPorId(Long idRequerimiento) {
                Query query = getEntityManager().createQuery(
                                this.selectRequerimiento
                                                + "WHERE r.idRequerimiento = :idRequerimiento ",
                                Requerimiento.class);
                query.setParameter(this.parametroIdRequerimiento, idRequerimiento);
                if (idRequerimiento != null){
                        return (Requerimiento) query.getSingleResult();
                } else {
                        return null;
                }
        }

        /**
         * M&eacute;todo para obtener requerimiento por ID.
         */
        public void actualizarRequerimiento(Requerimiento requerimiento) {
                if (requerimiento.getIdRequerimiento() == null) {
                        getEntityManager().persist(requerimiento);
                } else {
                        getEntityManager().merge(requerimiento);
                }

                getEntityManager().flush();

        }

        /**
         * M&eacute;todo para guardar documento oficial.
         */
        public void guardaDocumentoOficial(DocumentoOficial documentoOficial) {
                getEntityManager().persist(documentoOficial);
        }

        /**
         * M&eacute;todo para guardar la asociaci&oacute;n del los documentos
         * oficiales con el requerimiento.
         */
        public void guardaDocumentoRequerimiento(
                        DocumentoRequerimiento documentoRequerimiento) {
                getEntityManager().persist(documentoRequerimiento);
        }

        /**
         * M&eacute;todo para obtener requerimiento por ID.
         */
        public List<Requerimiento> obtenerListaRequerimientosPorIdTramite(
                        String idTramite) {
                Query query = getEntityManager()
                                .createQuery(
                                                this.selectRequerimiento
                                                                + "WHERE r.tramite.numeroAsunto = :idTramite and r.estadoRequerimiento <> :rech ",
                                                Requerimiento.class);
                query.setParameter(this.parametroIdTramite, idTramite);
                query.setParameter("rech", EstadoRequerimiento.RECHAZADO.getClave());
                return query.getResultList();
        }

        /**
         * Metodo para obtener un documento oficial por Id de requerimiento.
         */
        @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
        public List<DocumentoOficial> obtenerDocumentosOficialesPorIdRequerimiento(
                        Long idRequerimiento) {
                StringBuffer sb = new StringBuffer();
                sb.append("SELECT do from DocumentoOficial do, DocumentoRequerimiento req ");
                sb.append("where do.idDocumentoOficial = req.documentoOficial.idDocumentoOficial ");
                sb.append("and req.idRequerimiento = :idRequerimiento ");
                sb.append("and blnActivo = 1 order by do.fechaCreacion DESC ");
                Query query = getEntityManager().createQuery(sb.toString());
                query.setParameter(this.parametroIdRequerimiento, idRequerimiento);
                return query.getResultList();
        }

        /**
         * Metodo para obtener una notificacion por Id de requerimiento.
         */
        @Override
        public NotificacionRequerimiento obtenerNotificacionPorIdRequerimiento(
                        Long idRequerimiento) {
                Query query = getEntityManager()
                                .createQuery(
                                                "SELECT notif from NotificacionRequerimiento notif "
                                                                + "where notif.requerimiento.idRequerimiento = :idRequerimiento "
                                                                + "and notif.fechaBaja = null ");
                query.setParameter(this.parametroIdRequerimiento, idRequerimiento);
                List<Object> lista = query.getResultList();
                if (lista != null && !lista.isEmpty()) {
                        return (NotificacionRequerimiento) lista.get(0);
                }
                return null;
        }

        /**
         * Metodo para obtener una autorizacion por Id de requerimiento.
         */
        @Override
        public Persona obtenerAutorizadorPorIdRequerimiento(Long idRequerimiento) {
                Query query = getEntityManager()
                                .createQuery(
                                                "SELECT per from FirmaRequerimiento fr , PersonaInterna per "
                                                                + "where fr.firma.claveUsuario=per.claveUsuario "
                                                                + " and fr.requerimiento.idRequerimiento = :idRequerimiento  "
                                                                + " order by fr.firma.idFirma asc");
                query.setParameter(this.parametroIdRequerimiento, idRequerimiento);
                List<Object> lista = query.getResultList();
                if (lista != null && !lista.isEmpty()) {
                        return (Persona) lista.get(0);
                }
                return null;
        }

        /**
         * Metodo para obtener un abogado por Id de tramite
         */
        @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
        public Persona obtenerAbogadoPorIdTramite(final String folioTramite) {
                StringBuffer sql = new StringBuffer(NumerosConstantes.CIEN);
                sql.append("SELECT p FROM Requerimiento r , PersonaInterna p ");
                sql.append("WHERE r.idUsuario = p.claveUsuario ");
                sql.append("AND r.tramite.numeroAsunto = :folioTramite ");
                Query query = getEntityManager().createQuery(sql.toString());

                query.setParameter("folioTramite", folioTramite);
                List<Object> lista = query.getResultList();
                if (lista != null && !lista.isEmpty()) {
                        return (Persona) lista.get(0);
                }
                return null;
        }

        @Override
        public Requerimiento obtenerUltimoRequerimientoPorTramite(
                        String numeroAsunto) {
                Query query = getEntityManager()
                                .createQuery(
                                                this.selectRequerimiento
                                                                + "WHERE r.tramite.numeroAsunto = :idTramite ORDER BY r.idRequerimiento DESC ",
                                                Requerimiento.class);
                query.setParameter(this.parametroIdTramite, numeroAsunto);
                List<Requerimiento> lista = query.getResultList();
                if (!lista.isEmpty()) {
                        return lista.get(0);
                }
                return null;
        }

        @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
        @Override
        public Date obtenerFechaRequerimiento(String numeroAsunto) {
                StringBuilder consulta = new StringBuilder();
                consulta.append(" select req.fechaAutorizacion from Requerimiento req ");
                consulta.append(" where (req.tipoRequerimiento = '"
                                + TipoRequerimiento.CONTRIBUYENTE.getClave()
                                + "' or req.tipoRequerimiento = '"
                                + TipoRequerimiento.PROMOVENTE_CAL.getClave()
                                + "') and req.estadoRequerimiento = '"
                                + EstadoRequerimiento.AUTORIZADO.getClave()
                                + "' and req.tramite.numeroAsunto = :numeroAsunto ORDER BY req.tramite.numeroAsunto ");
                Query query = getEntityManager().createQuery(consulta.toString());
                query.setParameter("numeroAsunto", numeroAsunto);
                List<Date> fechas = query.getResultList();
                if (fechas != null && !fechas.isEmpty()) {
                        return fechas.get(0);
                } else {
                        return null;
                }
        }

        public List<Requerimiento> obtenerRequerimientosActivos(String idTramite){
                Query query = getEntityManager()
                        .createQuery(
                                this.selectRequerimiento
                                        + "WHERE r.tramite.numeroAsunto = :idTramite AND r.estadoRequerimiento IN ('ESTREQ.GN','ESTREQ.AU') ORDER BY r.idRequerimiento DESC ",
                                Requerimiento.class);
                query.setParameter(this.parametroIdTramite, idTramite);
                List<Requerimiento> lista = query.getResultList();
                if (!lista.isEmpty()) {
                        return lista;
                }
                return null;
        }


}
