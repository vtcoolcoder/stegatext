package stegatext;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import stegatext.interfaces.helpers.CryptoObfuscateHelper;
import stegatext.interfaces.Obfuscator;


@RequiredArgsConstructor
@Component
public class ObfuscatorImpl implements Obfuscator {

    private final CryptoObfuscateHelper cryptoObfuscateHelper;
    

    @Override
    public void encode() {
        cryptoObfuscateHelper.encodeOrEncrypt(String::getBytes);
    }
    
    
    @Override
    public void decode() {
        cryptoObfuscateHelper.decodeOrDecrypt(String::new);
    }
}