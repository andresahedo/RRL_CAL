/**
 * 
 */

function ejecutarProcesoFirma(boton){
	var fileCertificado = document.getElementById('certificate');
	var fileLlavePrivada= document.getElementById('privateKey');
    var contrasena=document.getElementById('privateKeyPassword');    
    var compatibilidad = PKI.SAT.FielUtil.validaNavegador(fileCertificado);
    if (compatibilidad === true) {
    	PKI.SAT.FielUtil.validaFielyFirmaCadena(
                fileCertificado,
                fileLlavePrivada,
                contrasena,
                function(certificado) {//Metodo que valida si la cadena orginal es una cadena vacia regresa la cadena
                	 inhabilitarBoton(true,boton);
                	 var cert = new PKI.SAT.Certificado(certificado);
                	 var serialNumber=cert.getNumeroSerie().replace(/ /g,"");
                     document.getElementById("numeroSerie").value=serialNumber;
                	 document.getElementById("RFC").value=cert.getRFC().replace(/ /g,"");// se obtiene el RFC y se eliminan espacios en blanco
                	 var cadenaOriginal = document.getElementById("cadenaOriginal").value;
                	 if(isBlank(cadenaOriginal)){
                	    	cadenaOriginal=document.getElementById("cadenaOriginal").value= "||" + serialNumber + "|UTF-8|" + document.getElementById("RFC").value + "|||";
                	    }
                    return cadenaOriginal;
                },
                function(error_code, certificado, firma, cadena_original) {//funcion callback
                    if (error_code === 0) {
                    	var cert = new PKI.SAT.Certificado(certificado);
                    	var certificadoVigente=true;//cert.isVigente();
                    	if(certificadoVigente){
                    		document.getElementById("cadenaOriginal").value = cadena_original;
                            document.getElementById("firmaDigital").value = firma;
                        	BANDERA_ERROR_FIRMA = false;
                    		boton = boton.replace(":", "\\:");
                    	    $('#'+boton).trigger('click');
                    	}else{
                    		inhabilitarBoton(false);
                    		alert('El certificado no es vigente');
                    	}
                    } else {
                    	inhabilitarBoton(false);
                    	BANDERA_ERROR_FIRMA = true;
                        alert('Ocurrió un error al firmar la cadena: \n' + PKI.SAT.FielUtil.obtenMensajeError(error_code));
                    }
                }
        );
    } else if (compatibilidad === false) {
        alert('Navegador NO es compatible para realizar firmado');
    } else {
        alert('Ocurrió un error al validar el navegador: \n' +
                PKI.SAT.FielUtil.obtenMensajeError(compatibilidad));
    }
}

function procesarFirmaFormulario(event) {
		if(BANDERA_ERROR_FIRMA) { 
			event.preventDefault();
		}else{
			//Se elimina el pwd para no enviarlo al server
			document.getElementById("privateKeyPassword").value = '';
		}
}

function inhabilitarBoton(valor){
	$( "#btnSubmit" ).prop( "disabled", valor );
	 setTimeout(function(){$('#'+boton).prop( "disabled", valor )}, 10);
}

function isBlank(str) {
    return (!str || /^\s*$/.test(str));
}