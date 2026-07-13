package click_menu.fiap.com.br.infrastructure.controllers;

import click_menu.fiap.com.br.application.usecases.tiposUsuario.AtualizarTipoUsuarioUseCase;
import click_menu.fiap.com.br.application.usecases.tiposUsuario.BuscarTipoUsuarioPorIdUseCase;
import click_menu.fiap.com.br.application.usecases.tiposUsuario.CriarTipoUsuarioUseCase;
import click_menu.fiap.com.br.application.usecases.tiposUsuario.DeletarTipoUsuarioUseCase;
import click_menu.fiap.com.br.application.usecases.tiposUsuario.ListarTiposUsuarioUseCase;
import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioResponseDTO;
import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tipos-usuario")
@Tag(name = "Tipos de Usuário", description = "Gerenciador de Tipos de Usuario")
public class TipoUsuarioController {
    private final CriarTipoUsuarioUseCase criarTipoUsuarioUseCase;
    private final ListarTiposUsuarioUseCase listarTiposUsuarioUseCase;
    private final BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase;
    private final AtualizarTipoUsuarioUseCase atualizarTipoUsuarioUseCase;
    private final DeletarTipoUsuarioUseCase deletarTipoUsuarioUseCase;

    public TipoUsuarioController(CriarTipoUsuarioUseCase criarTipoUsuarioUseCase, ListarTiposUsuarioUseCase listarTiposUsuarioUseCase, BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase, AtualizarTipoUsuarioUseCase atualizarTipoUsuarioUseCase, DeletarTipoUsuarioUseCase deletarTipoUsuarioUseCase) {
        this.criarTipoUsuarioUseCase = criarTipoUsuarioUseCase;
        this.listarTiposUsuarioUseCase = listarTiposUsuarioUseCase;
        this.buscarTipoUsuarioPorIdUseCase = buscarTipoUsuarioPorIdUseCase;
        this.atualizarTipoUsuarioUseCase = atualizarTipoUsuarioUseCase;
        this.deletarTipoUsuarioUseCase = deletarTipoUsuarioUseCase;
    }

    @Operation(summary = "Criação de Tipo de Usuário", description = "Cria um tipo de usuário")
    @PostMapping
    public ResponseEntity<TipoUsuarioResponseDTO> criarTipoUsuario(
            @Valid @RequestBody TipoUsuarioCreateDTO tipoUsuarioCreateDTO) {
        var tipoUsuarioCriado = criarTipoUsuarioUseCase.executar(tipoUsuarioCreateDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(tipoUsuarioCriado);
    }

    @Operation(summary = "Listar Tipos de Usuário", description = "Lista os tipos de usuário")
    @GetMapping
    public ResponseEntity<List<TipoUsuarioResponseDTO>> listar() {
        return ResponseEntity.ok(listarTiposUsuarioUseCase.executar());
    }

    @Operation(summary = "Buscar Tipo de Usuário", description = "Busca um tipo de usuário conforme ID")
    @GetMapping("/{id}")
    public ResponseEntity<TipoUsuarioResponseDTO> buscarPorId(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(buscarTipoUsuarioPorIdUseCase.executar(id));
    }

    @Operation(summary = "Atualização de Tipo de Usuário", description = "Atualiza um tipo de usuário conforme ID")
    @PutMapping("/{id}")
    public ResponseEntity<TipoUsuarioResponseDTO> atualizar(
            @PathVariable("id") UUID id,
            @RequestBody TipoUsuarioUpdateDTO tipoUsuarioUpdateDTO
    ) {
        TipoUsuarioResponseDTO tipoUsuarioAtualizado = atualizarTipoUsuarioUseCase.executar(id, tipoUsuarioUpdateDTO);

        return ResponseEntity.ok(tipoUsuarioAtualizado);
    }

    @Operation(summary = "Deleção de Tipo de Usuário", description = "Deleta um tipo de usuário conforme ID")
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable("id") UUID id) {
        deletarTipoUsuarioUseCase.executar(id);
    }
}
