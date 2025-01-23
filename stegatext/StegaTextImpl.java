package stegatext;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import stegatext.interfaces.StegaText;
import stegatext.interfaces.Obfuscator;
import stegatext.interfaces.Cryptographer;


@RequiredArgsConstructor
@Component
public class StegaTextImpl implements StegaText {

    private final Obfuscator obfuscator;
    private final Cryptographer cryptographer;
    

    @Override
    public void encode() {
        obfuscator.encode();
    }
    
    
    @Override
    public void decode() {
        obfuscator.decode();
    }
    
    
    @Override
    public void encrypt() {
        cryptographer.encrypt();
    }
    
    
    @Override
    public void decrypt() {
        cryptographer.decrypt();
    }
}