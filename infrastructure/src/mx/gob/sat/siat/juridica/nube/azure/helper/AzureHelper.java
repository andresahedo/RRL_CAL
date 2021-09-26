package mx.gob.sat.siat.juridica.nube.azure.helper;

import com.microsoft.windowsazure.services.blob.client.SharedAccessBlobPermissions;
import com.microsoft.windowsazure.services.blob.client.SharedAccessBlobPolicy;
import mx.gob.sat.siat.juridica.base.helper.BaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.EnumSet;
import java.util.GregorianCalendar;
import java.util.TimeZone;

@Component
public class AzureHelper extends BaseHelper {

    /**
     * 
     */
    private static final long serialVersionUID = -6198531348768193955L;
    private static final Logger LOGGER_AZURE = LoggerFactory.getLogger(AzureHelper.class);

    public SharedAccessBlobPolicy generateSharedAccessBlobPolicyRead(int calendar, int time) {
        SharedAccessBlobPolicy policy = generateSharedAccessBlobPolicy(calendar, time);
        policy.setPermissions(EnumSet.of(SharedAccessBlobPermissions.READ));
        return policy;
    }

    public SharedAccessBlobPolicy generateSharedAccessBlobPolicyWrite(int calendar, int time) {
        SharedAccessBlobPolicy policy = generateSharedAccessBlobPolicy(calendar, time);
        policy.setPermissions(EnumSet.of(SharedAccessBlobPermissions.WRITE));
        return policy;
    }

    private SharedAccessBlobPolicy generateSharedAccessBlobPolicy(int calendar, int time) {
        SharedAccessBlobPolicy policy = new SharedAccessBlobPolicy();

        // Create a UTC Gregorian calendar value.

        GregorianCalendar start = new GregorianCalendar(TimeZone.getTimeZone("UTC"));

        GregorianCalendar end = new GregorianCalendar(TimeZone.getTimeZone("UTC"));

        // Specify the current time as the start time for the shared
        // access
        // signature.
        start.setTime(new Date());

        start.add(calendar, (time * -1));
        policy.setSharedAccessStartTime(start.getTime());

        // Use the start time + 1 hour as the end time for the shared
        // access
        // signature.
        end.setTime(new Date());

        end.add(calendar, time);
        policy.setSharedAccessExpiryTime(end.getTime());
        getLogger().debug("start time [{}]", start.getTime());
        getLogger().debug("end time [{}]", end.getTime());
        return policy;
    }

    public static SecretKeySpec generateKey(String key) {
        byte[] generatekey = key.getBytes(Charset.forName("ISO-8859-1"));
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e) {
            LOGGER_AZURE.error("", e);
        }
        if (sha != null) {
            generatekey = sha.digest(generatekey);
        }

        return new SecretKeySpec(generatekey, "AES");

    }

    public byte[] generateKeyFile(String key) {
        byte[] generatekey = key.getBytes(Charset.forName("UTF-8"));
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e) {
            getLogger().error("", e);
        }
        if (sha != null) {
            generatekey = sha.digest(generatekey);
        }

        return generatekey;

    }

    public void crearArchivo(String path, byte[] conten) {
        File file = new File(path);
        FileOutputStream fos = null;

        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    fos = new FileOutputStream(file);
                    fos.write(conten);
                    fos.flush();
                }
            }
            catch (IOException e) {
                getLogger().debug(e.toString());
            }
            finally {

                try {
                    if (fos != null) {
                        fos.close();
                    }
                    if (fos != null) {
                        fos.close();
                    }
                }
                catch (IOException ex) {
                    getLogger().error("", ex);
                }

            }

        }
    }

}
