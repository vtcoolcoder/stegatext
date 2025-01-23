package stegatext.helpers;


import stegatext.interfaces.helpers.MessageHelper;
import stegatext.constants.MessageHelperConstants;

import org.springframework.stereotype.Component;


@Component
public class MessageHelperImpl implements MessageHelper, MessageHelperConstants {
   
    @Override
    public void help() {
        System.err.println(STR."""
                \t\t\t\t\t\t\
                ИСПОЛЬЗОВАНИЕ
                
                Обфускация:
                    \{OBFUSCATION}
                    
                Деобфускация:
                    \{DEOBFUSCATION}
                    
                Шифрование:
                    \{ENCRYPTION}
                    
                Дешифрование:
                    \{DECRYPTION}
                    
                Справка:
                    \{HELP}
                """
        );
    } 
    
    
    @Override
    public void reportWrongParamName(String paramName) {
        System.err.println(STR."Неизвестное имя параметра: \{paramName} !");
        System.err.println();
    }
}