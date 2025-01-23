package stegatext;


import stegatext.configs.MapperConfig;
import stegatext.interfaces.StegaText;
import stegatext.interfaces.helpers.MessageHelper;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Lazy;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Component
@Lazy
public class StegaTextRunner {
  
    private static StegaTextRunner instance;
    private static ParamSwitcher paramSwitcher;
    private static String[] clArgs;

    
    private final StegaText stegaText;
    private final MessageHelper messageHelper;
    private final CLArgsChecker clArgsChecker;
        

    public static void main(String... args) {
        clArgs = args;       
        getInstance().runStegaText();
    }
    
          
    public void encode() {     
        stegaText.encode();
    }
    
    
    public void decode() {
        stegaText.decode();
    }
    
       
    public void encrypt() {    
        stegaText.encrypt();
    }
    
    
    public void decrypt() {
        stegaText.decrypt();
    }
    
    
    private void runStegaText() {
        if (clArgsChecker.isCLArgsExist()) {
            doSwitchedAction();
        } else {
            messageHelper.help();
        }
    }
    
    
    private void doSwitchedAction() {
        final Consumer<String> wrongParamConsumer = paramName -> {
            messageHelper.reportWrongParamName(paramName); 
            messageHelper.help(); 
        };
        
        final var paramSwitcher = ParamSwitcher.builder()
                .encodeAction((Runnable) this::encode)
                .decodeAction((Runnable) this::decode)
                .encryptAction((Runnable) this::encrypt)
                .decryptAction((Runnable) this::decrypt)
                .helpAction((Runnable) messageHelper::help)
                .wrongParamAction(wrongParamConsumer)
                .build();
                   
        paramSwitcher.doAction(clArgsChecker.getParamName());
    }
    
    
    private static StegaTextRunner getInstance() {
        if (instance == null) {
            var context = new AnnotationConfigApplicationContext(MapperConfig.class);
            
            context.registerBean(
                "clArgs", 
                String[].class, 
                () -> clArgs
            );
            
            instance = context.getBean(StegaTextRunner.class);
        } 
        
        return instance;       
    }
}