package org.douglasalvarado.service.postgres;

import org.douglasalvarado.dto.UsuarioDto;
import org.douglasalvarado.interfaces.UsuarioService;
import org.douglasalvarado.model.postgres.UsuarioPostgres;
import org.douglasalvarado.repository.postgres.UsuarioPostgresRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServicePostgresImpl implements UsuarioService {
    private final UsuarioPostgresRepository usuarioRepository;

    public UsuarioServicePostgresImpl(UsuarioPostgresRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UsuarioDto findByCorreo(String correo) {
        UsuarioPostgres usuario = usuarioRepository.findByCorreo(correo);
        return toDto(usuario);
    }

    @Override
    public boolean existsByCorreo(String email) {
        return usuarioRepository.existsByCorreo(email);
    }

    @Override
    public UsuarioDto createUsuario(UsuarioDto usuarioDto) {
        UsuarioPostgres usuario = toModel(usuarioDto);
        UsuarioPostgres savedUsuario = usuarioRepository.save(usuario);
        return toDto(savedUsuario);
    }

    @Override
    public UsuarioDto getUsuario(Long id) {
        return usuarioRepository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public List<UsuarioDto> getAllUsuarios() {
        return usuarioRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public UsuarioDto updateUsuario(Long id, UsuarioDto usuarioDto) {
        if (usuarioRepository.existsById(id)) {
            UsuarioPostgres usuario = toModel(usuarioDto);
            usuario.setId(id);
            UsuarioPostgres updatedUsuario = usuarioRepository.save(usuario);
            return toDto(updatedUsuario);
        } else {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
        }
    }

    @Override
    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    private UsuarioPostgres toModel(UsuarioDto usuarioDto) {
        return UsuarioPostgres.builder()
                .id(usuarioDto.getId())
                .nombre(usuarioDto.getNombre())
                .correo(usuarioDto.getCorreo())
                .password(usuarioDto.getPassword())
                .build();
    }

    private UsuarioDto toDto(UsuarioPostgres usuario) {
        return UsuarioDto.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .correo(usuario.getCorreo())
                .password(usuario.getPassword())
                .build();
    }
}
