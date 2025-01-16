package stegatext.properties;


import stegatext.configs.PropertiesConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import lombok.Cleanup;
import lombok.Getter;


public enum Base64MapperContentProperties {

    PROPERTIES;
    
    
    @Component
    public record Properties(
            @Value("${A}") String a,
            @Value("${B}") String b,
            @Value("${C}") String c,
            @Value("${D}") String d,
            @Value("${E}") String e,
            @Value("${F}") String f,
            @Value("${G}") String g,
            @Value("${H}") String h,
            @Value("${I}") String i,
            @Value("${J}") String j,
            @Value("${K}") String k,
            @Value("${L}") String l,
            @Value("${M}") String m,
            @Value("${N}") String n,
            @Value("${O}") String o,
            @Value("${P}") String p,
            @Value("${Q}") String q,
            @Value("${R}") String r,
            @Value("${S}") String s,
            @Value("${T}") String t,
            @Value("${V}") String v,
            @Value("${U}") String u,
            @Value("${W}") String w,
            @Value("${X}") String x,
            @Value("${Y}") String y,
            @Value("${Z}") String z,
            @Value("${AA}") String aA,
            @Value("${BB}") String bB,
            @Value("${CC}") String cC,
            @Value("${DD}") String dD,
            @Value("${EE}") String eE,
            @Value("${FF}") String fF,
            @Value("${GG}") String gG,
            @Value("${HH}") String hH,
            @Value("${II}") String iI,
            @Value("${JJ}") String jJ,
            @Value("${KK}") String kK,
            @Value("${LL}") String lL,
            @Value("${MM}") String mM,
            @Value("${NN}") String nN,
            @Value("${OO}") String oO,
            @Value("${PP}") String pP,
            @Value("${QQ}") String qQ,
            @Value("${RR}") String rR,
            @Value("${SS}") String sS,
            @Value("${TT}") String tT,
            @Value("${VV}") String vV,
            @Value("${UU}") String uU,
            @Value("${WW}") String wW,
            @Value("${XX}") String xX,
            @Value("${YY}") String yY,
            @Value("${ZZ}") String zZ,
            @Value("${ZERO}") String zero,
            @Value("${ONE}") String one,
            @Value("${TWO}") String two,
            @Value("${THREE}") String three,
            @Value("${FOUR}") String four,
            @Value("${FIVE}") String five,
            @Value("${SIX}") String six,
            @Value("${SEVEN}") String seven,
            @Value("${EIGHT}") String eight,
            @Value("${NINE}") String nine,
            @Value("${PLUS}") String plus,
            @Value("${SLASH}") String slash,
            @Value("${EQUALS}") String propertyEquals) {
    
    }
    
    
    @Getter
    private Properties properties;
    
    
    Base64MapperContentProperties() {
        @Cleanup
        var context = new AnnotationConfigApplicationContext(PropertiesConfig.class);
        
        properties = context.getBean(Properties.class);
    }
}