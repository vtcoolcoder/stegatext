package stegatext.interfaces.crypto;


public interface KeyManager {

    void saveSecretKey(final String key);
    
    
    String openSecretKey();
    
    
    String generateKey();
}