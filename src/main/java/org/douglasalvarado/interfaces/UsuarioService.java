package org.douglasalvarado.interfaces;

import org.douglasalvarado.dto.UsuarioDto;

import java.util.List;

public interface UsuarioService {
    UsuarioDto findByCorreo(String correo);
    boolean existsByCorreo(String email);
    UsuarioDto createUsuario(UsuarioDto usuarioDto);
    UsuarioDto getUsuario(Long id);
    List<UsuarioDto> getAllUsuarios();
    UsuarioDto updateUsuario(Long id, UsuarioDto usuarioDto);
    void deleteUsuario(Long id);
}
