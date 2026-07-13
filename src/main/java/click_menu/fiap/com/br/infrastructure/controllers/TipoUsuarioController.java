package click_menu.fiap.com.br.infrastructure.controllers;

import click_menu.fiap.com.br.application.usecases.tiposUsuario.AtualizarTipoUsuarioUseCase;
import click_menu.fiap.com.br.application.usecases.tiposUsuario.BuscarTipoUsuarioPorIdUseCase;
import click_menu.fiap.com.br.application.usecases.tiposUsuario.CriarTipoUsuarioUseCase;
import click_menu.fiap.com.br.application.usecases.tiposUsuario.DeletarTipoUsuarioUseCase;
import click_menu.fiap.com.br.application.usecases.tiposUsuario.ListarTiposUsuarioUseCase;
import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioResponseDTO;
import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tipos-usuario")
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

    @PostMapping
    public ResponseEntity<TipoUsuarioResponseDTO> criarTipoUsuario(
            @Valid @RequestBody TipoUsuarioCreateDTO tipoUsuarioCreateDTO) {
        var tipoUsuarioCriado = criarTipoUsuarioUseCase.executar(tipoUsuarioCreateDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(tipoUsuarioCriado);
    }

    @GetMapping
    public ResponseEntity<List<TipoUsuarioResponseDTO>> listar() {
        return ResponseEntity.ok(listarTiposUsuarioUseCase.executar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoUsuarioResponseDTO> buscarPorId(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(buscarTipoUsuarioPorIdUseCase.executar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoUsuarioResponseDTO> atualizar(
            @PathVariable("id") UUID id,
            @RequestBody TipoUsuarioUpdateDTO tipoUsuarioUpdateDTO
    ) {
        TipoUsuarioResponseDTO tipoUsuarioAtualizado = atualizarTipoUsuarioUseCase.executar(id, tipoUsuarioUpdateDTO);

        return ResponseEntity.ok(tipoUsuarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable("id") UUID id) {
        deletarTipoUsuarioUseCase.executar(id);
    }
}
