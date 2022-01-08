package com.brunopbrito31.apilivros.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import com.brunopbrito31.apilivros.models.entities.UsuarioModel;
import com.brunopbrito31.apilivros.repositories.UsuarioModelRepository;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioModelController {

    @Autowired
    private UsuarioModelRepository usuarioModelRep;

    @Autowired
    private PasswordEncoder encoder;


    @GetMapping
    public ResponseEntity<List<UsuarioModel>> getlistarTodos(){
        return ResponseEntity.ok(usuarioModelRep.findAll());
    }
    
    @PostMapping("/salvar")
    public ResponseEntity<UsuarioModel> salvar(@RequestBody UsuarioModel usuModel){
        usuModel.setPassword(encoder.encode(usuModel.getPassword())); //encrypta a senha
        return ResponseEntity.ok().body(usuarioModelRep.save(usuModel));
    }

    @GetMapping("/validarSenha")
    public ResponseEntity<Boolean> validarSenha(@RequestParam String login, @RequestParam String password){
        
        Optional<UsuarioModel> optUsuario = usuarioModelRep.findByLogin(login);
        if(!optUsuario.isPresent()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
        
        UsuarioModel usuario = optUsuario.get();

        // Verifica se a  senha que o usuario digitou e a mesma que esta no banco de dados
        // Compara a senha aberta com a senha criptografada
        boolean valid = encoder.matches(password,usuario.getPassword());

        HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;

        return ResponseEntity.status(status).body(valid);
    }
    
}
