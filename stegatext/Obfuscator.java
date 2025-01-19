package stegatext;


import stegatext.configs.MapperConfig;
import stegatext.mappers.Base64Mapper;

import java.util.Base64;
import java.util.Scanner;
import java.util.Objects;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


@Component
public class Obfuscator {

    private static final Base64.Encoder ENCODER = Base64.getEncoder();
    private static final Base64.Decoder DECODER = Base64.getDecoder();
    private static final Scanner SCANNER = new Scanner(System.in);
    
    
    private final Base64Mapper base64Mapper;
    
    
    @Autowired
    public Obfuscator(Base64Mapper base64Mapper) {
        this.base64Mapper = Objects.requireNonNull(base64Mapper);
    }


    public static void main(String... args) {
        var context = new AnnotationConfigApplicationContext(MapperConfig.class);
        var obfuscator = context.getBean(Obfuscator.class);
                
        obfuscator.obfuscating(args);
    }
    
    
    private void obfuscating(String... args) {
        if (args.length > 0) {
            switch (args[0]) {
                case "--encode" -> encode();
                case "--decode" -> decode();
            }
        } else {
            help();
        }
    }
    
    
    private void encode() {
        SCANNER.useDelimiter("\n").tokens()
                .map(String::getBytes)
                .map(ENCODER::encodeToString)
                .map(this::base64LineToEncodedLine)
                .forEach(line -> System.out.println(STR."\{line}\n\n"));
    }
    
    
    private void decode() {
        SCANNER.useDelimiter("\n\n\n").tokens()
                .map(this::encodedLineToBase64Line)
                .map(DECODER::decode)
                .map(String::new)
                .forEach(System.out::println);
    }
    
    
    private String base64LineToEncodedLine(String base64Line) {
        Objects.requireNonNull(base64Line);
        
        return base64Line.chars()
                .mapToObj(e -> (char) e)
                .map(base64Mapper.characterToStringMapper()::get)
                .collect(Collectors.joining("\n\n"));
    }
    
    
    private String encodedLineToBase64Line(String encodedLine) {
        Objects.requireNonNull(encodedLine);
              
        return new Scanner(encodedLine).useDelimiter("\n\n").tokens()
                .map(base64Mapper.stringToCharacterMapper()::get)
                .map(e -> "" + e)
                .collect(Collectors.joining());
    }
    
    
    private void help() {
        System.err.println("Справка:");
    }
}