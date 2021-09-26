package mx.gob.sat.siat.juridica.base.web.util;

public final class ElFunctions {

    private ElFunctions(){}

    public static Boolean isNumber(String toCheck) {
        if (toCheck == null) {
            return false;
        }
        if (toCheck.length() == 0) {
            return false;
        }
        int i = 0;
        if (toCheck.charAt(0) == '-') {
            if (toCheck.length() == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < toCheck.length(); i++) {
            char c = toCheck.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

}
