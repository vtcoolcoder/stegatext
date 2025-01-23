package stegatext.crypto;


import stegatext.interfaces.functions.SupplierWE;
import stegatext.interfaces.crypto.CryptoProcessor;

import java.util.Objects;

import java.security.Key;
import javax.crypto.*;
import javax.crypto.spec.*;


public class CryptoProcessorImpl implements CryptoProcessor {

    private record FieldInitializer(Cipher cipher, Key key) {
    
        public FieldInitializer {
            Objects.requireNonNull(cipher);
            Objects.requireNonNull(key);
        }
    }


    private static final String ALGORITHM = "DESede";
    private static final int VALID_KEY_LENGTH = 168;
    
    
    private final Cipher cipher;
    private final Key key;
    

    public CryptoProcessorImpl(final String pwd) {      
        var fields = tryCatchWrapping(() -> {
            var pwdBytes = validatePwd(pwd).getBytes();
            var spec = new SecretKeySpec(pwdBytes, ALGORITHM);
            return new FieldInitializer(
                    Cipher.getInstance(ALGORITHM),
                    SecretKeyFactory.getInstance(ALGORITHM).generateSecret(spec)
            );
        });
        
        key = fields.key();
        cipher = fields.cipher();
    }
    
    
    @Override
    public byte[] encrypt(final String sourcePhrase) {
        Objects.requireNonNull(sourcePhrase);
         
        return tryCatchWrapping(() -> {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(sourcePhrase.getBytes());
        });
    }
    
    
    @Override   
    public String decrypt(final byte[] encodedPhrase) {
        Objects.requireNonNull(encodedPhrase);
                
        return tryCatchWrapping(() -> {
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(encodedPhrase));
        });
    }
    
    
    private static String validatePwd(final String pwd) {
        Objects.requireNonNull(pwd);
        
        final var keyLength = pwd.length();
        
        if (VALID_KEY_LENGTH != keyLength) {
            throw new IllegalArgumentException(
                    STR."""
                    Неверная длина ключа: \{keyLength} !
                    Длина ключа должна быть \{VALID_KEY_LENGTH} символов.
                    """
            );
        }
        
        return pwd;
    }
    
    
    private static <R> R tryCatchWrapping(SupplierWE<R, ? extends Exception> func) {
        return func.tryCatchWrapping().get();
    }
}