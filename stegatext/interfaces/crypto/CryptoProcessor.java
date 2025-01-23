package stegatext.interfaces.crypto;


public interface CryptoProcessor {

    byte[] encrypt(final String sourcePhrase);
    
    
    String decrypt(final byte[] encodedPhrase);
}