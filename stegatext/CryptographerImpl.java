package stegatext;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import stegatext.interfaces.helpers.CryptoObfuscateHelper;
import stegatext.interfaces.Cryptographer;
import stegatext.interfaces.crypto.CryptoProcessor;
import stegatext.interfaces.crypto.KeyManager;
import stegatext.crypto.CryptoProcessorImpl;


@RequiredArgsConstructor
@Component
public class CryptographerImpl implements Cryptographer {

    private final CryptoObfuscateHelper cryptoObfuscateHelper;
    private final KeyManager keyManager;
    
    
    @Override
    public void encrypt() {
        cryptoObfuscateHelper.encodeOrEncrypt(new CryptoProcessorImpl(generateKey())::encrypt);
    }
    
    
    @Override
    public void decrypt() {
        cryptoObfuscateHelper.decodeOrDecrypt(new CryptoProcessorImpl(openSecretKey())::decrypt);
    }
    
    
    private String generateKey() {        
        var key = keyManager.generateKey();
        keyManager.saveSecretKey(key);
        return key;
    }
    
    
    private String openSecretKey() {
        return keyManager.openSecretKey();
    }
}