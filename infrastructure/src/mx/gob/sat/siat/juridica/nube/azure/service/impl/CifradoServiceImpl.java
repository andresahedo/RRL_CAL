package mx.gob.sat.siat.juridica.nube.azure.service.impl;

import mx.gob.sat.siat.juridica.base.service.impl.BaseSerializableBusinessServices;
import mx.gob.sat.siat.juridica.nube.azure.helper.AzureHelper;
import mx.gob.sat.siat.juridica.nube.azure.service.CifradoService;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class CifradoServiceImpl extends BaseSerializableBusinessServices implements CifradoService {
    /**
     * 
     */
    private static final long serialVersionUID = -392489247117434943L;
    private static final int HEX_256 = 0x100;
    private static final int HEX_255 = 0xFF;
    private static final int ENCRYPT_MODE = 2;

    @Override
    public byte[] cifrarAES(byte[] file, String encryptionKey) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/CTR/NoPadding");
        } catch (NoSuchAlgorithmException e1) {
            getLogger().error("", e1);
        } catch (NoSuchPaddingException e1) {
            getLogger().error("", e1);
        }
        SecretKeySpec key = AzureHelper.generateKey(encryptionKey);
        try {
            if (cipher != null) {
                cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(key.getEncoded()));
                return cipher.doFinal(file);
            }
        } catch (InvalidKeyException e) {
            getLogger().error("", e);
        } catch (InvalidAlgorithmParameterException e) {
            getLogger().error("", e);
        } catch (IllegalBlockSizeException e) {
            getLogger().error("", e);
        } catch (BadPaddingException e) {
            getLogger().error("", e);
        }
        return null;
    }

    @Override
    public byte[] decifrarAES(byte[] cipherFile, String encryptionKey) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/CTR/NoPadding");
        } catch (NoSuchAlgorithmException e) {
            getLogger().error("", e);
        } catch (NoSuchPaddingException e) {
            getLogger().error("", e);
        }
        SecretKeySpec key = AzureHelper.generateKey(encryptionKey);
        try {
            if (cipher != null) {
                cipher.init(ENCRYPT_MODE, key, new IvParameterSpec(key.getEncoded()));
                return cipher.doFinal(cipherFile);
            }
        } catch (InvalidKeyException e) {
            getLogger().error("", e);
        } catch (InvalidAlgorithmParameterException e) {
            getLogger().error("", e);
        } catch (IllegalBlockSizeException e) {
            getLogger().error("", e);
        } catch (BadPaddingException e) {
            getLogger().error("", e);
        }
        return null;
    }

    public String genrerarHASHAchivo(byte[] archivo) {
        MessageDigest sha = null;
        byte[] hash = null;
        StringBuffer hashBuf = new StringBuffer();
        try {
            sha = MessageDigest.getInstance("MD5");
            hash = sha.digest(archivo);
            for (byte b : hash) {
                hashBuf.append(Integer.toHexString(HEX_256 + (int) (b & HEX_255)).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            getLogger().error("", e);
        }
        return hashBuf.toString();

    }

}
