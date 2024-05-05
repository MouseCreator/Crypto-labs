package mouse.univ.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Ioc {
    public static ApplicationContext get() {
        return new AnnotationConfigApplicationContext(SpringConfig.class);
    }
}
