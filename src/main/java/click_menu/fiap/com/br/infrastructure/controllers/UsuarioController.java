package click_menu.fiap.com.br.infrastructure.controllers;

import click_menu.fiap.com.br.application.usecases.usuarios.AtualizarUsuarioUseCase;
import click_menu.fiap.com.br.application.usecases.usuarios.CriarUsuarioUseCase;
import click_menu.fiap.com.br.application.usecases.usuarios.DeletarUsuarioUseCase;
import click_menu.fiap.com.br.infrastructure.dtos.UsuarioCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.UsuarioResponseDTO;
import click_menu.fiap.com.br.infrastructure.dtos.UsuarioUpdateDTO;
import click_menu.fiap.com.br.infrastructure.mappers.UsuarioMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final CriarUsuarioUseCase criarUsuarioUseCase;
    private final UsuarioMapper usuarioMapper;
    private final DeletarUsuarioUseCase deletarUsuarioUseCase;
    private final AtualizarUsuarioUseCase atualizarUsuarioUseCase;

    public UsuarioController(CriarUsuarioUseCase criarUsuarioUseCase, UsuarioMapper usuarioMapper, DeletarUsuarioUseCase deletarUsuarioUseCase, AtualizarUsuarioUseCase atualizarUsuarioUseCase) {
        this.criarUsuarioUseCase = criarUsuarioUseCase;
        this.usuarioMapper = usuarioMapper;
        this.deletarUsuarioUseCase = deletarUsuarioUseCase;
        this.atualizarUsuarioUseCase = atualizarUsuarioUseCase;
    }

    @PostMapping
    public ResponseEntity <UsuarioResponseDTO> criar (@Valid @RequestBody UsuarioCreateDTO dto) throws IllegalAccessException {

        var usuarioCriado = criarUsuarioUseCase.executar(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioCriado);
    }

    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable("id") UUID id){
        deletarUsuarioUseCase.executar(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar (
            @PathVariable ("id") UUID id,
            @RequestBody UsuarioUpdateDTO dto) {

        UsuarioResponseDTO usuarioAtualizado = atualizarUsuarioUseCase.executar(id, dto);

        return ResponseEntity.ok(usuarioAtualizado);
    }
}
