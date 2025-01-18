package stegatext.mappers;


import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;


@Component
public record Base64Mapper(
        Map<String, Character> stringToCharacterMapper,
        Map<Character, String> characterToStringMapper) {
        
    @Autowired
    public Base64Mapper {
        Objects.requireNonNull(stringToCharacterMapper);
        Objects.requireNonNull(characterToStringMapper);
    }
}