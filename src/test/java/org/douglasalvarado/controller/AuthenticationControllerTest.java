package org.douglasalvarado.controller;

import org.douglasalvarado.dto.RegisterDto;
import org.douglasalvarado.service.UsuarioService;
import org.douglasalvarado.util.JWTUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.security.core.AuthenticationException;

class AuthenticationControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private AuthenticationController authenticationController;

    private MockMvc mockMvc;

    public AuthenticationControllerTest() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }

    // Test para una autenticación exitosa
    @Test
    void testAuthenticate() throws Exception {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);
        when(jwtUtil.generateToken(anyString())).thenReturn("token");

        mockMvc.perform(post("/authentication/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"test@test.com\", \"password\": \"123456\"}"))
                .andExpect(status().isOk()) 
                .andExpect(content().string("token"));
    }

    // Test para un registro exitoso
    /*
    @Test
    void testRegister() throws Exception {
        RegisterDto registerDto = new RegisterDto("test@test.com", "123456", "123456");

        when(usuarioService.existsByCorreo(registerDto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(registerDto.getPassword())).thenReturn("hashed_password");

        mockMvc.perform(post("/authentication/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"test@test.com\", \"password\": \"123456\", \"confirmPassword\": \"123456\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("User registered successfully!"));
    }*/

    // Test para credenciales inválidas en Login
    @Test
    void testAuthenticateFailedAuthentication() throws Exception {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationException("Invalid credentials") {});

        mockMvc.perform(post("/authentication/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"wrong@test.com\", \"password\": \"wrongpassword\"}"))
                .andExpect(status().isUnauthorized()) 
                .andExpect(content().string("Authentication failed!"));
    }

    // Test cuando el email ya existe en el registro
    /*
    @Test
    void testRegisterEmailAlreadyExists() throws Exception {
        RegisterDto registerDto = new RegisterDto("existing@test.com", "123456", "123456");

        when(usuarioService.existsByCorreo(registerDto.getEmail())).thenReturn(true);

        mockMvc.perform(post("/authentication/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"existing@test.com\", \"password\": \"123456\", \"confirmPassword\": \"123456\"}"))
                .andExpect(status().isConflict())
                .andExpect(content().string("Email already exists!"));
    }*/

    // Test cuando las contraseñas no coinciden
    @Test
    void testRegisterPasswordsDoNotMatch() throws Exception {
        mockMvc.perform(post("/authentication/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"test@test.com\", \"password\": \"123456\", \"confirmPassword\": \"654321\"}"))
                .andExpect(status().isBadRequest()) 
                .andExpect(content().string("Passwords do not match!"));
    }
}
