package stegatext.crypto;


import static java.util.stream.Collectors.*;

import stegatext.interfaces.crypto.KeyGenerator;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import java.util.concurrent.ThreadLocalRandom;

import java.util.stream.IntStream;

import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;

import org.springframework.stereotype.Component;


@Component
public class KeyGeneratorImpl implements KeyGenerator {

    private static final int FIRST_LEFT_RANGE_BOUND = 33;
    private static final int FIRST_RIGHT_RANGE_BOUND = 126;
    private static final int SECOND_LEFT_RANGE_BOUND = 200;
    private static final int SECOND_RIGHT_RANGE_BOUND = 275;
    private static final int LIMIT = 168;
    
    
    private final List<Character> symbols;
    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    
    
    public KeyGeneratorImpl() {      
        IntPredicate hasNext = i -> (i >= FIRST_LEFT_RANGE_BOUND && i < FIRST_RIGHT_RANGE_BOUND) 
                                    || (i >= SECOND_LEFT_RANGE_BOUND && i < SECOND_RIGHT_RANGE_BOUND);
                                    
        IntUnaryOperator next = n -> n == (FIRST_RIGHT_RANGE_BOUND - 1) 
                                             ? n + (SECOND_LEFT_RANGE_BOUND - FIRST_RIGHT_RANGE_BOUND + 1) 
                                             : n + 1;
                                             
        symbols = IntStream.iterate(FIRST_LEFT_RANGE_BOUND, hasNext, next)
                           .mapToObj(e -> (char) e)
                           .collect(toList());
    }
    
           
    @Override  
    public String generateKey(final int shuffleLimit) {       
        IntStream.range(0, random.nextInt(shuffleLimit))
                 .forEach(e -> Collections.shuffle(symbols, new Random(random.nextInt())));
        
        return symbols.stream()
                .map(e -> "" + e)
                .collect(joining(""));
    }
}