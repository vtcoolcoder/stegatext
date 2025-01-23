package stegatext.crypto;


import stegatext.interfaces.crypto.KeyManager;
import stegatext.interfaces.crypto.KeyGenerator;

import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.Scanner;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class KeyManagerImpl implements KeyManager {

    private static final String SECRET_KEY_FILE_NAME = "SECRET-KEY.txt";
    
    
    private final KeyGenerator keyGenerator;
    
    
    @Override
    @SneakyThrows
    public void saveSecretKey(final String key) {
        @Cleanup
        var writer = new PrintWriter(SECRET_KEY_FILE_NAME);
        writer.print(key);
    }
    
    
    @Override
    @SneakyThrows
    public String openSecretKey() {
        @Cleanup
        var scanner = new Scanner(Path.of(SECRET_KEY_FILE_NAME));
        return scanner.tokens().findAny().get();
    }  
    
    
    @Override
    public String generateKey() { 
        return keyGenerator.generateKey();
    } 
}