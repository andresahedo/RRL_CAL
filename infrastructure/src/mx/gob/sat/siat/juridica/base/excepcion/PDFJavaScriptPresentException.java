package mx.gob.sat.siat.juridica.base.excepcion;

public class PDFJavaScriptPresentException extends PDFException {
    
    private static final long serialVersionUID = 6053868477412804004L;
    
    public static final int JAVASCRIPT = -2;
    
    public PDFJavaScriptPresentException(int causa){
        super(causa);
    }
    

}
