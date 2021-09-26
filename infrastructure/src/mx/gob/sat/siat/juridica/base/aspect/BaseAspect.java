/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.aspect;

import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.base.dto.UserProfileDTO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

/**
 * Clase principal de aspectos
 * 
 * @author softtek
 * 
 */
public class BaseAspect implements BeanFactoryAware {
    private final transient Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Instancia del BeanFactory
     */
    private BeanFactory beanFactory;

    /**
     * Constructor de la clase
     */
    protected BaseAspect() {
        super();
    }

    /**
     * Metodo para establecer el valor de la propiedad BeanFactory
     */
    @Override
    public void setBeanFactory(final BeanFactory beanFactory)  {
        this.beanFactory = beanFactory;
    }

    public BeanFactory getBeanFactory() {
        return this.beanFactory;
    }

    @Pointcut("execution(public * *(..))")
    protected void exceptions() {}

    @Pointcut("execution(* *.set*(..))")
    protected void setter() {}

    @Pointcut("execution(* *.get*(..))")
    protected void getter() {}

    @Pointcut("target(mx.gob.sat.siat.juridica.auditoria.interfaces.Auditable) && @annotation(mx.gob.sat.siat.juridica.auditoria.annotation.Audit)")
    protected
            void auditable() {}

    @Pointcut("target(mx.gob.sat.siat.juridica.base.api.BaseAuditoriaFacade) && exceptions()")
    protected void facade() {}

    /**
     * Metodo para obtener el nombre del servicio
     * 
     * @param jp
     * @return serviceName
     */
    protected String getServiceName(final JoinPoint jp) {

        String serviceName = "";

        serviceName = jp.getStaticPart().getSignature().getName();

        return serviceName;
    }

    /**
     * Metodo para obtener la firma del metodo
     * 
     * @param jp
     * @return
     */
    protected Method getMethod(final JoinPoint jp) {
        return ((MethodSignature) jp.getStaticPart().getSignature()).getMethod();
    }

    protected void setUserProfileIntoBusinessService(final Object container, final UserProfileDTO userProfile) {
        final Collection<Field> containerFields = getInheritedFields(container);

        Object value = null;

        try {
            for (final Field containerField : containerFields) {

                containerField.setAccessible(true);

                value = containerField.get(container);

                if (value instanceof Proxy) {
                    value = ((Advised) value).getTargetSource().getTarget();
                }

                if (value instanceof BaseBussinessBean) {
                    final Collection<Field> businessServicesFields = getInheritedFields(value);

                    for (final Field businessServicesField : businessServicesFields) {
                        if (UserProfileDTO.class == businessServicesField.getType()
                                && "userProfileDTO".equals(businessServicesField.getName())) {
                            businessServicesField.setAccessible(true);

                            businessServicesField.set(value, userProfile);

                            logger.debug("User profile: [{}] was set into: [{}]", userProfile, value);
                        }
                    }

                    setUserProfileIntoBusinessService(value, userProfile);
                }
            }
        }
        catch (IllegalArgumentException iae) {
            logger.error(iae.toString());
        }
        catch (IllegalAccessException iae) {
            logger.error(iae.toString());
        }
        catch (Exception e) {
            logger.error(e.toString());
        }
    }

    /**
     * Metodo que extrae los argumentos del JointPoint
     * 
     * @param jp
     * @return
     */
    protected String extractArgumentsFromJoinPoint(final JoinPoint jp) {
        final Map<String, Object> arguments = new LinkedHashMap<String, Object>();

        final Object[] args = jp.getArgs();

        if (args.length > 0) {
            final String[] names = ((CodeSignature) jp.getStaticPart().getSignature()).getParameterNames();

            for (int index = 0; index < args.length; index++) {
                arguments.put(names[index], args[index]);
            }
        }

        return arguments.toString();
    }

    /**
     * Metodo que devuelve una lista de los campos heredados
     * 
     * @param object
     * @return
     */
    private Set<Field> getInheritedFields(final Object object) {
        final Set<Field> inheritedFields = new LinkedHashSet<Field>();

        Class<?> clazz = object.getClass();

        while (null != clazz && Object.class != clazz) {
            for (final Field field : clazz.getDeclaredFields()) {
                inheritedFields.add(field);
            }

            clazz = clazz.getSuperclass();
        }

        return inheritedFields;
    }

    public Logger getLogger() {
        return this.logger;
    }

}
