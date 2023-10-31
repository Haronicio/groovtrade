package devrep.projet_p1.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//@EnableWebSecurity
@Configuration
public class Config {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        //https://spring.io/guides/gs/rest-service-cors/
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                .allowedOrigins("http://127.0.0.1:8080",
                                            "http://localhost:8080",
                                            "http://127.0.0.1:3000",
                                            "http://localhost:3000");
            }
        };
	}

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        //forcer à se connecter
        http.authorizeRequests().anyRequest().permitAll();

        return http.build();
    }

    
}
