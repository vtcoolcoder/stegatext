package stegatext.configs;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;


@Configuration
@ComponentScan("stegatext/properties")
@PropertySource(value="stegatext/properties/*", encoding="UTF-8")
public class PropertiesConfig {

}