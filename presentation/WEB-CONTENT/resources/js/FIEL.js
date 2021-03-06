var FIND_CERTIFICATE = "Buscar Certificado";
var FIND_PRIVATE_KEY = "Buscar Clave Privada";
var BANDERA_ERROR_FIRMA = false;

// javascript messages
var GENERIC_INVALID_MSG = "Certificado, clave privada o contrase\u00f1a de clave privada inv\u00e1lidos, int\u00e9ntelo nuevamente.";
var INVALID_CERTIFICATE_MSG = "Certificado no emitido por el SAT";
var INVALID_PRIVATE_KEY_MSG = "Clave privada inv\u00e1lida";
var INVALID_PRIVATE_KEY_PASSWORD_MSG = "Clave privada o contrase\u00f1a de clave privada inv\u00e1lida";
var JAVA_PROBLEM_MSG = "Su navegador no tiene Java instalado. Esta aplicaci\u00f3n no funcionar\u00e1.";

var NPOBJECT_ERROR = "Los datos introducidos no corresponden, favor de verificar su contrase\u00f1a de clave privada, clave privada y/o certificado nuevamente";

//applet messages
var INVALID_CERT_TYPE = "Certificado no emitido por el SAT: Debe usar un certificado de FIEL";
var INVALID_FILE = "Certificado no emitido por el SAT: Archivo inv\u00e1lido";
var INVALID_CERT = "Certificado no emitido por el SAT:";
var INVALID_USERID = "El certificado no contiene un RFC";
var CORRESPOND_ERROR = "El certificado no corresponde con la clave privada.";

//****************************************************************************************************************
function onload() {
	
    document.getElementByd("btnSubmit").setAttribute("disabled", "disabled");
}

function validarFormulario() {

    if (document.getElementById("privateKey").value == "") {
        alert("Debe seleccionar el archivo de Clave Privada");
        return false;
    }
    if (document.getElementById("certificate").value == "") {
        alert("Debe seleccionar el Certificado ");
        return false;
    }
    if (document.getElementById("privateKeyPassword").value == "") {
        alert("Debe ingresar la contraseña del Archivo de Clave Privada");
        return false;
    }
    if (document.getElementById("RFC").value == "") {
        alert("Debe ingresar el RFC");
        return false;
    }
    return true;
}

//****************************************************************************************************************
function browseForCertificate() {
	if(typeof String.prototype.trim !== 'function') {
		  String.prototype.trim = function() {
		    return this.replace(/^\s+|\s+$/g, ''); 
		  }
		}
    try {

        var filename = document.FIEL.showFileDialog(FIND_CERTIFICATE, "*.cer");

        document.getElementById("certificate").value = filename;
	
	//traido del fiel.js anterior
	if (filename != null && filename.length > 0) {
            var userId = document.FIEL.getRfcFormCertificate(filename);
            if (userId != null) {
                document.FIELForm.userID.value = userId.trim();
            } else {
                document.FIELForm.certificate.value = '';
                throw INVALID_CERTIFICATE_MSG;
            }
        } else {
            document.FIELForm.userID.value = '';
        }
    }
    catch (err) {
        document.getElementById("certificate").value = '';

        var desc = err.toString();
        if (navigator.appName.indexOf('Microsoft') >  - 1) {
            desc = err.description;
        }
        // if a key is specified, it returns this message from the SgiCripto libs (nice)
        if (desc.indexOf('5 >= 2') >  - 1) {
            desc = INVALID_CERTIFICATE_MSG;
        }
        // if the applet hasn't loaded yet, or won't load
        if (desc.indexOf('TypeError') >  - 1) {
            desc = JAVA_PROBLEM_MSG;
        }
        if (desc.indexOf("java.lang.Exception: ") == 0) {
            desc = desc.substring(21);
        }
        if (desc.indexOf("java.io.FileNotFoundException: ") == 0) {
            desc = desc.substring(31);
        }
        if (desc.indexOf("java.security.PrivilegedActionException: java.lang.Exception: ") == 0) {
            desc = desc.substring(31);
        }
        //$('#error').html(INVALID_FILE); //
        alert(desc);
    }
}

function browseForPrivateKey() {
	if(typeof String.prototype.trim !== 'function') {
		  String.prototype.trim = function() {
		    return this.replace(/^\s+|\s+$/g, ''); 
		  }
		}
    try {
        var filename = document.FIEL.showFileDialog(FIND_PRIVATE_KEY, "*.key");

        document.getElementById("privateKey").value = filename;
        
    }
    catch (err) {
        document.getElementById("privateKey").value = '';

        var desc = err.toString();
        if (navigator.appName.indexOf('Microsoft') >  - 1) {
            desc = INVALID_PRIVATE_KEY_MSG;
        }
        if (desc.indexOf('TypeError') >  - 1) {
            desc = JAVA_PROBLEM_MSG;
        }
        if (desc.indexOf("java.lang.Exception: ") == 0) {
            desc = desc.substring(21);
        }
        if (desc.indexOf("java.io.FileNotFoundException: ") == 0) {
            desc = desc.substring(31);
        }

        //        $('#error').html(INVALID_FILE);
        alert(INVALID_FILE);
        //addMessage(desc);
    }
}
//****************************************************************************************************************
function enviarFormulario(boton) {
    //if (!window.console) console.log("Enviando formulario");
    var rfc = document.getElementById("RFC").value;
    var userPwd = document.getElementById("privateKeyPassword").value;
    var certFilePath = document.getElementById("certificate").value;
    var keyFilePath = document.getElementById("privateKey").value;
    //var numeroSerie = document.getElementById("numeroSerie").value;
    var cadenaOriginal = document.getElementById("cadenaOriginal").value;
    var mode = document.getElementById("mode").value;
    var selloDigital;

    if (validarFormulario()) {
        //if (!window.console) console.log("Validando el formulario");
        try {
	    /*if (!window.console) {
		console.log("--------- Datos entrada ----------");
		console.log("RFC : " + rfc);
		console.log("Password : " + userPwd);
		console.log("Certificado : " + certFilePath);
		console.log("Llave privada : " + keyFilePath);
		console.log("Cadena original : " + cadenaOriginal);
	    }*/
            ///document.FIEL.firmarElectronicamente(rfc, userPwd, certFilePath, keyFilePath, null, cadenaOriginal, mode);
            document.FIEL.firmarElectronicamente(rfc, userPwd, certFilePath, keyFilePath, null, cadenaOriginal, mode);
            cadenaOriginal = document.FIEL.getCadena();
            selloDigital = document.FIEL.getFirma();
	    /*if (!window.console) {
		console.log("Cadena orignal del applet: " + cadenaOriginal);
		console.log("Sello digital del applet: " + selloDigital);
	    }*/
            //numeroSerie = document.FIEL.getNumeroSerieCertificado();
            document.getElementById("cadenaOriginal").value = cadenaOriginal;
            document.getElementById("firmaDigital").value = selloDigital;
            //document.getElementById("numeroSerie").value = numeroSerie;
            //document.FIELForm.submit();
            /*alert("Numero de serie del certificado: " + document.getElementById("numeroSerie").value + "\n\
            Cadena Original: " + cadenaOriginal + "\n\
            Firma digital:  " + selloDigital);*/
            //document.getElementById("formSolicitud:btnFirma").click();
            setparamAttrs("cadenaOriginal", cadenaOriginal);
            setparamAttrs("firmaDigital", selloDigital);
	    boton = boton.replace(":", "\\:");
	    $('#'+boton).trigger('click');
	    $( "#btnSubmit" ).prop( "disabled", true );
		setTimeout(function(){$('#'+boton).prop( "disabled", true )}, 10);
		BANDERA_ERROR_FIRMA = false;
        }
        catch (err) {
        	BANDERA_ERROR_FIRMA = true;
        	$( "#btnSubmit" ).removeAttr( "disabled" );
        	setTimeout(function(){$('#'+boton).removeAttr( "disabled")}, 10);
            document.getElementById("privateKeyPassword").value = '';
            var desc = err.toString();

            if (navigator.appName.indexOf('Microsoft') >  - 1) {
                desc = err.description;
            }
            // if a key is specified, it returns this message from the SgiCripto libs (nice)
            if (desc.indexOf('5 >= 2') >  - 1) {
                desc = INVALID_CERTIFICATE_MSG;
            }
            // if the applet hasn't loaded yet, or won't load
            if (desc.indexOf('TypeError') >  - 1) {
                desc = JAVA_PROBLEM_MSG;
            }
            if (desc.indexOf('Bad sequence') >  - 1 || desc.indexOf('cannot be cast') >  - 1) {
                desc = INVALID_PRIVATE_KEY_MSG;
            }
            // invalid private key password
            if (desc.indexOf('pad block corrupted') >  - 1) {
                desc = INVALID_PRIVATE_KEY_PASSWORD_MSG;
            }
            // applet is not loaded yet
            if (desc.indexOf('TypeError') >  - 1) {
                desc = JAVA_PROBLEM_MSG;
            }
            // chrome bug - message when key and pwd do not correspond
            if (desc.toLowerCase().indexOf('npobject') >  - 1) {
                desc = NPOBJECT_ERROR;
            }
            if (desc.indexOf("java.lang.Exception: ") == 0) {
                desc = desc.substring(21);
            }
            else {
                desc = GENERIC_INVALID_MSG;
            }

            alert(desc);
        }
    }
}

function setparamAttrs(parmID, parmVal) {

    var gData = document.createDocumentFragment();
    var newNode = document.createElement("param");

    newNode.setAttribute("id", parmID);
    newNode.setAttribute("value", parmVal);

    gData.appendChild(newNode);
    document.body.appendChild(gData);
}

function procesarFirma(event) {
	if(validarFormulario()){
		enviarFormulario('FIELForm:procesa');
		if(BANDERA_ERROR_FIRMA) { // paso las validaciones iniciales, pero el applet de firma genero otro error, por lo que no se puede hacer el submit
			event.preventDefault();
		}else{
			//Se elimina el pwd para no enviarlo al server
			document.getElementById("privateKeyPassword").value = '';
		}
	} else {
		event.preventDefault();
	}
}