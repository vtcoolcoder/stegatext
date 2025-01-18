package stegatext.configs;


import stegatext.factories.MapperFactory;

import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;


@Configuration
@ComponentScan("stegatext")
@PropertySource(value="stegatext/properties/*", encoding="UTF-8")
public class MapperConfig {

    @Bean
    public Map<String, Character> stringToCharacterMapper(MapperFactory factory) {
        return factory.createStringToCharacterMap();
    }
    
    
    @Bean
    public Map<Character, String> characterToStringMapper(MapperFactory factory) {
        return factory.createCharacterToStringMap();
    }
}