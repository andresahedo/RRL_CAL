/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.util.helper;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.helper.BaseHelper;
import org.springframework.stereotype.Component;

/**
 * @author softtek
 */
@Component
public class ValidarFechaHelper extends BaseHelper {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    public boolean validaFechaHelper(int dia, int mes, int anio) {
        return comprobarFecha(dia, mes, anio);
    }

    /**
     * Devuelve el numero de dias de un mes dado
     */
    private int getDiasMes(int mes, int anio) {

        int diasMes = 0;

        switch (mes) {

        case 1:
        case NumerosConstantes.TRES:
        case NumerosConstantes.CINCO:
        case NumerosConstantes.SIETE:
        case NumerosConstantes.OCHO:
        case NumerosConstantes.DIEZ:
        case NumerosConstantes.DOCE: 
            // meses de 31 dias
            diasMes = NumerosConstantes.TREINTA_UNO;
            break;

        case NumerosConstantes.CUATRO:
        case NumerosConstantes.SEIS:
        case NumerosConstantes.NUEVE:
        case NumerosConstantes.ONCE: 
            // meses de 30 dias
            diasMes = NumerosConstantes.TREINTA;
            break;

        case 02: 
            // febrero
            if (esBisiesto(anio)) {
                diasMes = NumerosConstantes.VEINTINUEVE;
            }
            else {
                diasMes = NumerosConstantes.VEINTIOCHO;
            }
            break;
        default:
            break;

        }

        return diasMes;

    }

    /**
     * Metodo auxiliar para comprobar que una fecha es correcta
     */
    private boolean comprobarFecha(int dia, int mes, int anio) {
        if (dia <= 0) {
            return false;
        }
        if (dia > getDiasMes(mes, anio)) {
            return false;
        }

        return true;

    }

    /**
     * Comprueba si un anios es bisiesto Son bisiestos los anios
     * multiplos de 4 que no sean multiplos de 100 y los multiplos de
     * 400
     */
    private boolean esBisiesto(int anio) {
        return ((((anio % NumerosConstantes.CUATRO) == 0) && ((anio % NumerosConstantes.CIEN) != 0)) || ((anio % NumerosConstantes.CUATROCIENTOS) == 0));
    }

}
