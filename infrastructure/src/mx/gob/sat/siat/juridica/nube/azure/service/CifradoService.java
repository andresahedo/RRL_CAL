package mx.gob.sat.siat.juridica.nube.azure.service;

public interface CifradoService {

    byte[] cifrarAES(byte[] file, String encryptionKey);

    byte[] decifrarAES(byte[] cipherFile, String encryptionKey);

    String genrerarHASHAchivo(byte[] archivo);
}
