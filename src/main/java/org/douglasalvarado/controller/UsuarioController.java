package org.douglasalvarado.controller;

import org.douglasalvarado.dto.UsuarioDto;
import org.douglasalvarado.service.UsuarioDatabaseServiceSelector;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioDatabaseServiceSelector usuarioServiceSelector;

    @PostMapping("/create")
    public ResponseEntity<UsuarioDto> createUsuario(@RequestBody UsuarioDto usuarioDto) {
        try {
            UsuarioDto createdUsuario = usuarioServiceSelector.getUsuarioService().createUsuario(usuarioDto);
            return ResponseEntity.ok(createdUsuario);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/find-by/{id}")
    public ResponseEntity<UsuarioDto> getUsuario(@PathVariable Long id) {
        UsuarioDto usuario = usuarioServiceSelector.getUsuarioService().getUsuario(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping("/list")
    public ResponseEntity<List<UsuarioDto>> getAllUsuarios() {
        List<UsuarioDto> usuarios = usuarioServiceSelector.getUsuarioService().getAllUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UsuarioDto> updateUsuario(@PathVariable Long id, @RequestBody UsuarioDto usuario) {
        try {
            UsuarioDto updatedUsuario = usuarioServiceSelector.getUsuarioService().updateUsuario(id, usuario);
            return ResponseEntity.ok(updatedUsuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        try {
            usuarioServiceSelector.getUsuarioService().deleteUsuario(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
