package mx.gob.sat.siat.juridica.auditoria.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface Audit {
    String identificadorProceso() default "";

    int movimiento() default 1;

    String observaciones() default "";

    String claveSistema() default "";
}