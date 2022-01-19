package com.brunopbrito31.apilivros.security;

import com.brunopbrito31.apilivros.services.DetalheUsuarioServiceimpl;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@EnableWebSecurity
public class JWTConfiguracao extends WebSecurityConfigurerAdapter {

    private final DetalheUsuarioServiceimpl usuarioService;
    private final PasswordEncoder passwordEncoder;

    public JWTConfiguracao(DetalheUsuarioServiceimpl usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(usuarioService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable().authorizeRequests() // habilitado para ambiente de producao
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.GET, "/products").permitAll()
                .antMatchers(HttpMethod.GET, "/products/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/products/total").permitAll()
                .antMatchers(HttpMethod.GET, "/categories").permitAll()
                .antMatchers(HttpMethod.GET, "/categories/{id}").permitAll()
                .antMatchers(HttpMethod.POST, "/categories/{id}").permitAll()
                .antMatchers(HttpMethod.POST, "/stock-items/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/stock-items/**").permitAll()
                .antMatchers(HttpMethod.GET, "/stock-items/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/stock-items/**").permitAll()
                .antMatchers(HttpMethod.GET, "/description/**").permitAll()
                .antMatchers(HttpMethod.POST, "/upload/file").permitAll()
                .antMatchers("/contacts").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAutenticarFilter(authenticationManager()))
                .addFilter(new JWTValidarFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;
    }
    
}
