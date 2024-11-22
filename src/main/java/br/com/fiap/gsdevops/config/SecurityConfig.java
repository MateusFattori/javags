package br.com.fiap.gsdevops.config;

import br.com.fiap.gsdevops.auth.AuthorizationFilter;
import br.com.fiap.gsdevops.service.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final TokenService tokenService; 

    public SecurityConfig(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Bean
    public SecurityFilterChain config(HttpSecurity http, AuthorizationFilter authorizationFilter) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                // Swagger e documentação abertos
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/docs").permitAll()

                // Endpoint de autenticação aberto
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()

                // Endpoints de usuários
                .requestMatchers(HttpMethod.GET, "/usuarios").permitAll()
                .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                .requestMatchers(HttpMethod.PUT, "/usuarios/me").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/usuarios/{id}").authenticated()

                // Endpoints de viagens
                .requestMatchers(HttpMethod.GET, "/viagens").permitAll()
                .requestMatchers(HttpMethod.POST, "/viagens").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/viagens/{id}").authenticated()

                // Endpoints de veículos
                .requestMatchers(HttpMethod.GET, "/veiculos").permitAll()
                .requestMatchers(HttpMethod.POST, "/veiculos").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/veiculos/{id}").authenticated()

                // Endpoints de emissões
                .requestMatchers(HttpMethod.GET, "/emissoes").permitAll()
                .requestMatchers(HttpMethod.POST, "/emissoes").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/emissoes/{id}").authenticated()

                // Endpoints de combustíveis
                .requestMatchers(HttpMethod.GET, "/combustiveis").permitAll()
                .requestMatchers(HttpMethod.POST, "/combustiveis").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/combustiveis/{id}").authenticated()

                // Qualquer outro endpoint precisa de autenticação
                .anyRequest().authenticated()
            )
            .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
