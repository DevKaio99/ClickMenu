package com.fiap.ClickMenu.Controllers;

import com.fiap.ClickMenu.Dtos.UsuarioRequestDTO;
import com.fiap.ClickMenu.Dtos.UsuarioResponseDTO;
import com.fiap.ClickMenu.Entities.Usuario;
import com.fiap.ClickMenu.Services.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public Page<UsuarioResponseDTO> listarUsuarios(Pageable pageable    ) {
        return usuarioService.listarUsuarios(pageable);
    }

    @GetMapping("/busca")
    public ResponseEntity<List<UsuarioResponseDTO>> buscarUsuarioPorNome(String nome) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorNome(nome));
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> salvarUsuario (@RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO usuarioSalvo = usuarioService.salvarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
    }

    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable("id") Long id){
        usuarioService.deletarUsuario(id);
    }

}
