package com.fiap.ClickMenu.Controllers;

import com.fiap.ClickMenu.Dtos.UsuarioCreateDTO;
import com.fiap.ClickMenu.Dtos.UsuarioResponseDTO;
import com.fiap.ClickMenu.Dtos.UsuarioUpdateDTO;
import com.fiap.ClickMenu.Dtos.UsuarioUpdatePassDTO;
import com.fiap.ClickMenu.Services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
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
    public ResponseEntity<UsuarioResponseDTO> criarUsuario (@Valid @RequestBody UsuarioCreateDTO dto) {
        UsuarioResponseDTO usuarioCriado = usuarioService.criarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario (
            @PathVariable("id") Long id,
            @Valid @RequestBody UsuarioUpdateDTO dto
    ){
        UsuarioResponseDTO usuarioAtualizado = usuarioService.atualizarUsuario(id, dto);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @PutMapping("/{id}/senha")
    public ResponseEntity<UsuarioResponseDTO> atualizarSenha (
            @PathVariable("id") Long id,
            @Valid @RequestBody UsuarioUpdatePassDTO dto
    ){
        UsuarioResponseDTO senhaAtualizada = usuarioService.atualizarSenha(id, dto);
        return ResponseEntity.ok(senhaAtualizada);
    }

    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable("id") Long id){
        usuarioService.deletarUsuario(id);
    }

}
