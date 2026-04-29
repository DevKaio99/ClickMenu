package com.fiap.ClickMenu.Controllers;

import com.fiap.ClickMenu.Dtos.UsuarioCreateDTO;
import com.fiap.ClickMenu.Dtos.UsuarioResponseDTO;
import com.fiap.ClickMenu.Dtos.UsuarioUpdateDTO;
import com.fiap.ClickMenu.Dtos.UsuarioUpdatePassDTO;
import com.fiap.ClickMenu.Services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuario", description = "Gerenciador de usuários")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Listar Usuários", description = "Lista todos os usuários existentes no banco de dados")
    @GetMapping
    public Page<UsuarioResponseDTO> listarUsuarios(Pageable pageable    ) {
        return usuarioService.listarUsuarios(pageable);
    }

    @Operation(summary = "Buscar Usuário", description = "Busca um ou mais usuários através do Nome ou usuários que contém o nome/letras digitado")
    @GetMapping("/busca")
    public ResponseEntity<List<UsuarioResponseDTO>> buscarUsuarioPorNome(String nome) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorNome(nome));
    }

    @Operation(summary = "Criar Usuário", description = "Cria um usuário novo no banco de dados")
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criarUsuario (@Valid @RequestBody UsuarioCreateDTO dto) {
        UsuarioResponseDTO usuarioCriado = usuarioService.criarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    @Operation(summary = "Atualização de Usuário", description = "Atualiza as informações básicas do usuário especificado pelo ID")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario (
            @PathVariable("id") Long id,
            @Valid @RequestBody UsuarioUpdateDTO dto
    ){
        UsuarioResponseDTO usuarioAtualizado = usuarioService.atualizarUsuario(id, dto);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @Operation(summary = "Alteração de Senha", description = "Altera a senha do usuário especificado pelo ID exigindo a senha atual deste usuário e a senha nova")
    @PutMapping("/{id}/senha")
    public ResponseEntity<UsuarioResponseDTO> atualizarSenha (
            @PathVariable("id") Long id,
            @Valid @RequestBody UsuarioUpdatePassDTO dto
    ){
        UsuarioResponseDTO senhaAtualizada = usuarioService.atualizarSenha(id, dto);
        return ResponseEntity.ok(senhaAtualizada);
    }

    @Operation(summary = "Deletar Usuário", description = "Deleta o usuário de ID especificado")
    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable("id") Long id){
        usuarioService.deletarUsuario(id);
    }

}
