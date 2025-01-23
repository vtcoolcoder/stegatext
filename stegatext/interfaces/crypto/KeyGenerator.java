package stegatext.interfaces.crypto;


public interface KeyGenerator {

    int DEFAULT_SHUFFLE_LIMIT = 23 * 7;
    
    
    String generateKey(final int shuffleLimit);
    
    
    default String generateKey() {
        return generateKey(DEFAULT_SHUFFLE_LIMIT);
    }
}