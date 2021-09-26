<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="mx.gob.sat.siat.juridica.base.dto.transformer.UserProfileDTOTransformer"%>
<%@ page import="mx.gob.sat.siat.modelo.dto.AccesoUsr"%>
<%@ page import="mx.gob.sat.siat.juridica.base.dto.UserProfileDTO"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>autenticación</title>
</head>
<body>

    <%

UserProfileDTOTransformer transformer = new UserProfileDTOTransformer();



HttpSession misession = (HttpSession) request.getSession(true);
		AccesoUsr accesoUsr = null;
		try {
			accesoUsr = (AccesoUsr)misession.getAttribute("acceso");
			if(null==accesoUsr){
				accesoUsr = (AccesoUsr)session.getAttribute("accesoEF");
				if(null!=accesoUsr){%>
    <%= "Es usuario empleado" %>

    <%=accesoUsr.getRfcCorto() %>
    <%=accesoUsr.getUsuario() %>
    <%}
			}else{%>
    <%= "Es usuario contribuyente" %>

    <%=accesoUsr.getRfcCorto() %>
    <%=accesoUsr.getUsuario() %>

    <%}
			
		} catch (Exception e) {
		%>
    <%= "Error al obtener el accesoUsr" %>
    <%
		}
		if (accesoUsr == null) {
			%>
    <%= "error en el usuario" %>
    <%
		} else {
			UserProfileDTO userProfileDTO = transformer.transformarDTO(accesoUsr);
			misession.setAttribute("userProfile", userProfileDTO);%>
    <%=userProfileDTO.getRfc()%>
    <%=userProfileDTO.getNombreCompleto()%>
    <%=userProfileDTO.getUsuario()%>
    <%=userProfileDTO.getIp()%>
    <%} %>

</body>
</html>