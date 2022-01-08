package com.brunopbrito31.apilivros.security;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.brunopbrito31.apilivros.models.entities.UsuarioModel;
import com.brunopbrito31.data.DetalheUsuarioData;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// vai ser responsavel por autenticar o usuario e fazer a geracao do token jwt
public class JWTAutenticarFilter extends UsernamePasswordAuthenticationFilter{

    public static final int TOKEN_EXPIRACAO = 600_000; // 10 Minutos

    // a senha so vai ficar aqui por questoes de desenvolvimento, o ideal eh que ela fique no arquivo de configuracao
    // jamais no codigo fonte
    public static final String TOKEN_SENHA = "30bdd81f-73b3-4ff3-886e-4c7d87d3d5eb";

    private final AuthenticationManager authenticationManager;

    public JWTAutenticarFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws org.springframework.security.core.AuthenticationException {
        
        try {
            UsuarioModel usuario = new ObjectMapper().readValue(request.getInputStream(), UsuarioModel.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                usuario.getLogin(),
                usuario.getPassword(),
                new ArrayList<>()
            ));

        } catch (IOException e) {
            throw new RuntimeException("Falha ao autenticar o usuario",e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {


        DetalheUsuarioData usuarioData = (DetalheUsuarioData) authResult.getPrincipal();

        String token = JWT.create().withSubject(usuarioData.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRACAO))
                .sign(Algorithm.HMAC512(TOKEN_SENHA));
        
        response.getWriter().write(token);
        response.getWriter().flush();
    }

    
    
}
