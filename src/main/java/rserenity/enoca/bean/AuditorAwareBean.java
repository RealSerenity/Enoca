package rserenity.enoca.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import rserenity.enoca.audit.AuditorAwareImpl;

@Configuration
public class AuditorAwareBean {

    @Bean(name = "auditorAware")
    public AuditorAware<String> auditorAware(){
        return new AuditorAwareImpl();
    }
}