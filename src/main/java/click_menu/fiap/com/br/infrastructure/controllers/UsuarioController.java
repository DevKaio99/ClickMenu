package click_menu.fiap.com.br.infrastructure.controllers;

import click_menu.fiap.com.br.application.usecases.usuarios.AtualizarSenhaUsuarioUseCase;
import click_menu.fiap.com.br.application.usecases.usuarios.AtualizarUsuarioUseCase;
import click_menu.fiap.com.br.application.usecases.usuarios.CriarUsuarioUseCase;
import click_menu.fiap.com.br.application.usecases.usuarios.DeletarUsuarioUseCase;
import click_menu.fiap.com.br.infrastructure.dtos.Usuario.UsuarioCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.Usuario.UsuarioResponseDTO;
import click_menu.fiap.com.br.infrastructure.dtos.Usuario.UsuarioUpdateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.Usuario.UsuarioUpdatePassDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final CriarUsuarioUseCase criarUsuarioUseCase;
    private final DeletarUsuarioUseCase deletarUsuarioUseCase;
    private final AtualizarUsuarioUseCase atualizarUsuarioUseCase;
    private final AtualizarSenhaUsuarioUseCase atualizarSenhaUsuarioUseCase;

    public UsuarioController(CriarUsuarioUseCase criarUsuarioUseCase, DeletarUsuarioUseCase deletarUsuarioUseCase, AtualizarUsuarioUseCase atualizarUsuarioUseCase, AtualizarSenhaUsuarioUseCase atualizarSenhaUsuarioUseCase) {
        this.criarUsuarioUseCase = criarUsuarioUseCase;
        this.deletarUsuarioUseCase = deletarUsuarioUseCase;
        this.atualizarUsuarioUseCase = atualizarUsuarioUseCase;
        this.atualizarSenhaUsuarioUseCase = atualizarSenhaUsuarioUseCase;
    }

    @Operation(summary = "Criação de Usuário", description = "Cria um Usuário")
    @PostMapping
    public ResponseEntity <UsuarioResponseDTO> criar (@Valid @RequestBody UsuarioCreateDTO dto) throws IllegalAccessException {

        var usuarioCriado = criarUsuarioUseCase.executar(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioCriado);
    }

    @Operation(summary = "Deleção de Usuário", description = "Deleta um Usuário quando informado um ID e quando esse usuário não possui Restaurante cadastrado")
    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable("id") UUID id){
        deletarUsuarioUseCase.executar(id);
    }

    @Operation(summary = "Atualização de Usuário", description = "Altera informações básicas de um Usuário quando informado um ID")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar (
            @PathVariable ("id") UUID id,
            @RequestBody UsuarioUpdateDTO dto) {

        UsuarioResponseDTO usuarioAtualizado = atualizarUsuarioUseCase.executar(id, dto);

        return ResponseEntity.ok(usuarioAtualizado);
    }

    @Operation(summary = "Alteração de Senha", description = "Altera a senha do usuário especificado pelo ID exigindo a senha atual deste usuário e a senha nova")
    @PutMapping("/{id}/senha")
    public ResponseEntity<UsuarioResponseDTO> atualizarSenha (
            @PathVariable("id") UUID id,
            @Valid @RequestBody UsuarioUpdatePassDTO dto
    ){
        UsuarioResponseDTO senhaAtualizada = atualizarSenhaUsuarioUseCase.executar(id, dto);
        return ResponseEntity.ok(senhaAtualizada);
    }
}
