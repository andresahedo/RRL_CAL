var timerSesion;
var timerAviso;


function reiniciarTimer() {
	
	limpiarTimersSesion();
    timerSesion = setInterval(function(){sesionDialog.show();iniciarTimerAviso();}, $('#tiempoAvisoSesion').val());
}

function iniciarTimerAviso() {
	limpiarTimersSesion();
   timerAviso = setInterval(function(){	sesionDialog.hide(); sesionFinalizadaDialog.show();limpiarTimersSesion();}, $('#tiempoEsperaAviso').val());
}

function limpiarTimersSesion(){
	clearTimeout(timerSesion);
	clearTimeout(timerAviso);
}

var pfAjaxHandler = function() {
    reiniciarTimer();
 }
$(document).on('pfAjaxComplete',pfAjaxHandler);


$(document).ready(function() {
	reiniciarTimer();
});
