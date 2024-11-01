package org.douglasalvarado.service.mongo;

import org.douglasalvarado.dto.UsuarioDto;
import org.douglasalvarado.interfaces.UsuarioService;
import org.douglasalvarado.model.mongo.UsuarioMongo;
import org.douglasalvarado.repository.mongo.UsuarioMongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceMongoImpl implements UsuarioService {
    private final UsuarioMongoRepository usuarioRepository;

    public UsuarioServiceMongoImpl(UsuarioMongoRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UsuarioDto findByCorreo(String correo) {
        UsuarioMongo usuario = usuarioRepository.findByCorreo(correo);
        return toDto(usuario);
    }

    @Override
    public boolean existsByCorreo(String email) {
        return usuarioRepository.existsByCorreo(email);
    }

    @Override
    public UsuarioDto createUsuario(UsuarioDto usuarioDto) {
        UsuarioMongo usuario = toModel(usuarioDto);
        UsuarioMongo savedUsuario = usuarioRepository.save(usuario);
        return toDto(savedUsuario);
    }

    @Override
    public UsuarioDto getUsuario(Long id) {
        return usuarioRepository.findById(id.toString()).map(this::toDto).orElse(null);
    }

    @Override
    public List<UsuarioDto> getAllUsuarios() {
        return usuarioRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public UsuarioDto updateUsuario(Long id, UsuarioDto usuarioDto) {
        String idString = id.toString();
        if (usuarioRepository.existsById(idString)) {
            UsuarioMongo usuario = toModel(usuarioDto);
            usuario.setId(idString);
            UsuarioMongo updatedUsuario = usuarioRepository.save(usuario);
            return toDto(updatedUsuario);
        } else {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
        }
    }

    @Override
    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id.toString());
    }

    private UsuarioMongo toModel(UsuarioDto usuarioDto) {
        return UsuarioMongo.builder()
                .nombre(usuarioDto.getNombre())
                .correo(usuarioDto.getCorreo())
                .password(usuarioDto.getPassword())
                .build();
    }

    private UsuarioDto toDto(UsuarioMongo usuario) {
        return UsuarioDto.builder()
                .nombre(usuario.getNombre())
                .correo(usuario.getCorreo())
                .password(usuario.getPassword())
                .build();
    }
}
