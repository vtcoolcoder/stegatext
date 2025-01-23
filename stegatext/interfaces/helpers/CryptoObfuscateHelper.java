package stegatext.interfaces.helpers;


import java.util.Scanner;
import java.util.function.Function;
import java.util.Base64;
import java.util.Objects;
import java.util.stream.Collectors;

import stegatext.mappers.Base64Mapper;


public interface CryptoObfuscateHelper {
    
    Scanner SCANNER = new Scanner(System.in);
    Base64.Encoder ENCODER = Base64.getEncoder();
    Base64.Decoder DECODER = Base64.getDecoder();
    
    
    default void encodeOrEncrypt(
            Function<String, byte[]> encodeOrEncryptMapper,
            Base64Mapper base64Mapper
    ) {
        Objects.requireNonNull(encodeOrEncryptMapper);
        
        SCANNER.useDelimiter("\n").tokens()
                .map(encodeOrEncryptMapper)
                .map(ENCODER::encodeToString)
                .map(e -> base64LineToEncodedLine(e, base64Mapper))
                .forEach(line -> System.out.println(STR."\{line}\n\n"));   
    }
    
    
    void encodeOrEncrypt(Function<String, byte[]> encodeOrEncryptMapper);
    
    
    default void decodeOrDecrypt(
            Function<byte[], String> decodeOrDecryptMapper, 
            Base64Mapper base64Mapper
    ) {
        Objects.requireNonNull(decodeOrDecryptMapper);
        
        SCANNER.useDelimiter("\n\n\n").tokens()
                .map(e -> encodedLineToBase64Line(e, base64Mapper))
                .map(DECODER::decode)
                .map(decodeOrDecryptMapper)
                .forEach(System.out::println);     
    }
    
    
    void decodeOrDecrypt(Function<byte[], String> decodeOrDecryptMapper);
    
    
    private String base64LineToEncodedLine(String base64Line, Base64Mapper base64Mapper) {
        Objects.requireNonNull(base64Line);
        Objects.requireNonNull(base64Mapper);
        
        return base64Line.chars()
                .mapToObj(e -> (char) e)
                .map(base64Mapper.characterToStringMapper()::get)
                .collect(Collectors.joining("\n\n"));
    }
    
    
    private String encodedLineToBase64Line(String encodedLine, Base64Mapper base64Mapper) {
        Objects.requireNonNull(encodedLine);
        Objects.requireNonNull(base64Mapper);
              
        return new Scanner(encodedLine).useDelimiter("\n\n").tokens()
                .map(base64Mapper.stringToCharacterMapper()::get)
                .map(e -> "" + e)
                .collect(Collectors.joining());
    }
}