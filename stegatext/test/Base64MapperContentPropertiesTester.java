package stegatext.test;


import static stegatext.properties.Base64MapperContentProperties.PROPERTIES;


import java.util.Set;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.Comparator;

import java.lang.reflect.*;


public class Base64MapperContentPropertiesTester {

    public static void main(String... args) {
        final var properties = PROPERTIES.getProperties();
        var methods = properties.getClass().getMethods();
        
        final var objectMethodNames = Set.of(    
                "equals",
                "getClass",
                "hashCode",
                "notify",
                "notifyAll",
                "toString",
                "wait"
        );
        
        Predicate<Method> isObjectMethod = method -> objectMethodNames.contains(method.getName());
        
        Function<Method, String> toContent = method -> {
            try {
                return STR."\{method.getName()}:\n\{method.invoke(properties)}";
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
                && (Character.toUpperCase(str.charAt(0)) == str.charAt(1));
                                    
        final var symbols = Set.of("plus", "slash", "propertyEquals");
        
        Comparator<String> comparatorStr = Comparator.comparing(str -> symbols.contains(str) ? str : "");
        
        final var digits = Set.of(
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
        
        Function<String, String> toIntStr = str -> switch (str) {
            case "zero" -> "0";
            case "one" -> "1";
            case "two" -> "2";
            case "three" -> "3";
            case "four" -> "4";
            case "five" -> "5";
            case "six" -> "6";
            case "seven" -> "7";
            case "eight" -> "8";
            case "nine" -> "9";
            default -> "";
        };
              
        comparatorStr = comparatorStr.thenComparing(str -> digits.contains(str) ? toIntStr.apply(str) : "");   
        comparatorStr = comparatorStr.thenComparing(str -> isTwoLetters.test(str) ? str : "");   
        comparatorStr = comparatorStr.thenComparing(str -> isOneLetter.test(str) ? str : "");
            
        return comparatorStr;
    }
}
    