/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.util.exception;

/**
 * 
 * @author softtek
 * 
 */
public class BaseBussinessException extends Exception {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "category" tipo String
     */
    private String category;

    /**
     * Atributo privado "situation" tipo String
     */
    private String situation;

    /**
     * Atributo privado "args" tipo Object
     */
    private Object[] args;

    /**
     * Atributo privado "cause" tipo Throwable
     */
    private Throwable cause;

    /**
     * Atributo privado "LINE_SEPARATOR" tipo String
     */
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * Constructor vacio
     */
    protected BaseBussinessException() {

    }

    /**
     * Sobrecarga del constructor
     * 
     * @param message
     */
    public BaseBussinessException(String message) {
        super(message);
    }

    public BaseBussinessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Sobrecarga del constructor
     * 
     * @param category
     * @param situation
     */
    public BaseBussinessException(String category, String situation) {
        this.category = category;
        this.situation = situation;
    }

    /**
     * Sobrecarga del constructor
     * 
     * @param category
     * @param situation
     * @param args
     */
    public BaseBussinessException(String category, String situation, Object[] args) {
        this(category, situation);
        this.args = ((args == null) ? null : (Object[]) args.clone());
    }

    /**
     * Sobrecarga del constructor
     * 
     * @param category
     * @param situation
     * @param cause
     */
    public BaseBussinessException(String category, String situation, Throwable cause) {
        this(category, situation);
        this.cause = cause;
    }

    /**
     * Sobrecarga del constructor
     * 
     * @param category
     * @param situation
     * @param cause
     * @param args
     */
    public BaseBussinessException(String category, String situation, Throwable cause, Object[] args) {
        this(category, situation, args);
        this.cause = cause;
    }

    /**
     * 
     * @return category
     */
    public String getCategory() {
        return category;
    }

    /**
     * 
     * @param category
     *            a fijar
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 
     * @return situacion
     */
    public String getSituation() {
        return situation;
    }

    /**
     * 
     * @param situation
     *            a fijar
     */
    public void setSituation(String situation) {
        this.situation = situation;
    }

    /**
     * 
     * @return Object
     */
    public Object[] getArgs() {
        if (args != null) {
            Object[] arreglo = new Object[args.length];
            for (int i = 0; i < args.length; i++) {
                Object o = args[i];
                arreglo[i] = o;
            }
            return arreglo;
        }
        else {
            return null;
        }
    }

    /**
     * 
     * @param args
     *            a fijar
     */
    public void setArgs(Object[] argsArray) {
        if (argsArray != null) {
            Object[] argsLocal = argsArray.clone();
            Object[] arreglo = new Object[argsLocal.length];
            for (int i = 0; i < argsLocal.length; i++) {
                Object o = argsLocal[i];
                arreglo[i] = o;
            }
            this.args = arreglo;
        }
        else {
            this.args = null;
        }
    }

    /**
     * @return cause
     */
    public Throwable getCause() {
        return cause;
    }

    /**
     * 
     * @param cause
     *            a fijar
     */
    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    /**
     * 
     * @return serialVersionUID
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * @return formattedMessage
     */
    public String toString() {
        StringBuffer formattedMessage = new StringBuffer();
        formattedMessage.append("Class name: ").append(super.getClass().getName());
        if ((getMessage() != null) && (getMessage().trim().length() > 0)) {
            formattedMessage.append(LINE_SEPARATOR).append("   Message: ").append(getMessage());
        }
        if (getCause() != null) {
            formattedMessage.append(LINE_SEPARATOR).append("     Cause: ").append(getCause());
        }
        return formattedMessage.toString();
    }

}
