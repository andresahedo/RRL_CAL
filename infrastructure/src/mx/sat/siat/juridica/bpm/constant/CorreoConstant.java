/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.sat.siat.juridica.bpm.constant;

/**
 * Interface para la definicion de constante
 * 
 * @author softtek
 * 
 */
public interface CorreoConstant {

    String PSTYLE = "<p style=\"font-size:12px;color:#666;width:94%;margin:auto;margin-top:20px; ";
    String FUENTE = "font-family:Verdana, Arial, Helvetica;\">";
    String HEADER = "<table width=\"100%\"><tr><td align='center' width=\"50%\"><img src='cid:logoHacienda' width='396' height='207'></td>"
            + "<td align='center' width=\"50%\"><img src='cid:logosat' width='400' height='76'></td></tr></table>";
    String ALIGN = "<tr><td> ";
    String ALIGN2 = " </td><td> ";
    String CIERRETR = " </td></tr>";
    String TEXT_ALIGN_LEFT = "text-align:left; font-family:Verdana, Arial, Helvetica;\">";
    String TEXT_CIERRE_PARRAFO = "</p><br>";
    String TEXT_BR = "<br>";
    /**
     * Cuerpo del correo
     */
    String CORREO = new StringBuffer().append(PSTYLE).append(FUENTE)
            .append("N&uacute;mero de expediente:  ").append("$solicitud")
            .append("<br>Nombre del promovente: ")
            .append("$nombreSolicitante </br>")
            .append("<br>RFC del promovente:").append("$rfcPromovente  </br>")
            .append("<br>Siendo las ").append("$hora").append(" del ")
            .append(" $fecha")
            .append(", se ha recibido su promoci&oacute;n referente al ")
            .append("$tipoTramite").append(" con el n&uacute;mero de asunto")
            .append("$numeroAsunto")
            .append("<br> Unidad administrativa del SAT:")
            .append("$unidadAdmin").toString();

    /**
     * Cuerpo de correo de tarea pendiente
     */
    String CORREO_TAREA_PENDIENTE = new StringBuffer()
            .append(HEADER)
            .append(PSTYLE)
            .append("text-align:center; font-weight:bold;font-family:Verdana, Arial, Helvetica;\">")
            .append("AVISO DE TAREA PENDIENTE")
            .append(TEXT_CIERRE_PARRAFO)
            .append(TEXT_BR)
            .append(PSTYLE)
            .append(FUENTE)
            .append("Con relaci&oacute;n al tr&aacute;mite n&uacute;mero ")
            .append("<b>$numeroAsunto</b> ")
            .append(" correspondiente al contribuyente <b>$nombreContribuyente</b>, RFC <b>$rfcContribuyente</b>,")
            .append(" promovido el ")
            .append("<b>$fechaInicioTramite</b>, ")
            .append(" se le informa que tiene la tarea &quot;<b>$nombreTarea</b>&quot;, pendiente por atender.")
            .append("</br></p><br>")
            .append(PSTYLE)
            .append(FUENTE)
            .append("Para dar atenci&oacute;n a dicha tarea, ingresar a la aplicaci&oacute;n")
            .append(" correspondiente en la opci&oacute;n Tareas pendientes por atender.")
            .append("</p>").toString();

    /**
     * Cuerpo de correo de tarea pendiente
     */
    String CORREO_AUTORIDAD = new StringBuffer()
            .append(HEADER)
            .append(PSTYLE)
            .append("text-align:center; font-weight:bold;font-family:Verdana, Arial, Helvetica;\">")
            .append("CORREO DE AVISO DE RECEPCI&Oacute;N DE PROMOCI&Oacute;N")
            .append(TEXT_CIERRE_PARRAFO)
            .append(TEXT_BR)
            .append(PSTYLE)
            .append(FUENTE)
            .append("Siendo las $fechaHora del $fechaEnvio, se ha recibido ")
            .append("la Promoci&oacute;n de $descripcionModalidad con el n&uacute;mero ")
            .append("de Asunto $numeroAsunto. ").append("</p>").toString();

    /**
     * Cuerpo de correo de tarea pendiente
     */
    String CORREO_RECEPCION_DOCUMENTOS = new StringBuffer()
            .append(HEADER)
            .append(PSTYLE)
            .append("text-align:center; font-weight:bold;font-family:Verdana, Arial, Helvetica;\">")
            .append("CORREO DE AVISO DE RECEPCI&Oacute;N DE DOCUMENTOS")
            .append(TEXT_CIERRE_PARRAFO)
            .append(TEXT_BR)
            .append(PSTYLE)
            .append(FUENTE)
            .append("Se ha recibido documentaci&oacute;n adicional relacionada con el asunto ")
            .append("$numeroAsunto ")
            .append(" del Contribuyente $nombreContribuyente RFC $rfcContribuyente")
            .append(" $fechaHora ").append("</p>").toString();

    String CORREO_ACTUALIZACION_USUARIOS = new StringBuffer()
            .append(HEADER)
            .append("<p style=\"font-size:12px; color:#666; width:94%; margin:auto; margin-top:20px; text-align:center; ")
            .append("font-weight:bold; font-family:Verdana, Arial, Helvetica;\">CORREO DE NOTIFICACI&Oacute;N")
            .append(" DE MOVIMIENTO DE USUARIO </p><br><br><br>")
            .append("<p style=\"font-size:12px; color:#666; width:94%; margin:auto; margin-top:20px; text-align:left; ")
            .append("font-weight:bold; font-family:Verdana, Arial, Helvetica;\">Se notifica el resultado de la actualiz")
            .append("aci&oacute;n de movimientos de usuario siendo $fechaActualizacion </p><br><br>")
            .append("<table width=\"100%\" style=\"text-align:left\" border=\"1\" align=\"center\">")
            .append(ALIGN).append("Dato del movimiento").append(ALIGN2)
            .append("Descripci&oacute;n del dato").append(CIERRETR)
            .append(ALIGN).append("N&uacute;mero de empleado").append(ALIGN2)
            .append("$numeroEmpleado").append(CIERRETR).append(ALIGN)
            .append("Nombre").append(ALIGN2).append("$nombre").append(CIERRETR)
            .append(ALIGN).append("Apellido paterno").append(ALIGN2)
            .append("$apPaterno").append(CIERRETR).append(ALIGN)
            .append("Apellido materno").append(ALIGN2).append("$apMaterno")
            .append(CIERRETR).append(ALIGN).append("RFC").append(ALIGN2)
            .append("$rfc").append(CIERRETR).append(ALIGN).append("RFC corto")
            .append(ALIGN2).append("$corto").append(CIERRETR).append(ALIGN)
            .append("Correo").append(ALIGN2).append("$mail").append(CIERRETR)
            .append(ALIGN).append("Permiso (Rol)").append(ALIGN2)
            .append("$permiso").append(CIERRETR).append(ALIGN)
            .append("Tipo de movimiento").append(ALIGN2)
            .append("$tipoMovimiento").append(CIERRETR).append(ALIGN)
            .append("Estado actualizaci&oacute;n").append(ALIGN2)
            .append("$edoActualizacion").append(CIERRETR).append(ALIGN)
            .append("Descripci&oacute;n de la excepci&oacute;n").append(ALIGN2)
            .append("$excepcion").append(CIERRETR).append("</table>")
            .toString();

    String CORREO_RECHAZO_ASUNTO = new StringBuffer()
            .append("<table width=\"100%\"><tr><td align='center' width=\"50%\"><img src='cid:logoHacienda' width='396' height='207'></td>"
                    + "<td align='center' width=\"50%\"><img src='cid:logosat' width='400' height='76'></td></tr></table>")
            .append(" <p style=\"font-size:12px;color:#666;width:94%;margin:auto;margin-top:20px; text-align:center; font-weight:bold;font-family:Verdana, Arial, Helvetica;\"> CORREO DE AVISO DE RECHAZO DE ASUNTO ")
            .append("</p><br>")
            .append("<br>")
            .append("<p  style=\"font-size:12px;color:#666;width:94%;margin:auto;margin-top:20px; text-align:left; font-family:Verdana, Arial, Helvetica;\">")
            .append("$nombreFuncionario:</p>")
            .append("<p style=\"font-size:12px;color:#666;width:94%;margin:auto;margin-top:20px; text-align:left; font-family:Verdana, Arial, Helvetica;\">")
            .append("Unidad administrativa del SAT: $uniAdmin </p><br><br><br>")
            .append("<p style=\"font-size:12px;color:#666;width:94%;margin:auto;margin-top:20px; text-align:left; font-family:Verdana, Arial, Helvetica;\">")
            .append("Se le informa que el asunto con n&uacute;mero $numAsunto, que corresponde a un(a) $elemento y con fecha de recepci&oacute;n ")
            .append("$fechaRecepcion, ha sido rechazado por $nomRechazo.</p>")
            .append("<p style=\"font-size:12px;color:#666;width:94%;margin:auto;margin-top:20px; text-align:left; font-family:Verdana, Arial, Helvetica;\">")

            .append("Por favor ingrese al portal de Empleados a atender el asunto indicado.</p>")
            .toString();

    String CORREO_RECHAZO_PRUEBA = new StringBuffer()
            .append(HEADER)
            .append(PSTYLE)
            .append("text-align:center; font-weight:bold;font-family:Verdana, Arial, Helvetica;\"> CORREO DE AVISO DE RECHAZO DE ASUNTO ")
            .append(TEXT_CIERRE_PARRAFO)
            .append(TEXT_BR)
            .append(PSTYLE)
            .append(TEXT_ALIGN_LEFT)
            .append("$nombreFuncionario:</p>")
            .append(PSTYLE)
            .append(TEXT_ALIGN_LEFT)
            .append("Se le informa que el asunto con n&uacute;mero $numAsunto, ")
            .append("que corresponde a un(a) $elemento y con fecha de recepci&oacute;n ")
            .append("$fechaRecepcion, ha sido rechazado por $nomRechazo .</p>")
            .append(PSTYLE)
            .append(TEXT_ALIGN_LEFT)
            .append("Por favor ingrese al portal de Empleados a atender el asunto indicado.</p>")
            .toString();

    String CORREO_CUMPLIMENTACION_REQUERIMIENTO = new StringBuffer()
            .append(HEADER)
            .append(PSTYLE)
            .append("text-align:center; font-weight:bold; font-family:Verdana, Arial, Helvetica;\"> CORREO DE AVISO DE CUMPLIMENTACI&Oacute;N DE REQUERIMIENTO </p>")
            .append("<br><br><br>")
            .append(PSTYLE)
            .append(TEXT_ALIGN_LEFT)
            .append("$nombreFuncionario</p>")
            .append(PSTYLE)
            .append(TEXT_ALIGN_LEFT)
            .append("Unidad administrativa del SAT: $uniAdmin </p><br><br><br>")
            .append(PSTYLE)
            .append(TEXT_ALIGN_LEFT)
            .append("Se le informa que el asunto con n&uacute;mero $numAsunto ")
            .append("requerido el d&iacute;a $fechaCreacionRequerimiento y notificado el $fechaNotificacion ")
            .append("se encuentra cumplimentado a partir del $fechaCumplimentacion.</p>")
            .append(PSTYLE)
            .append(TEXT_ALIGN_LEFT)
            .append("Por favor ingrese al portal de Empleados a atender el asunto indicado.</p>")
            .toString();
    
    String CORREO_REPROCESO_TRAMITES = new StringBuffer()
    .append(HEADER)
    .append(PSTYLE)
    .append("text-align:center; font-weight:bold; font-family:Verdana, Arial, Helvetica;\"> CORREO DE AVISO DE TRAMITES REPROCESADOS. </p>")
    .append(PSTYLE)
    .append(TEXT_ALIGN_LEFT)
    .append("Proceso de reprocesamiento RRL")
    .append("<br><br>")
    .append("Resumen $fecha</p>")
    .append("<br><br><br><br>")
    .append("$tabla")
    .toString();
    
}
