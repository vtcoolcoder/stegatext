package stegatext;


import java.util.Set;

import lombok.Getter;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;


@Component
@Lazy
public class CLArgsChecker {

    private static final int FIRST_ARG_IDX = 0;
    private static final Set<String> PARAM_NAMES = Set.of(
            "--encode", "-e",
            "--decode", "-d", 
            "--encrypt", "-E",
            "--decrypt", "-D",
            "--help", "-h"
    );
    
    
    private final String[] args;
    
    @Getter
    private final String paramName;
   
    
    @Autowired
    public CLArgsChecker(String... args) {
        this.args = args;
        paramName = this.args[FIRST_ARG_IDX];
    }
    
    
    public boolean isValidParam() {  
        return PARAM_NAMES.contains(paramName);
    }
    
       
    public boolean isCLArgsExist() { return args.length > 0; }
}