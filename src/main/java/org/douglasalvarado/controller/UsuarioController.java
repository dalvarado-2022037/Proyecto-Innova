package org.douglasalvarado.controller;

import org.douglasalvarado.dto.UsuarioDto;
import org.douglasalvarado.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/create")
    public ResponseEntity<UsuarioDto> createUsuario(@RequestBody UsuarioDto usuariodDto) {
        try {
            UsuarioDto createdUsuario = usuarioService.createUsuario(usuariodDto);
            return ResponseEntity.ok(createdUsuario);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/find-by/{id}")
    public ResponseEntity<UsuarioDto> getUsuario(@PathVariable String id) {
        Optional<UsuarioDto> usuario = Optional.ofNullable(usuarioService.getUsuario(id));
        return usuario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).build());
    }

    @GetMapping("/list")
    public ResponseEntity<List<UsuarioDto>> getAllUsuarios() {
        List<UsuarioDto> usuarios = usuarioService.getAllUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UsuarioDto> updateUsuario(@PathVariable String id, @RequestBody UsuarioDto usuario) {
        try {
            UsuarioDto updatedUsuario = usuarioService.updateUsuario(id, usuario);
            return ResponseEntity.ok(updatedUsuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable String id) {
        try {
            usuarioService.deleteUsuario(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
