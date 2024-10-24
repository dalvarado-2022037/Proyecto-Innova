package org.douglasalvarado.controller;

import org.douglasalvarado.dto.LoginDto;
import org.douglasalvarado.dto.RegisterDto;
import org.douglasalvarado.model.Usuario;
import org.douglasalvarado.repository.UsuarioRepository;
import org.douglasalvarado.service.UsuarioService;
import org.douglasalvarado.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/login")
    public String authenticate(@RequestBody LoginDto loginDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
            return jwtUtil.generateToken(loginDto.getEmail());
        } catch (AuthenticationException e) {
            return "Authentication failed!";
        }
    }


    @PostMapping("/register")
    public String register(@RequestBody RegisterDto registerDto) {
        if (usuarioService.existsByCorreo(registerDto.getEmail())) {
            return "Email already exists!";
        }
        
        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            return "Passwords do not match!";
        }

        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());

        Usuario newUser = new Usuario();
        newUser.setCorreo(registerDto.getEmail());
        newUser.setPassword(encodedPassword);
        usuarioService.createUsuario(newUser);

        return "User registered successfully!";
    }
}
