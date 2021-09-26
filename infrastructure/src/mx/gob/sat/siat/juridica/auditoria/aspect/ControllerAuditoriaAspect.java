package mx.gob.sat.siat.juridica.auditoria.aspect;

import mx.gob.sat.siat.exception.AccesoDenegadoException;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.juridica.auditoria.annotation.Audit;
import mx.gob.sat.siat.juridica.auditoria.controller.BaseAuditoriaControllerBean;
import mx.gob.sat.siat.juridica.auditoria.dto.BitacoraDTO;
import mx.gob.sat.siat.juridica.base.aspect.BaseAspect;
import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;
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
public class ControllerAuditoriaAspect extends BaseAspect {
    // @Autowired
    private SegbMovimientosDAO segbMovimientosDAO;

    @Pointcut("target(controller) && auditable()")
    protected void actions(final BaseAuditoriaControllerBean controller) {}

    @After("actions(controller)")
    public void preProcessController(final BaseAuditoriaControllerBean controller, final JoinPoint jp) {
        final Method method = getMethod(jp);
        String identificadorProceso = method.getAnnotation(Audit.class).identificadorProceso();
        int movimiento = method.getAnnotation(Audit.class).movimiento();
        String observaciones = method.getAnnotation(Audit.class).observaciones();
        String sistema = method.getAnnotation(Audit.class).claveSistema();

        // Check for user profile validity
        final UserProfileDTO userProfile = getUserProfile(controller);

        if (isUserProfileValid(userProfile)) {
            BitacoraDTO bitacoraDTO = controller.getBitacoraDTO();

            // Datos de la bitacora
            if (observaciones != null && "".equals(observaciones)) {
                observaciones = bitacoraDTO.getObservaciones();
            }

            try {
                SegbMovimientoDTO segbMovimientoDTO =
                        MovimientoUtil.addMovimiento(getSession(controller), sistema, identificadorProceso, new Date(),
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

    private UserProfileDTO getUserProfile(final BaseControllerBean controller) {
        return controller.getUserProfile();
    }

    private boolean isUserProfileValid(final UserProfileDTO userProfile) {
        return null != userProfile;
    }

    public void setSegbMovimientosDAO(SegbMovimientosDAO segbMovimientosDAO) {
        this.segbMovimientosDAO = segbMovimientosDAO;
    }

    private HttpSession getSession(final BaseControllerBean controller) {
        return controller.getSession();
    }

}
