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
    private static final String PROGRAM_NAME_PREFIX = "java --enable-preview stegatext.Obfuscator";
    
    
    private static Obfuscator obfuscator = null;
    
    
    private final Base64Mapper base64Mapper;
    
    
    @Autowired
    public Obfuscator(Base64Mapper base64Mapper) {
        this.base64Mapper = Objects.requireNonNull(base64Mapper);
    }


    public static void main(String... args) {
        if (isCLArgsExist(args)) {
            parseCLArgs(args);
        } else {
            help();
        }        
    }
    
    
    private static boolean isCLArgsExist(String... args) { return args.length > 0; }
    
    
    private static void instanceObfuscatorIfNull() {
        if (obfuscator == null) {
            var context = new AnnotationConfigApplicationContext(MapperConfig.class);
            obfuscator = context.getBean(Obfuscator.class);
        }   
    }
    
    
    private static void parseCLArgs(String... args) {     
        final var paramName = args[0]; 
        switch (paramName) {
            case "--encode", "-e", "--decode", "-d" -> parseCLArgsForObfuscatorInstance(paramName);         
            case "--help", "-h" -> help();
            default -> { reportWrongParamName(paramName); help(); }
        }
    }
    
    
    private static void parseCLArgsForObfuscatorInstance(String paramName) {
        instanceObfuscatorIfNull();
        switch (paramName) {
            case "--encode", "-e" -> obfuscator.encode();
            case "--decode", "-d" -> obfuscator.decode();
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
    
    
    private static void help() {
        System.err.println(STR."""
                \t\t\t\t\t\t\
                ИСПОЛЬЗОВАНИЕ
                
                Для обфускации:
                    \{PROGRAM_NAME_PREFIX} {--encode|-e} < source-txt-file > obfs-txt-file
                    
                Для деобфускации:
                    \{PROGRAM_NAME_PREFIX} {--decode|-d} < obfs-txt-file > decoded-source-txt-file
                    
                Вызов справки:
                    \{PROGRAM_NAME_PREFIX} {--help|-h}
                """
        );
    }
    
    
    private static void reportWrongParamName(String paramName) {
        System.err.println(STR."Неизвестное имя параметра: \{paramName} !");
        System.err.println();
    }
}