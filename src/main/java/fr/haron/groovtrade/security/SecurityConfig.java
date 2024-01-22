package fr.haron.groovtrade.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Configuration;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    //gestion operation sur requete
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// return http.authorizeHttpRequests(auth -> {
        //     auth.antMatchers("/admin").hasRole("ADMIN");
        //     auth.antMatchers("/user").hasRole("USER");
        //     auth.antMatchers("/logout").permitAll();
        //     auth.antMatchers("/test").permitAll();
        //     auth.antMatchers("/add").permitAll();
        //     auth.anyRequest().authenticated();
        // }).httpBasic(Customizer.withDefaults())
        // //.oauth2Login(Customizer.withDefaults())
        //         .logout(x->x.logoutUrl("/logout").permitAll())
        //                        .csrf(csrf -> csrf.disable())
        //         .cors(withDefaults())
        //         .build();
        http
            .authorizeRequests()
            
            .antMatchers("/utilisateur/**").authenticated() // Protéger l'accès à la page de l'utilisateur
            .antMatchers("/api/utilisateur/**").authenticated()
            .anyRequest().permitAll()
            .and()
            .formLogin() // Utiliser la page de connexion par défaut de Spring Security
            .and()
            .logout()
            .logoutSuccessUrl("/produits/liste")
            .permitAll()
            .and()
            .csrf().disable();

        return http.build();
	}
    //creation nouveau utilisateur 
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }
    //nécassaire pour connexion avec frontend
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        // https://spring.io/guides/gs/rest-service-cors/
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://127.0.0.1:8080",
                                "http://localhost:8080",
                                "http://localhost:8081",
                                "http://127.0.0.1:3000",
                                "http://localhost:3000"
                                );
            }
        };
    }

    //permet d'encoder le mdp
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
