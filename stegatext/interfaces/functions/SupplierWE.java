package stegatext.interfaces.functions;


import java.util.function.Supplier;
import java.util.Objects;


@FunctionalInterface
public interface SupplierWE<R, E extends Exception> {

    R get() throws E;
    
    
    default Supplier<R> tryCatchWrapping() {
        return () -> {
            try {
                return get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
    
    
    static <R, E extends Exception> Supplier<R> adapt(final SupplierWE<R, E> func) {
        Objects.requireNonNull(func);
        return func.tryCatchWrapping();
    }
}