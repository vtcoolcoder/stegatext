package stegatext;


import static java.util.Objects.requireNonNull;


import java.util.Map;
import java.util.Set;
import java.util.Optional;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.function.Supplier;

import lombok.Builder;


@Builder
public class ParamSwitcher {
   
    private final Object encodeAction;
    private final Object decodeAction;
    private final Object encryptAction;
    private final Object decryptAction;
    private final Object helpAction;
    private final Object wrongParamAction;
    private final Map<String, Object> actionMapper;
    
    
    public ParamSwitcher(
            Object encodeAction,
            Object decodeAction,
            Object encryptAction,
            Object decryptAction,
            Object helpAction,
            Object wrongParamAction,
            Map<String, Object> mockActionMapper
    ) {
        this.encodeAction = validateAction(encodeAction);
        this.decodeAction = validateAction(decodeAction);
        this.encryptAction = validateAction(encryptAction);
        this.decryptAction = validateAction(decryptAction);
        this.helpAction = validateAction(helpAction);
        this.wrongParamAction = validateAction(wrongParamAction);
        
        this.actionMapper = Map.of(
                "--encode", this.encodeAction,
                "-e", this.encodeAction,
                "--decode", this.decodeAction,
                "-d", this.decodeAction,
                "--encrypt", this.encryptAction,
                "-E", this.encryptAction,
                "--decrypt", this.decryptAction,
                "-D", this.decryptAction,
                "--help", this.helpAction,
                "-h", this.helpAction   
        );
    } 
    
    
    private Object validateAction(Object action) {
        requireNonNull(action);
        return switch (action) {
            case Runnable _ -> action;
            case Function _ -> action;
            case Predicate _ -> action;
            case Consumer _ -> action;
            case Supplier _ -> action;
            default -> throw new IllegalArgumentException(
                    STR."""
                    Неизвестный тип действия: \{action.getClass().getName()} !
                    Допустимые типы: Runnable, Function, Predicate, Consumer, Supplier
                    """
            );
        };
    }    
    
    
    @SuppressWarnings("unchecked")
    public Optional<Object> doAction(String paramName) {
        final var action = actionMapper.getOrDefault(paramName, wrongParamAction);
        return switch (action) {
            case Runnable runnable -> { runnable.run(); yield Optional.empty(); }
            case Function function -> Optional.ofNullable(function.apply(paramName));
            case Predicate predicate -> Optional.ofNullable(predicate.test(paramName));
            case Consumer consumer -> { consumer.accept(paramName); yield Optional.empty(); }
            case Supplier supplier -> Optional.ofNullable(supplier.get());
            default -> Optional.empty();
        };
    }   
}