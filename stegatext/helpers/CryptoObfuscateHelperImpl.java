package stegatext.helpers;


import java.util.function.Function;

import stegatext.mappers.Base64Mapper;
import stegatext.interfaces.helpers.CryptoObfuscateHelper;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class CryptoObfuscateHelperImpl implements CryptoObfuscateHelper {

    private final Base64Mapper mapper;


    @Override
    public void encodeOrEncrypt(Function<String, byte[]> encodeOrEncryptMapper) {
        encodeOrEncrypt(encodeOrEncryptMapper, mapper); 
    }
    
    
    @Override
    public void decodeOrDecrypt(Function<byte[], String> decodeOrDecryptMapper) {
        decodeOrDecrypt(decodeOrDecryptMapper, mapper);
    }
}