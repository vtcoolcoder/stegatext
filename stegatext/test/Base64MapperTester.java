package stegatext.test;


import static java.util.Map.Entry;


import stegatext.configs.MapperConfig;
import stegatext.mappers.Base64Mapper;

import java.util.Objects;
import java.util.Map;
import java.util.function.Consumer;
import java.util.Comparator;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import lombok.Cleanup;
import lombok.Getter;


public class Base64MapperTester {

    private static final String AT = "@".repeat(120);
    private static final String EQUALS = "=".repeat(120);
    private static final String STAR = "*".repeat(120);
    private static final String DOLLAR = "$".repeat(120);
    private static final String SPACES = " ".repeat(45);
    
    private static final Consumer<Entry<?, ?>> PRINTER = entry -> System.out.println(
            STR."""
            \{AT}
            \{AT}
            \{entry.getKey()}
            \{EQUALS}
            \{entry.getValue()}
            \{STAR}
            \{STAR}
            """
    );

    @Getter
    private static final Base64Mapper MAPPER;
   
    
    static {
        @Cleanup
        var context = new AnnotationConfigApplicationContext(MapperConfig.class);
        MAPPER = context.getBean(Base64Mapper.class);
    }


    public static void main(String... args) {
        printAllMappers();
        
        System.err.println(
                STR."""
                
                
                characterToStringMapperSize: \{MAPPER.characterToStringMapper().size()}
                stringToCharacterMapperSize: \{MAPPER.stringToCharacterMapper().size()}
                """
        );   
    }
    
    
    private static void printAllMappers() {
        System.err.println(formattingTitle("CHARACTER", "STRING"));
        printCharToStrMapper(MAPPER.characterToStringMapper()); 
        
        System.err.println(formattingTitle("STRING", "CHARACTER"));
        printStrToCharMapper(MAPPER.stringToCharacterMapper());      
    }
    
    
    private static String formattingTitle(String source, String destination) {
        Objects.requireNonNull(source);
        Objects.requireNonNull(destination);
        return STR."\n\{DOLLAR}\n\{SPACES}\{source} ===>> \{destination}\n\{DOLLAR}\n";
    }
    
    
    private static void printStrToCharMapper(Map<String, Character> mapper) {
        printMapper(mapper, Entry.comparingByValue());
    }
    
    
    private static void printCharToStrMapper(Map<Character, String> mapper) {
        printMapper(mapper, Entry.comparingByKey());
    }
    
    
    private static <K, V> 
    void printMapper(Map<K, V> mapper, Comparator<Entry<K, V>> comparator) {
        Objects.requireNonNull(mapper);
        Objects.requireNonNull(comparator);
        
        mapper.entrySet().stream()
                .sorted(comparator)       
                .forEach(PRINTER); 
    }
}