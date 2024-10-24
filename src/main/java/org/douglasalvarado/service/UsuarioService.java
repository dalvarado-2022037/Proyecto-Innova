package org.douglasalvarado.service;

import org.douglasalvarado.dto.UsuarioDto;
import org.douglasalvarado.model.Usuario;
import org.douglasalvarado.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioDto createUsuario(UsuarioDto usuarioDto) {
        return toDto(usuarioRepository.save(toModel(usuarioDto)));
    }

    public UsuarioDto getUsuario(String id) {
        return toDto(usuarioRepository.findById(id).orElse(null));
    }

    public List<UsuarioDto> getAllUsuarios() {
        return usuarioRepository.findAll().stream()
            .map(this::toDto).toList();
    }

    public UsuarioDto updateUsuario(String id, UsuarioDto usuarioDto) {
        Usuario usuarioExistente = usuarioRepository.findById(id).orElse(null);
        if (usuarioExistente != null) {
            usuarioExistente.setNombre(usuarioDto.getNombre());
            usuarioExistente.setCorreo(usuarioDto.getCorreo());
            return toDto(usuarioRepository.save(usuarioExistente));
        }
        return null;
    }

    public void deleteUsuario(String id) {
        usuarioRepository.deleteById(id);
    }

    public boolean existsByCorreo(String email){
        return usuarioRepository.existsByCorreo(email);
    }

    public Usuario toModel(UsuarioDto usuarioDto){
        return Usuario.builder()
            .id(usuarioDto.getId())
            .correo(usuarioDto.getCorreo())
            .nombre(usuarioDto.getNombre())
            .password(usuarioDto.getPassword())
            .build();
    }

    public UsuarioDto toDto(Usuario usuario){
        return UsuarioDto.builder()
            .id(usuario.getId())
            .correo(usuario.getCorreo())
            .nombre(usuario.getNombre())
            .password(usuario.getPassword())
            .build();
    }
}
