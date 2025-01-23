package stegatext.constants;


public interface MessageHelperConstants {

    String PROGRAM_NAME_PREFIX = "java --enable-preview stegatext.StegaTextRunner";
    
    String OBFUSCATION = STR."\{PROGRAM_NAME_PREFIX} {--encode|-e} < source-txt-file > obfs-txt-file";
    
    String DEOBFUSCATION = STR."\{PROGRAM_NAME_PREFIX} {--decode|-d} < obfs-txt-file > decoded-source-txt-file";
    
    String ENCRYPTION = STR."\{PROGRAM_NAME_PREFIX} {--encrypt|-E} < source-txt-file > encrypted-txt-file";
    
    String DECRYPTION = STR."\{PROGRAM_NAME_PREFIX} {--decrypt|-D} < encrypted-txt-file > decrypted-source-txt-file";
    
    String HELP = STR."\{PROGRAM_NAME_PREFIX} {--help|-h}";
}