package org.douglasalvarado.controller;

import org.douglasalvarado.dto.LoginDto;
import org.douglasalvarado.dto.RegisterDto;
import org.douglasalvarado.dto.UsuarioDto;
import org.douglasalvarado.service.UsuarioService;
import org.douglasalvarado.util.JWTUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioService usuarioService;
    private final JWTUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody LoginDto loginDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
            String token = jwtUtil.generateToken(loginDto.getEmail());
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed!"); 
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        if (usuarioService.existsByCorreo(registerDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists!"); 
        }

        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords do not match!"); 
        }

        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());

        UsuarioDto newUser = new UsuarioDto();
        newUser.setCorreo(registerDto.getEmail());
        newUser.setPassword(encodedPassword);
        usuarioService.createUsuario(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
    }
}
