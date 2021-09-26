package mx.gob.sat.siat.juridica.auditoria.aspect;

import mx.gob.sat.siat.exception.AccesoDenegadoException;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.juridica.auditoria.annotation.Audit;
import mx.gob.sat.siat.juridica.auditoria.bussiness.BaseAuditoriaBussinessBean;
import mx.gob.sat.siat.juridica.auditoria.dto.BitacoraDTO;
import mx.gob.sat.siat.juridica.base.aspect.BaseAspect;
import mx.gob.sat.siat.juridica.base.dto.UserProfileDTO;
import mx.gob.sat.siat.modelo.dao.SegbMovimientosDAO;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import mx.gob.sat.siat.util.MovimientoUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.springframework.core.annotation.Order;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;

@SuppressAjWarnings({ "adviceDidNotMatch" })
@Aspect
@Order(1)
public class BusinessAuditoriaAspect extends BaseAspect {
    // @Autowired
    private SegbMovimientosDAO segbMovimientosDAO;

    @Pointcut("target(bussiness) && auditable()")
    protected void actions(final BaseAuditoriaBussinessBean bussiness) {}

    @After("actions(bussiness)")
    public void preProcessController(final BaseAuditoriaBussinessBean bussiness, final JoinPoint jp) {
        final Method method = getMethod(jp);
        String identificadorProceso = method.getAnnotation(Audit.class).identificadorProceso();
        int movimiento = method.getAnnotation(Audit.class).movimiento();
        String observaciones = method.getAnnotation(Audit.class).observaciones();
        String sistema = method.getAnnotation(Audit.class).claveSistema();

        // Check for user profile validity
        final UserProfileDTO userProfile = getUserProfile(bussiness);

        if (isUserProfileValid(userProfile)) {
            BitacoraDTO bitacoraDTO = bussiness.getBitacoraDTO();

            // Datos de la bitacora
            if (observaciones != null && "".equals(observaciones)) {
                observaciones = bitacoraDTO.getObservaciones();
            }

            try {
                SegbMovimientoDTO segbMovimientoDTO =
                        MovimientoUtil.addMovimiento(getSession(bussiness), sistema, identificadorProceso, new Date(),
                                new Date(), movimiento, observaciones);
                getLogger().debug("segbMovimientoDTO {}", segbMovimientoDTO);
                segbMovimientosDAO.insert(segbMovimientoDTO);

            }
            catch (AccesoDenegadoException ex) {
                getLogger().error("Error al insertar an la bitacora:", ex);
            }
            catch (DaoException ex) {
                getLogger().error("Error al insertar an la bitacora:", ex);
            }
        }
    }

    private UserProfileDTO getUserProfile(final BaseAuditoriaBussinessBean bussiness) {
        return bussiness.getUserProfile();
    }

    private boolean isUserProfileValid(final UserProfileDTO userProfile) {
        return null != userProfile;
    }

    public void setSegbMovimientosDAO(SegbMovimientosDAO segbMovimientosDAO) {
        this.segbMovimientosDAO = segbMovimientosDAO;
    }

    private HttpSession getSession(final BaseAuditoriaBussinessBean bussiness) {
        return bussiness.getSession();
    }

}
