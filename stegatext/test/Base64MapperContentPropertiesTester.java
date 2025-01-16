package stegatext.test;


import static stegatext.properties.Base64MapperContentProperties.PROPERTIES;
import static java.util.stream.Collectors.*;

import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

import java.util.function.Predicate;
import java.util.function.Function;

import java.lang.reflect.*;


public class Base64MapperContentPropertiesTester {

    private static final List<String> DIGIT_LIST = List.of(
            "zero", 
            "one", 
            "two", 
            "three", 
            "four", 
            "five", 
            "six", 
            "seven", 
            "eight", 
            "nine"
    );
    
    private static final Set<String> DIGIT_SET = Set.copyOf(DIGIT_LIST);   
    private static final Set<String> SYMBOLS = Set.of("plus", "slash", "propertyEquals");  
    private static final Set<String> OBJECT_METHOD_NAMES = Set.of(
            "equals",
            "getClass",
            "hashCode",
            "notify",
            "notifyAll",
            "toString",
            "wait"
    );
    
    private static final Map<String, String> DIGIT_MAP = createDigitMap(DIGIT_LIST);
            
    
    public static void main(String... args) {
        printAllProperties();
    }
    
    
    private static void printAllProperties() {
        final var properties = PROPERTIES.getProperties();
        var methods = properties.getClass().getMethods();
        
        Predicate<Method> isObjectMethod = method -> OBJECT_METHOD_NAMES.contains(method.getName());
        
        Function<Method, String> toContent = method -> {
            try {
                return STR."[\{method.getName()}]:\n\{method.invoke(properties)}";
            } catch (Exception e) {
                throw new RuntimeException(e);    
            }
        };
         
        Comparator<Method> comparator = (m1, m2) -> getStrComparator().compare(m1.getName(), m2.getName());
        
        Arrays.stream(methods)
                .sorted(comparator)
                .filter(isObjectMethod.negate())
                .map(toContent)
                .peek(_ -> System.out.println())
                .forEach(System.out::println);
    }
    
    
    private static Comparator<String> getStrComparator() {
        Predicate<String> isOneLetter = str -> (str.length() == 1) && Character.isLetter(str.charAt(0));
        Predicate<String> isTwoLetters = str -> (str.length() == 2) 
                && Character.isLetter(str.charAt(0)) 
                && Character.isLetter(str.charAt(1))
                && (Character.toUpperCase(str.charAt(0)) == str.charAt(1));
                                  
        Comparator<String> comparatorStr = Comparator.comparing(str -> SYMBOLS.contains(str) ? str : "");
        
        Function<String, String> toIntStr = str -> DIGIT_MAP.getOrDefault(str, ""); 
              
        comparatorStr = comparatorStr.thenComparing(str -> DIGIT_SET.contains(str) ? toIntStr.apply(str) : "");   
        comparatorStr = comparatorStr.thenComparing(str -> isTwoLetters.test(str) ? str : "");   
        comparatorStr = comparatorStr.thenComparing(str -> isOneLetter.test(str) ? str : "");
            
        return comparatorStr;
    }
    
    
    private static Map<String, String> createDigitMap(List<String> digitList) {
        Objects.requireNonNull(digitList);
              
        return digitList.stream()
                .map(digitList::indexOf)
                .collect(groupingBy(digitList::get, mapping(String::valueOf, reducing("", (a, b) -> b))));
    }
}
    