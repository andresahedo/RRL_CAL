/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.juridica.ara.constantes;

public interface AraConstantes {
    public static final String ACTIVO = "A";
    public static final String REVOCADO = "R";
    public static final String CADUCO = "C";
    
    public static final String MENSAJE_LLAVE_SERIE_INCORRECTO = "No es posible continuar, ya que el certificado y la llave no corresponde con el RFC {0}, favor de verificar.";
    public static final String MENSAJE_SERIE_INCORRECTO = "No es posible continuar, ya que el certificado no corresponde con el RFC {0}, favor de verificar.";
    public static final String MENSAJE_ACTIVO = "";
    public static final String MENSAJE_REVOCADO = "No es posible continuar, ya que el certificado esta Revocado, favor de verificar.";
    public static final String MENSAJE_CADUCO = "No es posible continuar, ya que el certificado esta Caducado, favor de verificar.";
    public static final String MENSAJE_SERIE_VACIO = "No es posible continuar, ya que el certificado esta vacio.";
    public static final String MENSAJE_ERROR_ARA = "Ocurri\u00F3 un error al firmar - ARA";
    public static final String MENSAJE_ERROR = "Ocurri\u00F3 un error al firmar - {0} - ARA";
    public static final String MENSAJE_ERROR_CONEXION = "Ocurri\u00F3 un error al firmar - Servicion ARA no disponible";
}
