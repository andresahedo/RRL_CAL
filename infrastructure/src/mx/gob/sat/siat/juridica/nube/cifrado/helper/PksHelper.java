package mx.gob.sat.siat.juridica.nube.cifrado.helper;

import gob.sat.sgi.SgiLibException;
import gob.sat.sgi.pkcs.PKCSLibs;
import gob.sat.sgi.sglib.PKCS8EncryptedPrivateKeyInfo;
import mx.gob.sat.siat.juridica.base.helper.BaseHelper;
import org.apache.commons.ssl.PKCS8Key;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.pkcs.RSAPrivateKeyStructure;
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.RSAPrivateCrtKeySpec;

@Component
public class PksHelper extends BaseHelper {

    /**
     * 
     */
    private static final long serialVersionUID = -3518595947750095851L;

    public PrivateKey getPrivateKey(byte[] privada, char[] clave) throws 
            GeneralSecurityException, UnsupportedEncodingException {
        // Intentar obtenerla por la forma del SAT y si falla intentar
        // por el OCSP
        getLogger().debug("busco privatekey");
        PrivateKey privateKey = null;
        try {
            privateKey = getPrivateKeyOpenSSL(privada, clave);
        }
        catch (Exception e) {
            getLogger().error("Error {}", e);
            privateKey = getPrivateKeySAT(privada, clave);
        }
        return privateKey;
    }

    public PrivateKey getPrivateKeyOpenSSL(byte[] privada, char[] clave) throws 
            GeneralSecurityException, UnsupportedEncodingException {

        PKCS8Key pkcs8 = new PKCS8Key(privada, clave);

        return pkcs8.getPrivateKey();
    }

    public PrivateKey getPrivateKeySAT(byte[] privada, char[] clave) throws 
            GeneralSecurityException, UnsupportedEncodingException {
        ByteArrayInputStream bis = new ByteArrayInputStream(privada);
        PKCS8EncryptedPrivateKeyInfo localPKCS8EncryptedPrivateKeyInfo = null;
        try {
            localPKCS8EncryptedPrivateKeyInfo =
                    new PKCS8EncryptedPrivateKeyInfo((DERSequence) PKCSLibs.leeDERObject(bis));
        }
        catch (IOException ex) {
            getLogger().error("Error {}", ex);
        }
        PrivateKeyInfo localPrivateKeyInfo = null;
        getLogger().debug("clave {}", clave);
        try {
            if (localPKCS8EncryptedPrivateKeyInfo != null) {
                localPKCS8EncryptedPrivateKeyInfo.getPrivateKeyInfo(clave);
                localPrivateKeyInfo = localPKCS8EncryptedPrivateKeyInfo.getPrivateKeyInfo(clave);
            }
        }
        catch (SgiLibException ex) {
            getLogger().error("Error {}", ex);
        }

        if (localPrivateKeyInfo != null) {
            RSAPrivateKeyStructure localRSAPrivateKeyStructure =
                    new RSAPrivateKeyStructure((DERSequence) localPrivateKeyInfo.getPrivateKey());

            RSAPrivateCrtKeyParameters keySpec =
                    new RSAPrivateCrtKeyParameters(localRSAPrivateKeyStructure.getModulus(),
                            localRSAPrivateKeyStructure.getPublicExponent(),
                            localRSAPrivateKeyStructure.getPrivateExponent(), localRSAPrivateKeyStructure.getPrime1(),
                            localRSAPrivateKeyStructure.getPrime2(), localRSAPrivateKeyStructure.getExponent1(),
                            localRSAPrivateKeyStructure.getExponent2(), localRSAPrivateKeyStructure.getCoefficient());

            PrivateKey privateKey =
                    KeyFactory.getInstance("RSA").generatePrivate(

                            new RSAPrivateCrtKeySpec(keySpec.getModulus(), keySpec.getPublicExponent(), keySpec
                                    .getExponent(), keySpec.getP(), keySpec.getQ(), keySpec.getDP(), keySpec.getDQ(),
                                    keySpec.getQInv()));

            return privateKey;
        }
        else {
            return null;
        }

    }
}
