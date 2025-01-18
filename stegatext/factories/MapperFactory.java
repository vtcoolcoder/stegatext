package stegatext.factories;


import java.util.Map;


public interface MapperFactory {
    
    Map<String, Character> createStringToCharacterMap();
    
    Map<Character, String> createCharacterToStringMap();
}