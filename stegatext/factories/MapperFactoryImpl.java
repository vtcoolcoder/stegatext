package stegatext.factories;


import static stegatext.properties.Base64MapperContentProperties.Properties;
import static java.util.Map.entry;


import java.util.Objects;
import java.util.Map;
import java.util.Arrays;
import java.util.Collections;

import java.util.stream.Collectors;

import java.util.function.Predicate;
import java.util.function.Function;

import java.lang.reflect.*;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;


@Component
public class MapperFactoryImpl implements MapperFactory {
    
    private static final int FIRST_ELEM_IDX = 0;
    private static final int SECOND_ELEM_IDX = 1;
    

    private final Map<String, Character> strToCharPartMap; 
    private final Map<Character, String> charToStrPartMap;

    private final Properties properties;
    
    
    @Autowired
    public MapperFactoryImpl(Properties properties) {
        Objects.requireNonNull(properties);
        this.properties = properties;
        
        strToCharPartMap = initStrToCharPartMap();
        charToStrPartMap = swapKeyAndValue(strToCharPartMap);
    }
    
    
    private Map<String, Character> initStrToCharPartMap() {
        return Map.ofEntries(                       
                entry(properties.zero(), '0'),
                entry(properties.one(), '1'),
                entry(properties.two(), '2'),
                entry(properties.three(), '3'),
                entry(properties.four(), '4'),
                entry(properties.five(), '5'),
                entry(properties.six(), '6'),
                entry(properties.seven(), '7'),
                entry(properties.eight(), '8'),
                entry(properties.nine(), '9'),
                entry(properties.plus(), '+'),
                entry(properties.slash(), '/'),
                entry(properties.propertyEquals(), '=')
        );
    }
   
    
    @Override
    public Map<String, Character> createStringToCharacterMap() {  
        return getResult(createLetterMap(), strToCharPartMap);
    }
    
    
    @Override
    public Map<Character, String> createCharacterToStringMap() { 
        return getResult(swapKeyAndValue(createLetterMap()), charToStrPartMap);
    }
    
       
    private static <K, V> Map<V, K> swapKeyAndValue(Map<K, V> map) {
        Objects.requireNonNull(map);
        return map.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }
    
    
    private Map<String, Character> createLetterMap() {               
        return Arrays.stream(properties.getClass().getMethods())
                .filter(getIsValidPredicate())
                .map(getToEntryMapper())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    
    
    private <K, V> 
    Map<K, V> getResult(Map<K, V> modifiablePartedMap, Map<K, V> unmodifiablePartedMap) {
        Objects.requireNonNull(modifiablePartedMap);
        Objects.requireNonNull(unmodifiablePartedMap);
        
        modifiablePartedMap.putAll(unmodifiablePartedMap);
        return Collections.unmodifiableMap(modifiablePartedMap);    
    }
    
    
    private Predicate<Method> getIsValidPredicate() {
        Predicate<Method> firstPart = method -> method.getName().length() == 1
                && Character.isLetter(method.getName().charAt(FIRST_ELEM_IDX));
                
        Predicate<Method> secondFirstSubPart = method -> method.getName().length() == 2
                && Character.isLetter(method.getName().charAt(FIRST_ELEM_IDX))
                && Character.isLetter(method.getName().charAt(SECOND_ELEM_IDX));
                
        Predicate<Method> secondSecondSubPart = method -> 
                Character.toUpperCase(method.getName().charAt(FIRST_ELEM_IDX))      
                        == method.getName().charAt(SECOND_ELEM_IDX);   
                
        Predicate<Method> secondPart = secondFirstSubPart.and(secondSecondSubPart);
                     
        return firstPart.or(secondPart);
    }
    
    
    private Function<Method, Map.Entry<String, Character>> getToEntryMapper() {
        Function<String, Character> methodNameToChar = name -> switch (name.length()) {
                case 1 -> name.charAt(FIRST_ELEM_IDX);
                case 2 -> name.charAt(SECOND_ELEM_IDX);
                default -> throw new IllegalArgumentException();
        };
               
        return method -> {
                try {
                    var methodName = method.getName();
                    var content = (String) method.invoke(properties);
                    var symbol = methodNameToChar.apply(methodName);
                    return Map.entry(content, symbol);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
        };       
    }
}